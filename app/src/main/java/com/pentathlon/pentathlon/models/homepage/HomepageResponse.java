package com.pentathlon.pentathlon.models.homepage;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomepageResponse{

	@SerializedName("sponsers")
	private List<SponsersItem> sponsers;

	@SerializedName("bestseller")
	private List<BestsellerItem> bestseller;

	@SerializedName("bestsellingbrands")
	private List<BestsellingbrandsItem> bestsellingbrands;

	@SerializedName("banners")
	private List<BannersItem> banners;

	@SerializedName("newproducts")
	private List<NewproductsItem> newproducts;

	public List<SponsersItem> getSponsers(){
		return sponsers;
	}

	public List<BestsellerItem> getBestseller(){
		return bestseller;
	}

	public List<BestsellingbrandsItem> getBestsellingbrands(){
		return bestsellingbrands;
	}

	public List<BannersItem> getBanners(){
		return banners;
	}

	public List<NewproductsItem> getNewproducts(){
		return newproducts;
	}
}