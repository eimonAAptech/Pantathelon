package com.pentathlon.pentathlon.Retrofit;


import com.google.gson.JsonObject;
import com.pentathlon.pentathlon.models.SignUpResponse.SignUpResponse;
import com.pentathlon.pentathlon.models.getFilterModel.FilterResponse;
import com.pentathlon.pentathlon.models.homecategory.HomecategoryResponse;
import com.pentathlon.pentathlon.models.homepage.HomepageResponse;
import com.pentathlon.pentathlon.models.productList.ProductListResponse;
import com.pentathlon.pentathlon.models.userDetails.UserDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiList {

    @POST("rest/V1/dapl/customer/token")
    Call<JsonObject> logInWithEmail(@Query("username") String username,
                                    @Query("password") String password,
                                    @Query("devicetoken") String devicetoken,
                                    @Query("firebasetoken") String firebasetoken);


    @POST("rest/V1/dapl/customer/otp")
    Call<JsonObject> loginwithPhone(@Query("mobile") String mobile);

    @POST("rest/V1/dapl/customer/otpverify")
    Call<JsonObject> verifyOTP(@Query("mobile") String mobile,
                               @Query("otp") String otp,
                               @Query("devicetoken") String devicetoken,
                               @Query("firebasetoken") String firebasetoken);

    @POST("rest/V1/customers")
    Call<SignUpResponse> createCustomer(@Body JsonObject sign);

    @GET("rest/V1/allcategories")
    Call<HomecategoryResponse> getAllCategory();

    @GET("rest/V1/homepage")
    Call<HomepageResponse> getHomePageData();

    @GET("rest/V1/customers/me")
    Call<UserDetailsResponse> getCustomerDetails(@Header("Authorization") String token);

    @POST("rest/V1/getproducts")
    Call<ProductListResponse> getProducts(@Body JsonObject jsonObject);

    @POST("rest/V1/dapl/customer/logout")
    Call<JsonObject> logoutCustomer(@Query("devicetoken") String devicetoken,
                                    @Query("accessoken") String accesstoken);

    @GET("rest/V1/getfilters/{categoryId}")
    Call<FilterResponse> getFilter(@Path("categoryId") int postId);


}


