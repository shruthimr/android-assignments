package Fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragement extends TweetsListFragment {
	
	
	User user = new User();
	public UserTimelineFragement(User u) {
		// TODO Auto-generated constructor stub
		user = u;
	}

	@Override
	//All non-view init stuuf comes here - Runs First
	public void onCreate(Bundle savedInstanceState) {
		String userId; 
		super.onCreate(savedInstanceState);
		if(user.getScreenName().equalsIgnoreCase("Shruthi"))
		{
			userId = null;
		}
		else
		{
			userId = user.getUid() + "";
		}
		TwitterApplication.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess( JSONArray jsonTweets) {
				// TODO Auto-generated method stub
				getAdapter().clear();
				getAdapter().addAll(Tweet.fromJSONArray(jsonTweets));
			}
		} , userId);
	}
}
