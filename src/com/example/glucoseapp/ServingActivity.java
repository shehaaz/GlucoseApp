package com.example.glucoseapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ServingActivity extends Activity {
	
	public static String ServingSizeGrams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serving);
		
		ServingSizeGrams = MainActivity.food_serving_size_map.get(MainActivity.foodItem);
		
		TextView servingSize = (TextView) findViewById(R.id.serving_size);
		
		servingSize.setText(ServingSizeGrams + " Grams");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.serving, menu);
		return true;
	}

}
