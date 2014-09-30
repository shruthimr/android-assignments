package com.codepath.apps.basictwitter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	Context adapterContext ;
	public TweetArrayAdapter(Context context,  List<Tweet> tweets) {
		super(context, 0,tweets );
		// TODO Auto-generated constructor stub
		adapterContext = context;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
	       // Get the data item for this position from the users list in constructor
	       final Tweet tweet = getItem(position);   
	       View v;
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
	    	   //item_user is the new xml (equal to nib). This builds object from the xml layout
	    	   LayoutInflater inflator = LayoutInflater.from(getContext());
	    	   v = inflator.inflate(R.layout.tweet_item, parent,false);
	       }
	       else
	       {
	    	   v = convertView;
	       }
	       
	       // Lookup view for data population
	       ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
	       TextView tvUserName = (TextView) v.findViewById(R.id.tvScreenName);
	       TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
	       TextView tvFavCount = (TextView) v.findViewById(R.id.tvStarCount);
	       TextView tvRetweetCount = (TextView) v.findViewById(R.id.tvRetweetCount);
	       TextView tvTimeStamp = (TextView) v.findViewById(R.id.tvTimeStamp);

	       ivProfileImage.setImageResource(android.R.color.transparent);
	       ImageLoader imageLoader = ImageLoader.getInstance();
	       
	       // Populate the data into the template view using the data object
	       imageLoader.displayImage(tweet.getUser().getProfileImageURL(), ivProfileImage);
	       tvUserName.setText(tweet.getUser().getScreenName());
	       tvBody.setText(tweet.getBody());
	       tvFavCount.setText(Long.toString(tweet.getFavouritesCount()));
	       tvRetweetCount.setText(Long.toString(tweet.getRetweetCount()));
	       tvTimeStamp.setText(tweet.getRelativeTimeStamp());
	       
	       ivProfileImage.setOnClickListener(new OnClickListener() {
			
	           @Override
	           public void onClick(View v) {
//	                Toast.makeText(v.getContext(),"I am here", Toast.LENGTH_LONG).show();  
	       			Intent i = new Intent(adapterContext , ProfileActivity.class);
	       			i.putExtra("user" ,  tweet.getUser());
	       			adapterContext.startActivity(i);	       			
	           }
		});
	       // Return the completed view to render on screen
	       return v;
	   }
	
}
