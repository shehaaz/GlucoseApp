package com.example.glucoseapp;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class LaunchActivity extends Activity {

	//launch

	private String name;
	private Context context;
	private Profile profile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

		context = this.getApplicationContext();

		AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
		Account[] list = manager.getAccounts();

		String gmail = null;

		for(Account account: list)
		{
			if(account.type.equalsIgnoreCase("com.google"))
			{
				gmail = account.name;
				String[] split_email = gmail.split("@");
				name = split_email[0];
				break;
			}
		}

		AsyncHttpClient getProfileClient = new AsyncHttpClient();

		getProfileClient.get("http://198.61.177.186:8080/virgil/data/glucoseapp/glucose_user_profile/"+name, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				//response success load the user profile from cassandra. The response is a json object

				if(response != null){

					JSONObject jObject;
					try {
						jObject = new JSONObject(response);

						String weight = jObject.getString("weight");
						String age = jObject.getString("age"); 
						String patient_type = jObject.getString("patient_type");
						String gender = jObject.getString("gender");
						//create new profile object
						profile = new Profile(weight,age,patient_type,gender); 
						Intent i = new Intent(context, MainActivity.class);
						i.putExtra("PROFILE",profile);
						startActivity(i);
						finish();
					}
					catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					
					Intent i = new Intent(context, UserProfileActivity.class);
					i.putExtra("NAME", name);
					startActivity(i);
					finish();
				}
			}

			@Override
			public void onFailure(Throwable e, String response) {
				System.out.println("Failed to reach Cassandra");

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launch, menu);
		return true;
	}

}
