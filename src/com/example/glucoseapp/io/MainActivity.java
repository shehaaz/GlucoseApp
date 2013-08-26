package com.example.glucoseapp.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.glucoseapp.R;
import com.example.glucoseapp.R.id;
import com.example.glucoseapp.R.layout;
import com.example.glucoseapp.R.menu;
import com.example.glucoseapp.R.raw;
import com.example.glucoseapp.meal.FoodItem;
import com.example.glucoseapp.meal.FoodItemListActivity;
import com.example.glucoseapp.user.Profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;




public class MainActivity extends Activity {
	
	private ArrayList<String> food_name_list = new ArrayList<String>();
	private Map<String, FoodItem> food_map = new HashMap<String, FoodItem>();
	private ArrayList<FoodItem> foodItem_list = new ArrayList<FoodItem>();
	private String food_items_picked;
	private Profile profile;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
            		foodItem_list.add(food_item);
            	}
            	//Get the food item from the map
            	
            	FoodItemListIntent.putParcelableArrayListExtra("FOOD_ITEMS", foodItem_list);
            	FoodItemListIntent.putExtra("PROFILE", profile);
            	startActivity(FoodItemListIntent);	
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
