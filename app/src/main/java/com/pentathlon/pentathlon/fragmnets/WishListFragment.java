package com.pentathlon.pentathlon.fragmnets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.adapter.WishListAdapter;
import com.pentathlon.pentathlon.databinding.CategoryFragmentBinding;
import com.pentathlon.pentathlon.databinding.WishlistFragmentBinding;

public class WishListFragment extends Fragment {
    WishlistFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(requireActivity().getColor(R.color.primarycolor));
        binding = WishlistFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerWishlist.setLayoutManager(new LinearLayoutManager(requireContext()));
        WishListAdapter adapter = new WishListAdapter(requireActivity());
        binding.recyclerWishlist.setAdapter(adapter);

    }
}
