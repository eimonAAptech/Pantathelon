package com.pentathlon.pentathlon.activity.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.adapter.ReviewImageAdapter;
import com.pentathlon.pentathlon.databinding.ActivityOrderHistoryBinding;
import com.pentathlon.pentathlon.databinding.ActivityReviewBinding;
import com.pentathlon.pentathlon.util.CameraManagerActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

public class ReviewActivity extends CameraManagerActivity implements ReviewImageAdapter.LayoutClick {
    ActivityReviewBinding binding;
    private List<String> images;
    private ReviewImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerImage.setLayoutManager(new GridLayoutManager(this, 3));
        images = new ArrayList<>();
        imageAdapter = new ReviewImageAdapter(this, images);
        binding.recyclerImage.setAdapter(imageAdapter);
        binding.addPhoto.setOnClickListener(view -> {
            isCamera = false;
            onCallCameraButton();

        });
        binding.imgBack.setOnClickListener(view -> finish());

    }


    @Override
    protected void onBitmapReceivedFromCamera(MultipartBody.Part filePart) {

    }

    @Override
    protected void onBitmapReceivedFromGallery(ClipData clipData, Uri uri) {
        imageAdapter.onClick(this::onlayoutClick);
        if (clipData != null) {
            for (int i = 0; i < clipData.getItemCount(); i++) {
                Uri uri1 = clipData.getItemAt(i).getUri();
                images.add(uri1.toString());
            }
            imageAdapter.notifyDataSetChanged();
        } else if (uri != null) {
            images.add(uri.toString());
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onlayoutClick(String position) {
        // images.remove(position);
        if (images.contains(position)) {
            images.remove(position);
            imageAdapter.notifyDataSetChanged();
        }

        //notifyDataSetChanged();

    }
}