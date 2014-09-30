package Fragments;

import org.json.JSONArray;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.util.Log;


public class MentionsTimelineFragment extends TweetsListFragment {
	private TwitterClient client;
	
	@Override
	//All non-view init stuuf comes here - Runs First
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		client = TwitterApplication.getRestClient();
		populateTimeline("1", null);
		super.onCreate(savedInstanceState);
	}
	

	public void populateTimeline(String sinceId, String maxId)
	{
		client.getMentionsTimeline(new JsonHttpResponseHandler() {
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
	
}
