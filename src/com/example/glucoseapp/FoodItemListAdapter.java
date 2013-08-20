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
				food_items.get(position).setMealServing(serving_amount);
				
			}
			
			});
		
		return row;
		
	}
	
	public ArrayList<FoodItem> getFoodItems(){
		return food_items;
	}
	
	static class ItemHolder{
		FoodItem food_item;
		TextView food_name;
		TextView food_item_servings;
		EditText food_amount_servings;
		ImageView send_button;
	}

}
