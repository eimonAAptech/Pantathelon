package com.pentathlon.pentathlon.models.homecategory;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomecategoryResponse{

	@SerializedName("image")
	private String image;

	@SerializedName("menu_icon")
	private String menuIcon;

	@SerializedName("is_active")
	private boolean isActive;

	@SerializedName("level")
	private int level;

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("product_count")
	private int productCount;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("position")
	private int position;

	@SerializedName("children_data")
	private List<ChildrenDataItem> childrenData;

	public String getImage(){
		return image;
	}

	public String getMenuIcon(){
		return menuIcon;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public int getLevel(){
		return level;
	}

	public int getParentId(){
		return parentId;
	}

	public int getProductCount(){
		return productCount;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public int getPosition(){
		return position;
	}

	public List<ChildrenDataItem> getChildrenData(){
		return childrenData;
	}
}