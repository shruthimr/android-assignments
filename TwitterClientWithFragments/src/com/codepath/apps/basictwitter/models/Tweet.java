package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;


@Table(name="Tweets")
public class Tweet extends Model {

	@Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;
	
	@Column(name = "body")
	private String body;	
	
	@Column(name = "createdAt")
	private String createdAt;
	
	@Column(name = "user", onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
	private User user;
	
	@Column(name = "retweetCount")
	private long retweetCount;
	
	@Column(name = "favouritesCount")
	private long favouritesCount;
	
	private String relativeTimeStamp;

    // Make sure to have a default constructor for every ActiveAndroid model
    public Tweet(){
       super();
    }
    
	public String getRelativeTimeStamp() {
		return relativeTimeStamp;
	}
	
	public long getRetweetCount() {
		return retweetCount;
	}

	public long getFavouritesCount() {
		return favouritesCount;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}
	public static Tweet fromJSON(JSONObject jsonObject)
	{
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObject.getString("text");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.uid = jsonObject.getLong("id");
			tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
			tweet.retweetCount = jsonObject.optLong("retweet_count");
			tweet.favouritesCount = jsonObject.optLong("favorite_count");
			tweet.relativeTimeStamp = parseCreatedAt(tweet.createdAt);
		}
		catch(JSONException e)
		{
			return null;
		}
		tweet.save();
		return tweet;
	}
	
	private static String parseCreatedAt(String createdAt) {
		try{
			Date date2 = new Date();
			String[] formats = new String[] { "EEE MMM d HH:mm:ss Z yyyy" };
			Date date1 = DateUtils.parseDate(createdAt, formats);
		
			long diff = (date2.getTime() - date1.getTime())/1000;
			if(diff <60)
			{
				return diff+"s";
			}
			else if(diff < 60*60)
			{
				return diff/60+"m";
			}
			else if(diff < 60*60*24)
			{
				return diff/(60*60)+"h";
			}
			else if(diff < 60*60*24*365)
			{
				return diff/(60*60*24)+"d";
			}
			else 
			{
				return ">1 yr";
			}
		}
		catch(DateParseException e)
		{
			return null;
		}
	}

	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		// TODO Auto-generated method stub
		
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
	      // Process each result in json array, decode and convert to business object
	      for (int i=0; i < jsonArray.length(); i++) {
	          JSONObject tweetJson = null;
	          try {
	        	  tweetJson = jsonArray.getJSONObject(i);
	          } catch (Exception e) {
	              e.printStackTrace();
	              continue;
	          }

	          Tweet tweet = Tweet.fromJSON(tweetJson);
	          if (tweet != null) {
	        	  tweets.add(tweet);
	          }
	      }
	      return tweets;
	}
	
	
	public JSONObject toJSONObject() {
		final JSONObject json = new JSONObject();
		try {
			json.put("id", uid);
			json.put("text", body);
			json.put("created_at", createdAt);
			json.put("retweet_count", retweetCount);
			json.put("favorite_count", favouritesCount);
			if (user != null) {
				json.put("user", user.toJSONObject());
			}
		} catch (JSONException e) {
			return null;
		}
		return json;
	}
	
	
	public String toString()
	{
		return this.getBody() + " - " + this.getUser().getName();
	}

	public static JSONArray getSavedTweets(Long id) {
		final List<Tweet> tweets = getTweetsFromDB(id);
		final JSONArray array = new JSONArray();
		for (Tweet tweet : tweets) {
			array.put(tweet.toJSONObject());
		}
		return array;
	}

	public static List<Tweet> getTweetsFromDB(Long id) {
		if (id == null) {
			return new Select().from(Tweet.class).orderBy("uid DESC").limit("25").execute();
		}
		return new Select().from(Tweet.class).where("uid < ?", id).orderBy("uid DESC").limit("25").execute();
	}

}
