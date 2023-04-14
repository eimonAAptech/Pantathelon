package com.pentathlon.pentathlon.fragmnets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.Retrofit.RetrofitManager;
import com.pentathlon.pentathlon.activity.home.HomePageActivity;
import com.pentathlon.pentathlon.activity.profile.AddressActivity;
import com.pentathlon.pentathlon.activity.profile.OrderHistory;
import com.pentathlon.pentathlon.databinding.CategoryFragmentBinding;
import com.pentathlon.pentathlon.databinding.ProfileFragmentBinding;
import com.pentathlon.pentathlon.util.PreferenceManager;
import com.pentathlon.pentathlon.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    ProfileFragmentBinding binding;
    PreferenceManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(0);
        requireActivity().getWindow().setStatusBarColor(requireActivity().getColor(R.color.black));
        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager = new PreferenceManager(requireActivity());
        if (manager.getData() != null) {
            String name = manager.getData().getFirstname() + " " + manager.getData().getLastname();
            binding.name.setText(name);
            binding.email.setText(manager.getData().getEmail());
            binding.phno.setText(manager.getData().getCustomAttributes().get(0).getValue());
        }

        binding.llAddress.setOnClickListener(view1 -> {
            startActivity(new Intent(requireActivity(), AddressActivity.class));
        });
        binding.llHistory.setOnClickListener(view1 -> {
            startActivity(new Intent(requireActivity(), OrderHistory.class));
        });
        binding.llLogout.setOnClickListener(view1 -> {
            logout();
        });
    }

    private void logout() {
        String accesstoken = manager.getToken();
        String device_token = manager.getDeviceId();
        RetrofitManager.getInstance().getMyApi(true, requireActivity())
                .logoutCustomer(device_token, accesstoken)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Util.dismiss();
                        if (response.code() == 200) {
                            new PreferenceManager(requireActivity()).clearData();
                            startActivity(new Intent(requireActivity(), HomePageActivity.class));
                            requireActivity().finishAffinity();
                        } else {
                            Util.errorMessage(response, requireActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }
}
