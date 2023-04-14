package com.pentathlon.pentathlon.fragmnets;

import static com.pentathlon.pentathlon.util.DataClass.categoryList;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.adapter.HomePageCategoryAdapter;
import com.pentathlon.pentathlon.databinding.CategoryFragmentBinding;
import com.pentathlon.pentathlon.databinding.FragmentHomeBinding;

public class CategoryFragment extends Fragment {
    CategoryFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = requireActivity().getWindow();
       // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

//        window.setStatusBarColor(requireActivity().getColor(R.color.primarycolor));
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        requireActivity().getWindow().getDecorView().setSystemUiVisibility(0);
        requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.primarycolor));
        binding = CategoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.categoryRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        binding.categoryRecyclerView.setAdapter(new HomePageCategoryAdapter(categoryList, getActivity()));
    }
}
