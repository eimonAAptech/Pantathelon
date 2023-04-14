package com.pentathlon.pentathlon.activity.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.activity.Logins.PhoneNumberLoginActivity;
import com.pentathlon.pentathlon.databinding.ActivityHomePageBinding;

import com.pentathlon.pentathlon.util.PreferenceManager;
import com.pentathlon.pentathlon.util.Util;

public class HomePageActivity extends AppCompatActivity {
    ActivityHomePageBinding binding;
    PreferenceManager preferenceManager;
    /// NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primarycolor));
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .add(R.id.nav_host_fragment, HomeFragment.class, null)
//                .addToBackStack(null) // name can be null
//                .commit();

        setContentView(binding.getRoot());

        // navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        preferenceManager = new PreferenceManager(this);
        binding.llCart.setOnClickListener(view -> {

            if (preferenceManager.checkkey("token")) {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                if (navController.getCurrentDestination().getId() != R.id.frag_cart) {
                    navController.popBackStack();
                    navController.navigate(R.id.frag_cart);
                    changebottomsheetcolor();
                }
            } else {
                Util.createAlertDialog(this, "Please Login", PhoneNumberLoginActivity.class);
            }


        });
        binding.llCategory.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            if (navController.getCurrentDestination().getId() != R.id.frag_category) {
                navController.popBackStack();
                navController.navigate(R.id.frag_category);
                changebottomsheetcolor();
            }
        });
        binding.llhome.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            if (navController.getCurrentDestination().getId() != R.id.frag_home) {
                navController.popBackStack();
                navController.navigate(R.id.frag_home);
                changebottomsheetcolor();
            }
        });
//
        binding.llProfile.setOnClickListener(view -> {
            if (preferenceManager.checkkey("token")) {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                if (navController.getCurrentDestination().getId() != R.id.frag_profile) {
                    navController.popBackStack();
                    navController.navigate(R.id.frag_profile);
                    changebottomsheetcolor();
                }
            } else {
                Util.createAlertDialog(this, "Please Login", PhoneNumberLoginActivity.class);
            }
        });
        binding.llWishList.setOnClickListener(view -> {
            if (preferenceManager.checkkey("token")) {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                if (navController.getCurrentDestination().getId() != R.id.frag_wishlist) {
                    navController.popBackStack();
                    navController.navigate(R.id.frag_wishlist);
                    //  changebottomsheetcolor();
                }
            } else {
                Util.createAlertDialog(this, "Please Login", PhoneNumberLoginActivity.class);
            }


            Navigation.findNavController(this, R.id.nav_host_fragment).addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                    switch (navDestination.getId()) {
                        case R.id.frag_wishlist:
                        case R.id.frag_cart:
                            changebottomsheetcolor();
                            break;
                    }
                }
            });

//            FragmentManager fragmentManager = getSupportFragmentManager();
//            Fragment myfrag = fragmentManager.findFragmentById(R.id.nav_host_fragment);
//            if (myfrag instanceof WishListFragment)
//                fragmentManager.popBackStackImmediate();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.nav_host_fragment, WishListFragment.class, null)
//                    .addToBackStack(null) // name can be null
//                    .commit();

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        changebottomsheetcolor();
    }

    @Override
    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//            finish();
