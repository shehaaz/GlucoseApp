package com.example.glucoseapp;

import java.util.Calendar;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServingActivity extends Activity {
	
	private String ServingSizeGrams;
	private String g_load;
	private String carb_p_serv;
	private String num_servings;
	private Calendar calendar;
	private Context context;
	private FoodItem food_item;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serving);
		
		calendar = Calendar.getInstance();
		context = this.getApplicationContext();
		
		
		//Get the parcel sent by MainActivity and extract the values.
		food_item = (FoodItem) getIntent().getParcelableExtra("FOOD_ITEM");
		
		ServingSizeGrams = food_item.getServingSizeGrams();
		g_load = food_item.getGlycemicLoad();	
		carb_p_serv = food_item.getAvailCarbServing();
		
		
		TextView servingSize = (TextView) findViewById(R.id.serving_size);
		
		servingSize.setText(ServingSizeGrams + " Grams");
		
		
		final TextView num_servings_textView = (TextView) findViewById(R.id.serving_amount);
		
		
		
		final Button button = (Button) findViewById(R.id.submit_serving);
		
	
		
        button.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View v) {
            
            	num_servings = num_servings_textView.getText().toString();
            	
            	try {
					AsyncHttpClient client = new AsyncHttpClient();
					JSONObject jsonParams = new JSONObject();
					String timestamp = String.valueOf(calendar.getTimeInMillis());
					jsonParams.put("carb_p_serv", carb_p_serv);
					jsonParams.put("num_servings", num_servings);
					jsonParams.put("g_load", g_load);

					StringEntity entity = new StringEntity(jsonParams.toString());


					client.put(context,"http://198.61.177.186:8080/virgil/data/glucoseapp/menu/"+timestamp+"/",entity,null,new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							Log.d("POST:","Success HTTP PUT to POST ColumnFamily");
							Intent i = new Intent(context, MainActivity.class);
							startActivity(i);
							finish();
						}
					});
				} catch (Exception e) {
					System.out.println("Failed HTTP PUT");
				} 
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.serving, menu);
		return true;
	}

}
