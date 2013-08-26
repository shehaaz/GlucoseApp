package com.example.glucoseapp.io;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.example.glucoseapp.R;
import com.example.glucoseapp.R.id;
import com.example.glucoseapp.R.layout;
import com.example.glucoseapp.R.menu;
import com.example.glucoseapp.meal.Meal;
import com.example.glucoseapp.user.Profile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SendMealActivity extends Activity {

	private String ServingSizeGrams;
	private String total_g_load;
	private String total_carbs;
	private String num_servings;
	private Calendar calendar;
	private Context context;
	private Meal meal;
	private ProgressDialog pDialog;
	private Profile profile;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serving);

		calendar = Calendar.getInstance();
		context = this.getApplicationContext();


		//Get the parcel sent by MainActivity and extract the values.
		meal = (Meal) getIntent().getParcelableExtra("MEAL");
		profile = (Profile) getIntent().getParcelableExtra("PROFILE");

		total_g_load = meal.getG_load();
		total_carbs = meal.getTotal_carbs();

		TextView textView_total_g_load = (TextView) findViewById(R.id.total_g_load);

		textView_total_g_load.setText("total_g_load: " + total_g_load);

		TextView textView_total_carbs = (TextView) findViewById(R.id.total_carbs);

		textView_total_carbs.setText("total_carbs: " + total_carbs);

		final Button button = (Button) findViewById(R.id.submit_serving);


		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				try {
					AsyncHttpClient client = new AsyncHttpClient();
					JSONObject jsonParams = new JSONObject();
					final String timestamp = String.valueOf(calendar.getTimeInMillis());
					jsonParams.put("total_carbs", total_carbs);
					jsonParams.put("total_g_load", total_g_load);
					jsonParams.put("p_type", profile.getP_type());
					jsonParams.put("bw", profile.getWeight());

					StringEntity entity = new StringEntity(jsonParams.toString());


					client.put(context,"http://198.61.177.186:8080/virgil/data/glucoseapp/menu/"+profile.getName()+"/"+timestamp+"/",entity,null,new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							Log.d("POST:","Success HTTP PUT to POST ColumnFamily");
							System.out.println("Success HTTP PUT to POST ColumnFamily");
							pDialog = new ProgressDialog(SendMealActivity.this);
							pDialog.setMessage("Computing Graph...");
							pDialog.show();

							AsyncHttpClient putClient = new AsyncHttpClient();

							putClient.get("http://198.61.177.186:5000/user/"+profile.getName()+"/"+timestamp, new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(String response) {
									Log.d("GET:","Success GET from Flask");
									System.out.println("Success GET from Flask");
									pDialog.dismiss();
									Intent i = new Intent(context, GraphActivity.class);
//									i.putExtra("PROFILE", profile);
//									i.putExtra("FOOD_ITEM", food_items.get(0));
									startActivity(i);
									finish();
								}
							});

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
