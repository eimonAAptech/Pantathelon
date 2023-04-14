package com.pentathlon.pentathlon.models.SignUpResponse;

import com.google.gson.annotations.SerializedName;

public class CustomAttributesItem{

	@SerializedName("value")
	private String value;

	@SerializedName("attribute_code")
	private String attributeCode;

	public String getValue(){
		return value;
	}

	public String getAttributeCode(){
		return attributeCode;
	}
}