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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.adapter.CartlistAdapter;
import com.pentathlon.pentathlon.databinding.CartFragmentBinding;
import com.pentathlon.pentathlon.databinding.CategoryFragmentBinding;

public class CartFragment extends Fragment {
    CartFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(requireActivity().getColor(R.color.primarycolor));
        binding = CartFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        CartlistAdapter adapter = new CartlistAdapter(requireActivity());
        binding.recyclerCart.setAdapter(adapter);
    }
}
