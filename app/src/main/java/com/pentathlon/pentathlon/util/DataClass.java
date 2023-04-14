package com.pentathlon.pentathlon.util;

import com.pentathlon.pentathlon.models.homecategory.ChildrenDataItem;
import com.pentathlon.pentathlon.models.homepage.BannersItem;
import com.pentathlon.pentathlon.models.homepage.BestsellerItem;
import com.pentathlon.pentathlon.models.homepage.BestsellingbrandsItem;
import com.pentathlon.pentathlon.models.homepage.NewproductsItem;

import java.util.ArrayList;
import java.util.List;

public class DataClass {
    public static List<BannersItem> bannersItemList =new ArrayList<>();
    public static List<ChildrenDataItem> categoryList =new ArrayList<>();
    public static List<NewproductsItem> newproductsItems =new ArrayList<>();
    public static List<BestsellerItem> bestsellerItemList=new ArrayList<>();
    public static List<BestsellingbrandsItem> bestsellingbrandsItemList=new ArrayList<>();
}
