package com.codepath.apps.basictwitter;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	private static final int COMPOSE_INTENT_ID = 1;

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter  aTweets;
	private ListView lvTweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		client = TwitterApplication.getRestClient();		
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		populateTimeline("1", null);

		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
		        loadMoreOldTweets();
		    }
		});
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose_tweet, menu);
        return true;
    }
    
	public void composeNewTweet(MenuItem mi) {
	     startActivityForResult(new Intent(this, ComposeActivity.class), COMPOSE_INTENT_ID);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == COMPOSE_INTENT_ID && resultCode == RESULT_OK) {
			updateTweets();
		}
	}
	
	public void updateTweets() {

		long maxId = Long.MIN_VALUE;
		for (Tweet tweet : tweets) {
			maxId = Math.max(tweet.getUid(), maxId);
		}
		final String sinceId = tweets.isEmpty() ? "1" : "" + maxId;
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray array) {
			    ArrayList<Tweet> tweets = Tweet.fromJSONArray(array);
				Collections.reverse(tweets);
				for (Tweet tweet : tweets) {
					aTweets.insert(tweet, 0);
				}
			}
			
			@Override
			public void onFailure(Throwable t, String s) {
				Log.d("debug", t.toString());
				Log.d("debug", s);
			}
		}, sinceId, null , isNetworkAvailable());
	}
	
	
	private void loadMoreOldTweets() {
		long minId = Long.MAX_VALUE;
		for (Tweet tweet : tweets) {
			minId = Math.min(tweet.getUid(), minId);
		}
		final String maxId = tweets.isEmpty() ? null : "" + minId;
		populateTimeline("1", maxId);
	}
	
	public void populateTimeline(String sinceId, String maxId)
	{
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				// TODO Auto-generated method stub
				aTweets.addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				// TODO Auto-generated method stub
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		} , sinceId, maxId , isNetworkAvailable());
	}

	public boolean isNetworkAvailable() {
		  try
		  {
				ConnectivityManager cm = (ConnectivityManager) 
					   getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = cm.getActiveNetworkInfo();
				// if no network is available networkInfo will be null
				// otherwise check if we are connected
				return  (networkInfo != null && networkInfo.isConnected()) ;
		  }
		  catch(SecurityException e)
		  {
			  	return false;
		  }

	}
	
}
