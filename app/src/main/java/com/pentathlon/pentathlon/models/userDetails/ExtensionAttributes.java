package com.pentathlon.pentathlon.models.userDetails;

import com.google.gson.annotations.SerializedName;

public class ExtensionAttributes{

	@SerializedName("is_subscribed")
	private boolean isSubscribed;

	public boolean isIsSubscribed(){
		return isSubscribed;
	}
}