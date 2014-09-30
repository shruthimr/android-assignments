package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import Fragments.UserTimelineFragement;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;


public class ProfileActivity extends FragmentActivity {

	private User u = new User();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		try{
			u = (User) getIntent().getSerializableExtra("user");
		}
		catch(Exception e)
		{
			u = null;
		}
		
		if(u == null)
		{

			TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject jsonObj) {
					// TODO Auto-generated method stub
					User u = User.fromJSON(jsonObj);
					getActionBar().setTitle("@"+u.getScreenName());
					populateProfileHeader(u);
					populateTimeLine(u);
				}
				
				private void populateProfileHeader(User u) {
					// TODO Auto-generated method stub
					TextView tvName = (TextView) findViewById(R.id.tvName);
					TextView tvTagLine = (TextView) findViewById(R.id.tvtagLine);
					TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
					TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
					ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
					
					tvName.setText(u.getName());
					tvTagLine.setText(u.getTagline());
					tvFollowers.setText(u.getFollowers() + " followers");
					tvFollowing.setText(u.getFollowing() + " following");
					ImageLoader.getInstance().displayImage(u.getProfileImageURL(), ivProfileImage);
				}
	
				

				
				@Override
				public void onFailure(Throwable e, String s) {
					// TODO Auto-generated method stub
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}
			});	
		
		}
		else
		{
			//Friends profile
			// TODO Auto-generated method stub
			
			TextView tvName = (TextView) findViewById(R.id.tvName);
			TextView tvTagLine = (TextView) findViewById(R.id.tvtagLine);
			TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
			TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
			ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
			
			tvName.setText(u.getName());
			tvTagLine.setText(u.getTagline());
			tvFollowers.setText(u.getFollowers() + " followers");
			tvFollowing.setText(u.getFollowing() + " following");
			ImageLoader.getInstance().displayImage(u.getProfileImageURL(), ivProfileImage);
			populateTimeLine(u);

		}
		
		
	}
	
	
	private void populateTimeLine(User u) {
		
	    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	    // removes the existing fragment calling onDestroy
	    ft.replace(R.id.flProfileContainer,  new UserTimelineFragement(u)); 
	    ft.commit();
	}
}
