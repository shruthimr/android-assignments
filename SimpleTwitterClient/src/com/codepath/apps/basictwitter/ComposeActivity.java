package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {

	private TwitterClient client;
	private EditText etTweet;
	private TextView tvCounter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		client = TwitterApplication.getRestClient();
		etTweet = (EditText)findViewById(R.id.etMsg);
		tvCounter = (TextView)findViewById(R.id.tvCount);

		etTweet.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(etTweet.getText().toString().length() > 100)
				{
					etTweet.setText(etTweet.getText().toString().substring(0, 100));
				}
				
				tvCounter.setText(Math.min(100,100-etTweet.getText().toString().length()) + "");
			}
		});
		
		
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose_done, menu);
        return true;
    }
	
	public void onDone(MenuItem mi) {
	     client.postTweet(new JsonHttpResponseHandler() {
	    	 @Override
			 public void onSuccess(JSONObject obj) {
	    		 setResult(RESULT_OK);
	    		 finish();
			 }
			
			 @Override
			 public void onFailure(Throwable t, String s) {
				 Log.d("debug", t.toString());
				 Log.d("debug", s);
			 }
	     }, etTweet.getText().toString());
	}
	
}
