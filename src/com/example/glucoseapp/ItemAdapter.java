package com.example.glucoseapp;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ItemAdapter extends ArrayAdapter<FoodItem>{

	public ItemAdapter(Context context, int textViewResourceId,
			List<FoodItem> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

}
