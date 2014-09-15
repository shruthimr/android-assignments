package com.yahoo.shruthir.gridimagesearch;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchSettingsActivity extends Activity{// implements OnItemSelectedListener  {

	Settings settings = new Settings();
	Spinner sizeSpinner;
	Spinner colorSpinner;
	Spinner typeSpinner;
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_settings);
        //Pull out the arguments from intent
		
	   sizeSpinner = (Spinner) findViewById(R.id.spImageSize);
	   colorSpinner = (Spinner) findViewById(R.id.spImageColor);
	   typeSpinner = (Spinner) findViewById(R.id.spImageType);
	
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.image_size, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		sizeSpinner.setAdapter(adapter);
		
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapter = ArrayAdapter.createFromResource(this,
		        R.array.image_color, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		colorSpinner.setAdapter(adapter);
		
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapter = ArrayAdapter.createFromResource(this,
		        R.array.image_type, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		typeSpinner.setAdapter(adapter);
		
		
	}

//	@Override
//	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//		// TODO Auto-generated method stub
//		switch (view.getId()) {
//	    case R.id.spImageColor:
//	    	settings.ColorFilter =  parent.getItemAtPosition(pos).toString();
//	        break;
//	    case R.id.spImageSize:
//	    	settings.ImageSize =  parent.getItemAtPosition(pos).toString();
//	        break;
//	    case R.id.spImageType:
//	    	settings.ImageType =  parent.getItemAtPosition(pos).toString();
//	        break;
//	    }
//	}
//
//	@Override
//	public void onNothingSelected(AdapterView<?> arg0) {
//		// TODO Auto-generated method stub
//		
//	}
	
    public void onSave(View v)
    {


        sizeSpinner = (Spinner) findViewById(R.id.spImageSize);
       colorSpinner = (Spinner) findViewById(R.id.spImageColor);
    	 typeSpinner = (Spinner) findViewById(R.id.spImageType);
    	EditText etValue = (EditText)findViewById(R.id.etSiteFilter);
    	
    	
    	settings.SiteFilter = etValue.getText().toString();
    	settings.ColorFilter = colorSpinner.getSelectedItem().toString();
    	settings.ImageSize = sizeSpinner.getSelectedItem().toString();
    	settings.ImageType = typeSpinner.getSelectedItem().toString();
    	
    	//Create result
    	Intent i = new Intent();
    	i.putExtra("settings", settings);
    	
    	setResult(RESULT_OK,i);
    	finish();
    }
    
}
