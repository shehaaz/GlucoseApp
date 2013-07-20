package com.example.glucoseapp;

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
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	//ArrayList<FoodItem> Food_Item_List = new ArrayList<FoodItem>();
	public static ArrayList<String> food_list = new ArrayList<String>();
	public static Map<String, String> food_serving_size = new HashMap<String, String>();
	public static String foodItem;
	public static String ServingSizeGrams;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(foodItem != null){
			
			TextView servingSize = (TextView) findViewById(R.id.serving_size);
			
			servingSize.setText(ServingSizeGrams + " Grams");
			
			TextView item = (TextView) findViewById(R.id.autocomplete_food);
			
			item.setText(foodItem);
		}

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
				
				FoodItem Food_Item = new FoodItem(	foodName,
													glycemicLoad,
													diabeticCarbChoices,
													servingSizeGrams,
													servingOZ,
													availCarbServing,
													reformatGI);
				//Food_Item_List.add(Food_Item);
				food_list.add(Food_Item.getFoodName());
				food_serving_size.put(Food_Item.getFoodName(), Food_Item.getServingSizeGrams());
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
		
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_food);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,food_list);

		textView.setAdapter(adapter);
		
		foodItem = textView.getText().toString();
		
		ServingSizeGrams = food_serving_size.get(foodItem);

		finish();
		startActivity(getIntent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
