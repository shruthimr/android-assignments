package Fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.ProfileActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsListFragment extends Fragment {

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter  aTweets;
	private ListView lvTweets;
	private View view;
	
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    view = inflater.inflate(R.layout.fragments_tweets_list, container, false);
	    // Setup handles to view objects here
	    // etFoo = (EditText) v.findViewById(R.id.etFoo);
		client = TwitterApplication.getRestClient();		
		lvTweets = (ListView) view.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
		        loadMoreOldTweets();
		    } 
		});
		
		
		return view;
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
				addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				// TODO Auto-generated method stub
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		} , sinceId, maxId , true);
	}
	
    
	@Override
	//All non-view init stuuf comes here - Runs First
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		
		super.onCreate(savedInstanceState);
	}
	
	public TweetArrayAdapter getAdapter()
	{
		return aTweets;
	}
	
	public void addAll(ArrayList<Tweet> tweets)
	{
		aTweets.clear();
		aTweets.addAll(tweets);
	}
}
