package com.pentathlon.pentathlon.fragmnets;

import static com.pentathlon.pentathlon.util.DataClass.bannersItemList;
import static com.pentathlon.pentathlon.util.DataClass.bestsellerItemList;
import static com.pentathlon.pentathlon.util.DataClass.bestsellingbrandsItemList;
import static com.pentathlon.pentathlon.util.DataClass.categoryList;
import static com.pentathlon.pentathlon.util.DataClass.newproductsItems;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayoutMediator;
import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.Retrofit.RetrofitManager;
import com.pentathlon.pentathlon.adapter.BestSellingProductAdapter;
import com.pentathlon.pentathlon.adapter.HomePageCategoryAdapter;
import com.pentathlon.pentathlon.adapter.ImageAdapter;
import com.pentathlon.pentathlon.adapter.MyPagerAdapter;
import com.pentathlon.pentathlon.adapter.NewArrivalAdapter;
import com.pentathlon.pentathlon.adapter.SellingBrandAdapter;
import com.pentathlon.pentathlon.databinding.FragmentHomeBinding;
import com.pentathlon.pentathlon.models.homecategory.ChildrenDataItem;
import com.pentathlon.pentathlon.models.homecategory.HomecategoryResponse;
import com.pentathlon.pentathlon.models.homepage.BannersItem;
import com.pentathlon.pentathlon.models.homepage.BestsellerItem;
import com.pentathlon.pentathlon.models.homepage.BestsellingbrandsItem;
import com.pentathlon.pentathlon.models.homepage.HomepageResponse;
import com.pentathlon.pentathlon.models.homepage.NewproductsItem;
import com.pentathlon.pentathlon.util.DataClass;
import com.pentathlon.pentathlon.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    private MyPagerAdapter adapter;
    private Timer timer;

    //    private Handler handler;
//    private Runnable runnable;
//    List<BannersItem> bannersItemList;
//    List<ChildrenDataItem> categoryList;
//    List<NewproductsItem> newproductsItems;
//    List<BestsellerItem> bestsellerItemList;
//    List<BestsellingbrandsItem> bestsellingbrandsItemList;
    private int dotsCount;
    private ImageView[] dots;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        Window window = requireActivity().getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(requireActivity().getColor(R.color.white));

        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.white));
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        categoryList = new ArrayList<>();
//        bannersItemList = new ArrayList<>();
//        newproductsItems = new ArrayList<>();
//        bestsellerItemList = new ArrayList<>();
//        bestsellingbrandsItemList = new ArrayList<>();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        {
            if (categoryList.size() <= 0) {
                getCategory();
                getHomePageData();
            } else {
                setCategory();
                if (!bannersItemList.isEmpty())
                    setBanners();
                setNewProducts();
                setbestSeller();
                setSellingBrand();
            }
        }
//        binding.btn.setOnClickListener(view1 -> {
//            Navigation.findNavController(view1).navigate(R.id.action_frag_home_to_frag_demo);
//        });
    }

    private void getCategory() {
        RetrofitManager.getInstance().getMyApi(false, getActivity()).getAllCategory().enqueue(new Callback<HomecategoryResponse>() {
            @Override
            public void onResponse(Call<HomecategoryResponse> call, Response<HomecategoryResponse> response) {
                Log.d("", response.body().toString());

                categoryList = response.body().getChildrenData();
                setCategory();

            }

            @Override
            public void onFailure(Call<HomecategoryResponse> call, Throwable t) {
                Util.dismiss();
            }
        });

    }

    private void setCategory() {
        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        binding.categoryRecyclerView.setAdapter(new HomePageCategoryAdapter(categoryList, getActivity()));
    }


    private void getHomePageData() {
        RetrofitManager.getInstance().getMyApi(true, getActivity()).getHomePageData().enqueue(new Callback<HomepageResponse>() {
            @Override
            public void onResponse(Call<HomepageResponse> call, Response<HomepageResponse> response) {
                Util.dismiss();
                bannersItemList = response.body().getBanners();
                newproductsItems = response.body().getNewproducts();
                bestsellerItemList = response.body().getBestseller();
                bestsellingbrandsItemList = response.body().getBestsellingbrands();
                if (!bannersItemList.isEmpty())
                    setBanners();
                setNewProducts();
                setbestSeller();
                setSellingBrand();
                getsponsor(response.body().getSponsers().get(0).getImage());
            }

            @Override
            public void onFailure(Call<HomepageResponse> call, Throwable t) {
                Util.dismiss();
            }
        });
    }

    private void setSellingBrand() {
        binding.recyclerBestBrand.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        SellingBrandAdapter adapter1 = new SellingBrandAdapter(requireActivity(), bestsellingbrandsItemList);
        binding.recyclerBestBrand.setAdapter(adapter1);

    }

    private void setbestSeller() {
        binding.recyclerBestProducts.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        BestSellingProductAdapter adapter1 = new BestSellingProductAdapter(bestsellerItemList, requireActivity());
        binding.recyclerBestProducts.setAdapter(adapter1);
    }

    private void getsponsor(String url) {
        String url2 = Util.imgUrl + url;
        Picasso.with(requireActivity())
                .load(url2)
                .placeholder(requireActivity().getDrawable(R.drawable.demoimg))
                .into(binding.imgSponsor);
    }

    private void setNewProducts() {
        binding.recyclerNewArrials.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));
        NewArrivalAdapter newArrivalAdapter = new NewArrivalAdapter(newproductsItems, requireActivity());
        binding.recyclerNewArrials.setAdapter(newArrivalAdapter);
    }

    private void updateDots(int currentPage) {
        for (int i = 0; i < dotsCount; i++) {
            if (i == currentPage) {
                if (requireActivity().getApplicationContext() != null) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.dot_selected));
                } else {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.dot_unselected));
                }
            }
        }
    }

    private Handler handler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                int currentPosition = binding.viewPager.getCurrentItem();
                int nextPosition = currentPosition == Objects.requireNonNull(binding.viewPager.getAdapter()).getItemCount() - 1 ? 0 : currentPosition + 1;
                binding.viewPager.setCurrentItem(nextPosition);
                handler.postDelayed(this, 1000);
            } catch (Exception e) {

            }

        }
    };

    private void setBanners() {

        ImageAdapter adapter = new ImageAdapter(bannersItemList, getActivity());
        binding.viewPager.setAdapter(adapter);
        dotsCount = adapter.getItemCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.dot_unselected));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, // width in pixels
                    15  // height in pixels
            );
            params.setMargins(8, 0, 8, 0);
            binding.dotsLayout.addView(dots[i], params);
            dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.dot_selected));

            binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    updateDots(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        startAutoSlide();
                    } else if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                        stopAutoSlide();
                    }
                }
            });
            handler = new Handler();
            handler.postDelayed(runnable, 1000);
        }
    }

    private void startAutoSlide() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        runnable = () -> {
            int currentItem = binding.viewPager.getCurrentItem();
            int itemCount = binding.viewPager.getAdapter().getItemCount();

            if (currentItem < itemCount - 1) {
                binding.viewPager.setCurrentItem(currentItem + 1);
            } else {
                binding.viewPager.setCurrentItem(0);
            }

            handler.postDelayed(runnable, 1000);
        };

        handler.postDelayed(runnable, 1000);
    }

    private void stopAutoSlide() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove any pending posts of the Handler to avoid memory leaks
        // handler.removeCallbacks(runnable);
        stopAutoSlide();
        handler.removeCallbacksAndMessages(null);
    }

}