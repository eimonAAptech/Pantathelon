package com.pentathlon.pentathlon.models.userDetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserDetailsResponse{

	@SerializedName("store_id")
	private int storeId;

	@SerializedName("firstname")
	private String firstname;

	@SerializedName("addresses")
	private List<Object> addresses;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("extension_attributes")
	private ExtensionAttributes extensionAttributes;

	@SerializedName("lastname")
	private String lastname;

	@SerializedName("custom_attributes")
	private List<CustomAttributesItem> customAttributes;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("disable_auto_group_change")
	private int disableAutoGroupChange;

	@SerializedName("group_id")
	private int groupId;

	@SerializedName("id")
	private int id;

	@SerializedName("website_id")
	private int websiteId;

	@SerializedName("email")
	private String email;

	@SerializedName("created_in")
	private String createdIn;

	public int getStoreId(){
		return storeId;
	}

	public String getFirstname(){
		return firstname;
	}

	public List<Object> getAddresses(){
		return addresses;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public ExtensionAttributes getExtensionAttributes(){
		return extensionAttributes;
	}

	public String getLastname(){
		return lastname;
	}

	public List<CustomAttributesItem> getCustomAttributes(){
		return customAttributes;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getDisableAutoGroupChange(){
		return disableAutoGroupChange;
	}

	public int getGroupId(){
		return groupId;
	}

	public int getId(){
		return id;
	}

	public int getWebsiteId(){
		return websiteId;
	}

	public String getEmail(){
		return email;
	}

	public String getCreatedIn(){
		return createdIn;
	}
}