//        }

        if (Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId() != R.id.frag_home) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.popBackStack();
            navController.navigate(R.id.frag_home);
            changebottomsheetcolor();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NonConstantResourceId"})
    private void changebottomsheetcolor() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        switch (navController.getCurrentDestination().getId()) {
            case R.id.frag_profile:
                binding.llhome.getBackground().setTint(getColor(R.color.white));
                binding.imgProfile.setImageDrawable(getDrawable(R.drawable.orange_profile));
                binding.category.setImageDrawable(getDrawable(R.drawable.category));
                binding.imgWishlist.setImageDrawable(getDrawable(R.drawable.heart));
                // binding.imgProfile.setImageDrawable(getDrawable(R.drawable.profile));
                binding.imgCart.setImageDrawable(getDrawable(R.drawable.shopping_cart));
                binding.txtProfile.setTextColor(getColor(R.color.orange));
                binding.txtCart.setTextColor(getColor(R.color.primarycolor));
                binding.txtCattegory.setTextColor(getColor(R.color.primarycolor));
                binding.txtWishlist.setTextColor(getColor(R.color.primarycolor));

                break;
            case R.id.frag_home:
                binding.llhome.getBackground().setTint(getColor(R.color.orange));
                binding.imgProfile.setImageDrawable(getDrawable(R.drawable.profile));
                binding.category.setImageDrawable(getDrawable(R.drawable.category));
                binding.imgWishlist.setImageDrawable(getDrawable(R.drawable.heart));
                //  binding.imgProfile.setImageDrawable(getDrawable(R.drawable.profile));
                binding.imgCart.setImageDrawable(getDrawable(R.drawable.shopping_cart));
                binding.txtProfile.setTextColor(getColor(R.color.primarycolor));
                binding.txtCart.setTextColor(getColor(R.color.primarycolor));
                binding.txtCattegory.setTextColor(getColor(R.color.primarycolor));
                binding.txtWishlist.setTextColor(getColor(R.color.primarycolor));
                break;
            case R.id.frag_category:
                binding.llhome.getBackground().setTint(getColor(R.color.white));
                binding.imgProfile.setImageDrawable(getDrawable(R.drawable.profile));
                binding.category.setImageDrawable(getDrawable(R.drawable.category_orange));
                binding.imgWishlist.setImageDrawable(getDrawable(R.drawable.heart));
                //  binding.imgProfile.setImageDrawable(getDrawable(R.drawable.profile));
                binding.imgCart.setImageDrawable(getDrawable(R.drawable.shopping_cart));
                binding.txtProfile.setTextColor(getColor(R.color.primarycolor));
                binding.txtCart.setTextColor(getColor(R.color.primarycolor));
                binding.txtCattegory.setTextColor(getColor(R.color.orange));
                binding.txtWishlist.setTextColor(getColor(R.color.primarycolor));
                break;
            case R.id.frag_wishlist:
                binding.llhome.getBackground().setTint(getColor(R.color.white));
                binding.imgProfile.setImageDrawable(getDrawable(R.drawable.profile));
                binding.category.setImageDrawable(getDrawable(R.drawable.category));
                binding.imgWishlist.setImageDrawable(getDrawable(R.drawable.orange_heart));
                //  binding.imgProfile.setImageDrawable(getDrawable(R.drawable.profile));
                binding.imgCart.setImageDrawable(getDrawable(R.drawable.shopping_cart));

                binding.txtProfile.setTextColor(getColor(R.color.primarycolor));
                binding.txtCart.setTextColor(getColor(R.color.primarycolor));
                binding.txtCattegory.setTextColor(getColor(R.color.primarycolor));
                binding.txtWishlist.setTextColor(getColor(R.color.orange));
                break;
            case R.id.frag_cart:
                binding.llhome.getBackground().setTint(getColor(R.color.white));
                binding.imgProfile.setImageDrawable(getDrawable(R.drawable.profile));
                binding.category.setImageDrawable(getDrawable(R.drawable.category));
                binding.imgWishlist.setImageDrawable(getDrawable(R.drawable.heart));
                //  binding.imgProfile.setImageDrawable(getDrawable(R.drawable.profile));
                binding.imgCart.setImageDrawable(getDrawable(R.drawable.cart_orange));
                binding.txtProfile.setTextColor(getColor(R.color.primarycolor));
                binding.txtCart.setTextColor(getColor(R.color.orange));
                binding.txtCattegory.setTextColor(getColor(R.color.primarycolor));
                binding.txtWishlist.setTextColor(getColor(R.color.primarycolor));
                break;
        }


    }

}