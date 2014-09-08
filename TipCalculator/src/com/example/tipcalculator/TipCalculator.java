package com.example.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class TipCalculator extends Activity {

	private SeekBar tipControl = null;
	private SeekBar splitControl = null;
	private TextView tipPercentage = null;
	private TextView tipValue = null;
	private TextView totalValue = null;
	private TextView splitSummary = null;
	private TextView split = null;
	private EditText subTotal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        
        subTotal = (EditText)findViewById(R.id.etSubTotal);
        subTotal.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {

            	calculateAndDisplay();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        }); 
        
        tipControl = (SeekBar)findViewById(R.id.sbTipSlider);
        tipControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
 
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            	calculateAndDisplay();				
			}
 
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
 
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
        
        
        splitControl = (SeekBar)findViewById(R.id.sbSplit);
        splitControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
 
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            	calculateAndDisplay();				
			}
 
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
 
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
        
    }

    public void calculateAndDisplay (){
    	
    	tipPercentage = (TextView)findViewById(R.id.tvTipPercentValue);
        tipValue = (TextView)findViewById(R.id.tvTip);
        totalValue = (TextView)findViewById(R.id.tvTotal);
        subTotal = (EditText)findViewById(R.id.etSubTotal);
        splitSummary = (TextView)findViewById(R.id.tvSplitPerHead);
        split = (TextView)findViewById(R.id.tvSplitValue);

        
        tipControl = (SeekBar)findViewById(R.id.sbTipSlider);
        int tipPercent = tipControl.getProgress();
        
        splitControl = (SeekBar)findViewById(R.id.sbSplit);
    	int count = splitControl.getProgress();
    	if(count == 0) count = 1;
    	split.setText(" ("+ String.format("%d",count) + ")");
    	
        tipPercentage.setText(" ("+ String.format("%d", tipPercent) + "%):");
        
        double subTotalValue = 0.0;
        if(!subTotal.getText().toString().isEmpty()) 
        	 subTotalValue = Double.parseDouble(subTotal.getText().toString());
        double tip = tipPercent/100.00 *subTotalValue;
        
        tipValue.setText(String.format("%.2f",tip));
        
        double total = (tip + subTotalValue);
        totalValue.setText(String.format("%.2f",total));
        
        
        splitSummary.setText(String.format("%.2f",total/count));

        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.tvTipValue) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
