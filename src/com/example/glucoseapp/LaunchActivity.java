package com.example.glucoseapp;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class LaunchActivity extends Activity {

	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

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
				//response success
			}

			@Override
			public void onFailure(Throwable e, String response) {
				// Response failed :(
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
