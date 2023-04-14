package com.pentathlon.pentathlon.models.getFilterModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("code")
	private String code;

	@SerializedName("values")
	private List<ValuesItem> values;

	public void setCode(String code) {
		this.code = code;
	}

	public void setValues(List<ValuesItem> values) {
		this.values = values;
	}

	public String getCode(){
		return code;
	}

	public List<ValuesItem> getValues(){
		return values;
	}
}