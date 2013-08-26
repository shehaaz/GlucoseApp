package com.example.glucoseapp.meal;

import android.os.Parcel;
import android.os.Parcelable;

public class Meal implements Parcelable {

	private String total_carbs;
	private String g_load;

	public Meal(String total_carbs, String g_load) {

		this.setTotal_carbs(total_carbs);
		this.setG_load(g_load);
	}

	public String getTotal_carbs() {
		return total_carbs;
	}

	public void setTotal_carbs(String total_carbs) {
		this.total_carbs = total_carbs;
	}

	public String getG_load() {
		return g_load;
	}

	public void setG_load(String g_load) {
		this.g_load = g_load;
	}

	//Below: code to make MEAL Parcelable

	public Meal(Parcel source){

		total_carbs = source.readString();
		g_load = source.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(total_carbs);
		dest.writeString(g_load);
	}

	public static final Creator<Meal> CREATOR = new Creator<Meal>() {

		public Meal createFromParcel(Parcel source) {
			return new Meal(source);
		}

		public Meal[] newArray(int size) {
			return new Meal[size];
		}
	};


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
