package com.yahoo.shruthir.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	ArrayList<String> items;
	ListView lvItems;
	ArrayAdapter<String> itemsAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        readItems();
        lvItems = (ListView) findViewById(R.id.lvTodoItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , items);
        lvItems.setAdapter(itemsAdapter);
        items.add("Buy Milk");
        items.add("Water Plants");
        setupListViewListener();
    }
    
    
    public void addTodoItem(View v)
    {
    	EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	saveItems();
    }
    
    
    private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir , "todo.txt");
		try{
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
			}
		catch(IOException e)
		{
			items = new ArrayList<String>();
		}
		
	}
    
    private void saveItems() 
    {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir , "todo.txt");
		try{
			FileUtils.writeLines(todoFile, items);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
    
    private void setupListViewListener() 
    {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
		
			public boolean onItemLongClick(AdapterView<?> parent ,
					View view, int position, long rowId){
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
    }
}
