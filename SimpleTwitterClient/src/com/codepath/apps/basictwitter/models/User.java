package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model {


	@Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "screenName")
	private String screenName;
	
	@Column(name = "profileImageURL")
	private String profileImageURL;
	
	
    // Make sure to have a default constructor for every ActiveAndroid model
    public User(){
       super();
    }
    
	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageURL() {
		return profileImageURL;
	}

	public long getUid() {
		return uid;
	}


	public static User fromJSON(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		User user = new User();
		try {
			user.name = jsonObject.getString("name");
			user.screenName = jsonObject.getString("screen_name");
			user.uid = jsonObject.getLong("id");
			user.profileImageURL = jsonObject.getString("profile_image_url");
		}
		catch(JSONException e)
		{
			e.printStackTrace();
			return null;
		}
		user.save();
		return user;	
	}
	
	public JSONObject toJSONObject() {
		final JSONObject json = new JSONObject();
		try {
			json.put("id", uid);
			json.put("name", name);
			json.put("screen_name", screenName);
			json.put("profile_image_url", profileImageURL);
		} catch (JSONException e) {
			return null;
		}
		return json;
	}

}
