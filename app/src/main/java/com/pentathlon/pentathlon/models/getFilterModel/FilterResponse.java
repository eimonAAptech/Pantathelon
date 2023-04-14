package com.pentathlon.pentathlon.models.getFilterModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FilterResponse{

	@SerializedName("data")
	private List<DataItem> data;

	public List<DataItem> getData(){
		return data;
	}
}