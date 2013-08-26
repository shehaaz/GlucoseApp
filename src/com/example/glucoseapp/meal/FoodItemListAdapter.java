package com.example.glucoseapp.meal;

import java.util.ArrayList;

import com.example.glucoseapp.R;
import com.example.glucoseapp.R.id;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodItemListAdapter extends ArrayAdapter<FoodItem> {
	
	Context context;
	int layoutResourceId;
	ArrayList<FoodItem> food_items;
	
	public FoodItemListAdapter(Context context, int layoutResourceId, ArrayList<FoodItem> food_items){
		super(context, layoutResourceId, food_items);
		
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.food_items = food_items;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		View row = convertView;
		ItemHolder holder = null;
		
	
		if(row == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new ItemHolder();
			
			holder.food_item = food_items.get(position);
			holder.food_name = (TextView) row.findViewById(R.id.list_food_item_name);
			holder.food_item_servings = (TextView) row.findViewById(R.id.list_food_item_servings);
			holder.food_amount_servings = (EditText) row.findViewById(R.id.list_amount_of_servings);
			holder.send_button = (ImageView) row.findViewById(R.id.confirm_button);
			
			row.setTag(holder);
			holder.send_button.setTag(holder);
		}
		else{
			holder = (ItemHolder) row.getTag();
		}
		
		holder.food_name.setText(holder.food_item.getFoodName());
		holder.food_item_servings.setText("Grams per servings: "+ holder.food_item.getServingSizeGrams());
	
		holder.send_button.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				ItemHolder tempHolder = (ItemHolder) v.getTag();
				
				String serving_amount = tempHolder.food_amount_servings.getText().toString();
				tempHolder.food_item.setMealServing(serving_amount);
				
				Toast.makeText(context, 
					"meal item: "+tempHolder.food_item.getFoodName()+" servings: "+tempHolder.food_item.getMealServing(), 
					Toast.LENGTH_LONG).show();
				
			}
			
			});
		
		return row;
		
	}
	
	public Meal getMeal(){
		
		Meal meal = new Meal("total_carb","g_load");
		
		Double temp_gload = 0.0;
		Double temp_total_carb = 0.0;
		
		for(FoodItem food : food_items){
			
			temp_gload += Double.parseDouble(food.getGlycemicLoad()) * 
						  Double.parseDouble(food.getAvailCarbServing()) * 
						  Double.parseDouble(food.getMealServing());
			
			temp_total_carb += Double.parseDouble(food.getAvailCarbServing()) + Double.parseDouble(food.getMealServing()); 
		}
		
		Double total_gload = temp_gload/temp_total_carb;
		
		meal.setTotal_carbs(Double.toString(temp_total_carb));
		meal.setG_load(Double.toString(total_gload));
		
		return meal;
	}
	
	static class ItemHolder{
		FoodItem food_item;
		TextView food_name;
		TextView food_item_servings;
		EditText food_amount_servings;
		ImageView send_button;
	}

}
