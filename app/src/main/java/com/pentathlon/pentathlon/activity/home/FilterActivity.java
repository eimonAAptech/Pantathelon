package com.pentathlon.pentathlon.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pentathlon.pentathlon.Retrofit.RetrofitManager;
import com.pentathlon.pentathlon.adapter.FilterChildAdapter;
import com.pentathlon.pentathlon.adapter.FilterParentAdapter;
import com.pentathlon.pentathlon.databinding.ActivityFilterBinding;
import com.pentathlon.pentathlon.models.getFilterModel.DataItem;
import com.pentathlon.pentathlon.models.getFilterModel.FilterResponse;
import com.pentathlon.pentathlon.models.getFilterModel.ValuesItem;
import com.pentathlon.pentathlon.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity implements FilterParentAdapter.LayoutClick {
    ActivityFilterBinding binding;
    FilterParentAdapter parentAdapter;
    FilterChildAdapter childAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getAllFilter();
        binding.parentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.childRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        parentAdapter = new FilterParentAdapter(this);
        parentAdapter.onClick(this::onparentClick);
        binding.parentRecycler.setAdapter(parentAdapter);
        childAdapter = new FilterChildAdapter(this);
        binding.childRecycler.setAdapter(childAdapter);
        binding.back.setOnClickListener(view -> finish());
        binding.imgClose.setOnClickListener(view -> finish());

    }

    private void getAllFilter() {
        RetrofitManager.getInstance().getMyApi(true, this).getFilter(4).enqueue(new Callback<FilterResponse>() {
            @Override
            public void onResponse(Call<FilterResponse> call, Response<FilterResponse> response) {
                Util.dismiss();
                if (response.code() == 200) {
//                            List<DataItem> list1=response.body().getData();
//                            ValuesItem item=new ValuesItem();
//                            item.setDisplay("");
//                            item.setValue("");
//                            DataItem dataItem=new DataItem();
//                            dataItem.setValues(item);

                    parentAdapter.swap(response.body().getData());
                } else {
                    Util.errorMessage(response, FilterActivity.this);
                }

            }

            @Override
            public void onFailure(Call<FilterResponse> call, Throwable t) {
                Util.dismiss();

            }
        });
    }

    @Override
    public void onparentClick(List<ValuesItem> values) {
        childAdapter.swap(values);
    }
}