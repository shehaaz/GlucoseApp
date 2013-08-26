package com.example.glucoseapp.meal;

import java.util.ArrayList;

import com.example.glucoseapp.R;
import com.example.glucoseapp.R.id;
import com.example.glucoseapp.R.layout;
import com.example.glucoseapp.R.menu;
import com.example.glucoseapp.io.SendMealActivity;
import com.example.glucoseapp.user.Profile;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class FoodItemListActivity extends ListActivity {
	
	private ArrayList<FoodItem> food_items;
	private Profile profile;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_item_list);
		
		context = getApplicationContext();
		
		food_items = getIntent().getParcelableArrayListExtra("FOOD_ITEMS");
		profile = (Profile) getIntent().getParcelableExtra("PROFILE");
		
		final FoodItemListAdapter adapter = new FoodItemListAdapter(this,R.layout.food_item, food_items);
		
		Button button = (Button) findViewById(R.id.submit_meal_button);
		
		button.setOnClickListener(new View.OnClickListener() {
			
	        public void onClick(View v) {
	        	
	        	Meal meal = adapter.getMeal();
	        	
	        	Intent i = new Intent(context, SendMealActivity.class);
	        	i.putExtra("MEAL",meal);
	        	i.putExtra("PROFILE", profile);
	        	startActivity(i);
	        }
	    });    
		
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_item_list, menu);
		return true;
	}

}
