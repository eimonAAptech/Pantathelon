package com.pentathlon.pentathlon.models.homepage;

import com.google.gson.annotations.SerializedName;

public class BestsellerItem{

	@SerializedName("image")
	private String image;

	@SerializedName("special_price")
	private String specialPrice;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("rating")
	private String rating;

	@SerializedName("id")
	private String id;

	@SerializedName("sku")
	private String sku;

	public String getImage(){
		return image;
	}

	public String getSpecialPrice(){
		return specialPrice;
	}

	public String getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getRating(){
		return rating;
	}

	public String getId(){
		return id;
	}

	public String getSku(){
		return sku;
	}
}