package com.pentathlon.pentathlon.models.productList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductListResponse{

	@SerializedName("current")
	private int current;

	@SerializedName("pages")
	private int pages;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("count")
	private int count;

	@SerializedName("message")
	private String message;

	public int getCurrent(){
		return current;
	}

	public int getPages(){
		return pages;
	}

	public List<DataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public int getCount(){
		return count;
	}

	public String getMessage(){
		return message;
	}
}