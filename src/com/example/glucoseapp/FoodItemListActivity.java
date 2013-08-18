package com.example.glucoseapp;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;

public class FoodItemListActivity extends ListActivity {
	
	private ArrayList<FoodItem> food_items;
	private Profile profile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_item_list);
		
		food_items = getIntent().getParcelableArrayListExtra("FOOD_ITEMS");
		profile = (Profile) getIntent().getParcelableExtra("PROFILE");
		
		FoodItemListAdapter adapter = new FoodItemListAdapter(this,R.layout.food_item, food_items);
		
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_item_list, menu);
		return true;
	}

}
