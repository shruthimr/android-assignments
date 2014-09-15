package com.yahoo.shruthir.gridimagesearch;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> 
{

	public ImageResultArrayAdapter(Context context, List<ImageResult> images) 
	{
		super(context, R.layout.item_image_result ,images);
		// TODO Auto-generated constructor stub
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position from the users list in constructor
       ImageResult imageInfo = getItem(position); 
       
       // Check if an existing view is being reused, otherwise inflate the view 
       // i.e create model from layout file
       if (convertView == null) {
    	   LayoutInflater inflator = LayoutInflater.from(getContext());
    	   convertView =  inflator.inflate(R.layout.item_image_result, parent, false);
       }
      
       SmartImageView ivImage = (SmartImageView) convertView.findViewById(R.id.ivThumbNail);
       TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

       ivImage.setImageResource(android.R.color.transparent);
       ivImage.setImageUrl(imageInfo.getThumbUrl()); 
       tvTitle.setText(imageInfo.getTitle().toString());
       // Return the completed view to render on screen
       return convertView;
   }
}
