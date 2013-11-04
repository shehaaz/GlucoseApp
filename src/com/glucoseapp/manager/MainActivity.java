package com.glucoseapp.manager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import com.example.glucoseapp.R;
import com.glucoseapp.meal.FoodItem;
import com.glucoseapp.meal.FoodItemListActivity;
import com.glucoseapp.user.Profile;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;




public class MainActivity extends Activity {

	private ArrayList<String> food_name_list = new ArrayList<String>();
	private Map<String, FoodItem> food_map = new HashMap<String, FoodItem>();
	private ArrayList<FoodItem> foodItem_list = new ArrayList<FoodItem>();
	private String food_items_picked;
	private Profile profile;
	private AlertDialog.Builder alertDialogBuilder;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Parse
		
//		Parse.initialize(this, "wx47PGwoq2OP4FtebytxMw2bgdLPY1sNLOAFTsc3", "0L48AQvVR3HNKsqBIvDB0GSOFhpzg6W7ScUFVM2D");
//		ParseAnalytics.trackAppOpened(getIntent());
//		
//		ParseObject testObject = new ParseObject("SHEHAAZ");
//		testObject.put("SHEHAAZ", "SAIF");
//		testObject.saveInBackground();
//		
//		ParseQuery<ParseObject> query = ParseQuery.getQuery("SHEHAAZ");
//		query.getInBackground("xWMyZ4YEGZ", new GetCallback<ParseObject>() {
//		  public void done(ParseObject object, ParseException e) {
//		    if (e == null) {
//		      // object will be your game score
//		    } else {
//		      // something went wrong
//		    }
//		  }
//		});
		
		//End Parse
		
		alertDialogBuilder = new AlertDialog.Builder(this);

		profile = (Profile) getIntent().getParcelableExtra("PROFILE");


		/*Loading the Data from CSV File*/

		InputStream is = getResources().openRawResource(R.raw.gldata);

		try {
			BufferedReader brReadMe = new BufferedReader
					(new InputStreamReader(is, "UTF-8"));

			String strLine;

			while ((strLine = brReadMe.readLine()) != null){

				String[] RowData = strLine.split(",");

				if (RowData.length == 8){

					String foodName = RowData[1];
					String glycemicLoad = RowData[2];
					String diabeticCarbChoices = RowData[3];
					String servingSizeGrams = RowData[4];
					String servingOZ = RowData[5];
					String availCarbServing = RowData[6];
					String reformatGI = RowData[7];

					FoodItem food_item = new FoodItem(	foodName,
							glycemicLoad,
							diabeticCarbChoices,
							servingSizeGrams,
							servingOZ,
							availCarbServing,
							reformatGI);

					food_name_list.add(food_item.getFoodName());
					food_map.put(food_item.getFoodName(), food_item);
				}
			} 

			brReadMe.close();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Auto-complete TextView*/

		final  MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.autocomplete_food);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,food_name_list);

		textView.setAdapter(adapter);

		textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());



		final Button button = (Button) findViewById(R.id.submit_food);

		final Intent FoodItemListIntent = new Intent(this, FoodItemListActivity.class);

		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//Get the selected Food item
				food_items_picked = textView.getText().toString();
				//split according to comma and leading space
				String [] items = food_items_picked.split(", ");
				for(String i : items){
					FoodItem food_item = food_map.get(i);
					if(food_item != null){
						foodItem_list.add(food_item);
					}
				}

				//Get the food item from the map
				if(!foodItem_list.isEmpty()){
					FoodItemListIntent.putParcelableArrayListExtra("FOOD_ITEMS", foodItem_list);
					FoodItemListIntent.putExtra("PROFILE", profile);
					startActivity(FoodItemListIntent);	
				}else{
					alertDialogBuilder.setTitle("Please Select Items from the List");
					alertDialogBuilder
					.setCancelable(true)
					.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
