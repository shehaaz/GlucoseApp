package com.example.glucoseapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable{
	
	private String weight;
	private String age;
	private String p_type;
	private String gender;
	
	public Profile(String pWeight,String pAge, String pP_type, String pGender){
		
		weight = pWeight;
		age = pAge;
		p_type = pP_type;
		gender = pGender;
		
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getP_type() {
		return p_type;
	}

	public void setP_type(String p_type) {
		this.p_type = p_type;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	//Below: Items for Parceable
	
	public Profile(Parcel source){
		
		weight = source.readString();
		age = source.readString();
		p_type = source.readString(); 
		gender = source.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(weight);
		dest.writeString(age);
		dest.writeString(p_type);
		dest.writeString(gender);
	}

	public static final Creator<Profile> CREATOR = new Creator<Profile>() {

		public Profile createFromParcel(Parcel source) {
			return new Profile(source);
		}

		public Profile[] newArray(int size) {
			return new Profile[size];
		}
	};


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
