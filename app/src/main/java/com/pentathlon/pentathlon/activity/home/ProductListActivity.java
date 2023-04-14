package com.pentathlon.pentathlon.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.Retrofit.ApiList;
import com.pentathlon.pentathlon.Retrofit.RetrofitManager;
import com.pentathlon.pentathlon.adapter.PaginationAdapter;
import com.pentathlon.pentathlon.customviews.PaginationScrollListener;
import com.pentathlon.pentathlon.databinding.ActivityProductDetialsBinding;
import com.pentathlon.pentathlon.databinding.ActivityProductListBinding;
import com.pentathlon.pentathlon.models.productList.DataItem;
import com.pentathlon.pentathlon.models.productList.ProductListResponse;
import com.pentathlon.pentathlon.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity implements PaginationAdapter.LayoutClick {
    int cat_id;
    String header;
    ActivityProductListBinding binding;
    private PaginationAdapter paginationAdapter;
    private ApiList apiList;
    private ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cat_id = getIntent().getIntExtra("ID", 0);
        header = getIntent().getStringExtra("header");
        binding.header.setText(header);

        apiList = RetrofitManager.getInstance().getMyApi(false, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        paginationAdapter = new PaginationAdapter(this);
        paginationAdapter.onClick(this);
        binding.recyclerProductList.setLayoutManager(gridLayoutManager);
        binding.recyclerProductList.setAdapter(paginationAdapter);
        //  getProducts();
        // makeJSONforAPI();
        binding.recyclerProductList.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                binding.progressBarBottom.setVisibility(View.VISIBLE);
                binding.rlProgress.setVisibility(View.VISIBLE);
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadFirstPage();
        binding.llFilter.setOnClickListener(view -> {
            startActivity(new Intent(ProductListActivity.this, FilterActivity.class));
        });

    }

    private void loadNextPage() {

        JsonObject object = new JsonObject();
        object.addProperty("queryString", "");
        object.addProperty("page", String.valueOf(currentPage));
        object.addProperty("size", "6");
        //object.addProperty("category_id", String.valueOf(cat_id));
        object.addProperty("category_id", "");

        apiList.getProducts(object).enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                binding.progressBarBottom.setVisibility(View.GONE);
                binding.rlProgress.setVisibility(View.GONE);

                paginationAdapter.removeLoadingFooter();
                isLoading = false;

                List<DataItem> results = response.body().getData();
                paginationAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) paginationAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void loadFirstPage() {
        JsonObject object = new JsonObject();
        object.addProperty("queryString", "");
        object.addProperty("page", String.valueOf(PAGE_START));
        object.addProperty("size", "6");
        //object.addProperty("category_id", String.valueOf(cat_id));
        object.addProperty("category_id", "");

        apiList.getProducts(object).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                List<DataItem> results = response.body().getData();
                binding.progressBar.setVisibility(View.GONE);
                paginationAdapter.addAll(results);
                TOTAL_PAGES = response.body().getPages();

                if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {

            }

        });
    }

    @Override
    public void onSubCatClick(DataItem item) {
        startActivity(new Intent(this, ProductDetials.class)
                .putExtra("sku", item.getSku()));
    }
}