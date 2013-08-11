package com.example.glucoseapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UserProfileActivity extends Activity {
	private String body_weight;
	private String age;
	private String gender;
	private String patient_type;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		final Button button = (Button) findViewById(R.id.submit_profile_button);

		final Intent mainIntent = new Intent(this, MainActivity.class);

		final EditText body_weight = (EditText) findViewById(R.id.body_weight_input);
		body_weight.setHint("Enter you weight in kg");

		Profile user_profile = new Profile(	body_weight,
											age,
											gender,
											patient_type);

	button.setOnClickListener(new View.OnClickListener() {

		public void onClick(View v) {
			//Get the selected Food item
			//body_weight = body_weight.getText().toString();
			//Get the food item from the map
			
			mainIntent.putExtra("FOOD_ITEM", food_item);
			startActivity(mainIntent);	
		}
	});
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}
	
	
	

}
