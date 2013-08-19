package com.example.glucoseapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

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
		
		FoodItem food_item = food_items.get(position);
		
		if(row == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new ItemHolder();
			
			holder.food_name = (TextView) row.findViewById(R.id.list_food_item_name);
			holder.food_item_servings = (TextView) row.findViewById(R.id.list_food_item_servings);
			holder.food_amount_servings = (EditText) row.findViewById(R.id.list_amount_of_servings);
			
			row.setTag(holder);
		}
		else{
			holder = (ItemHolder) row.getTag();
		}
		
		holder.food_name.setText(food_item.getFoodName());
		holder.food_item_servings.setText("Grams per servings: "+ food_item.getServingSizeGrams());
//		food_item.setMealServing(holder.food_amount_servings.getText().toString());
		
		return row;
		
	}
	
	static class ItemHolder{
		TextView food_name;
		TextView food_item_servings;
		EditText food_amount_servings;
	}

}
