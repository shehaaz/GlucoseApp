package com.example.glucoseapp;

public class FoodItem {
	
	private String foodName;
	private String glycemicLoad;
	private String diabeticCarbChoices;
	private String servingSizeGrams;
	private String servingOZ;
	private String availCarbServing;
	private String reformatGI;
	
	

	public FoodItem(String p_foodName, String p_glycemicLoad,
			String p_diabeticCarbChoices, String p_servingSizeGrams,
			String p_servingOZ, String p_availCarbServing, String p_reformatGI) {
		
		setFoodName(p_foodName);
		setGlycemicLoad(p_glycemicLoad);
		setDiabeticCarbChoices(p_diabeticCarbChoices);
		setServingSizeGrams(p_servingSizeGrams);
		setServingOZ(p_servingOZ);
		setAvailCarbServing(p_availCarbServing);
		setReformatGI(p_reformatGI);
	}



	public String getFoodName() {
		return foodName;
	}



	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}



	public String getGlycemicLoad() {
		return glycemicLoad;
	}



	public void setGlycemicLoad(String glycemicLoad) {
		this.glycemicLoad = glycemicLoad;
	}



	public String getDiabeticCarbChoices() {
		return diabeticCarbChoices;
	}



	public void setDiabeticCarbChoices(String diabeticCarbChoices) {
		this.diabeticCarbChoices = diabeticCarbChoices;
	}



	public String getServingSizeGrams() {
		return servingSizeGrams;
	}



	public void setServingSizeGrams(String servingSizeGrams) {
		this.servingSizeGrams = servingSizeGrams;
	}



	public String getServingOZ() {
		return servingOZ;
	}



	public void setServingOZ(String servingOZ) {
		this.servingOZ = servingOZ;
	}



	public String getAvailCarbServing() {
		return availCarbServing;
	}



	public void setAvailCarbServing(String availCarbServing) {
		this.availCarbServing = availCarbServing;
	}



	public String getReformatGI() {
		return reformatGI;
	}



	public void setReformatGI(String reformatGI) {
		this.reformatGI = reformatGI;
	}


	
	
}
