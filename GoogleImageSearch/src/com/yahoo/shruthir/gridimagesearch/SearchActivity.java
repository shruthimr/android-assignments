package com.yahoo.shruthir.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery = null;
	GridView gvResults = null;
	Button btnSearch = null;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	Settings settings ;
	static int start = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				// TODO Auto-generated method stub
				Intent i = new Intent( getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
				
			}
        	
		});
        
        gvResults.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				start = start + 8;
				loadMoreImages(start); 
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_settings, menu);
        return true;
    }
    
    
    public void onSettingsChange(MenuItem mi)
    {
    	Intent i = new Intent(this, SearchSettingsActivity.class);
    	//Pass arguments
    	//Execute Intent startActivityForResults
    	startActivityForResult(i, 5);
    }
    
    public void setupViews()
    {
    	etQuery = (EditText) findViewById(R.id.etQuery);
    	gvResults = (GridView) findViewById(R.id.gvResults);
    	btnSearch = (Button) findViewById(R.id.btnSearch);	
    }
    
    public void onImageSearch(View v)
    {
		start = 0;
		imageResults.clear();
		loadMoreImages(start);
		
    }
    
    public void loadMoreImages(int start)
    {
    	String query = etQuery.getText().toString();
    	if(settings != null) {
    		if(!settings.ColorFilter.isEmpty())   query = query + "&imgcolor="+settings.ColorFilter; 
    		if(!settings.ImageSize.isEmpty())   query = query + "&imgsz="+settings.ImageSize; 
    		if(!settings.ImageType.isEmpty())   query = query + "&imgtype="+settings.ImageType; 
    		if(!settings.SiteFilter.isEmpty())   query = query + "&as_sitesearch="+settings.SiteFilter; 
    	}
    	
    	Log.d("DEBUG",query);	
//    	Toast.makeText(this, "Searching for:"+query, Toast.LENGTH_SHORT).show();
    	
    	//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android%27
    	AsyncHttpClient client = new AsyncHttpClient();
    	client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + start + "&v=1.0&q=" + query, 
    			new JsonHttpResponseHandler(){
    				@Override
    				public void onSuccess(JSONObject response){
    					JSONArray imageJsonResults = null;
    					try{
    						imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
    						imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
    						Log.d("DEBUG", imageResults.toString());
    					}
    					catch(JSONException e)
    					{
    						e.printStackTrace();
    					}
    				}
    		});
    }
    
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	if(requestCode == 5)
    	{
    		if(resultCode == RESULT_OK)
    		{
    			settings =  (Settings)data.getSerializableExtra("settings");
    			start = 0;
    			imageResults.clear();
    			loadMoreImages(start);
    		}
    	}
    }
    
}
