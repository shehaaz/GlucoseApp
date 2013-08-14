package com.example.glucoseapp;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class UserProfileActivity extends Activity {
	private String body_weight;
	private String age;
	private String gender;
	private String patient_type;

	private String name;

	private Context context;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		context = this.getApplicationContext();

		name = getIntent().getStringExtra("NAME");

		Button b = (Button) findViewById(R.id.submit_profile_button);


		final EditText body_weight_input = (EditText) findViewById(R.id.body_weight_input);
		final EditText age_input = (EditText) findViewById(R.id.age_input);
		final EditText gender_input = (EditText) findViewById(R.id.gender);
		final EditText patient_type_input = (EditText) findViewById(R.id.patient_type);



		Profile profile = new Profile(body_weight,age,patient_type,gender); 

		b.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				body_weight = body_weight_input.getText().toString();
				age = age_input.getText().toString();
				gender = gender_input.getText().toString();
				patient_type = patient_type_input.getText().toString();

				try{

					AsyncHttpClient client = new AsyncHttpClient();
					JSONObject jsonParams = new JSONObject();

					jsonParams.put("weight", body_weight);
					jsonParams.put("age", age);
					jsonParams.put("patient_type", patient_type);
					jsonParams.put("gender", gender);

					StringEntity entity = new StringEntity(jsonParams.toString());

					client.put(context,"http://198.61.177.186:8080/virgil/data/glucoseapp/glucose_user_profile/"+name+"/",entity,null,new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {

							Intent i = new Intent(context, MainActivity.class);
							startActivity(i);

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
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}




}
