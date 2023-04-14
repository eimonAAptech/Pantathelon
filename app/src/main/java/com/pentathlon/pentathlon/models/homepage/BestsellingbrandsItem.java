package com.pentathlon.pentathlon.models.homepage;

import com.google.gson.annotations.SerializedName;

public class BestsellingbrandsItem{

	@SerializedName("image")
	private String image;

	@SerializedName("icon")
	private String icon;

	@SerializedName("title")
	private String title;

	@SerializedName("brand")
	private String brand;

	public String getImage(){
		return image;
	}

	public String getIcon(){
		return icon;
	}

	public String getTitle(){
		return title;
	}

	public String getBrand(){
		return brand;
	}
}