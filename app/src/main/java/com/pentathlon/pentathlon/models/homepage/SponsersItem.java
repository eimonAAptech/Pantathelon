package com.pentathlon.pentathlon.models.homepage;

import com.google.gson.annotations.SerializedName;

public class SponsersItem{

	@SerializedName("image")
	private String image;

	@SerializedName("data")
	private String data;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

	public String getImage(){
		return image;
	}

	public String getData(){
		return data;
	}

	public String getTitle(){
		return title;
	}

	public String getType(){
		return type;
	}
}