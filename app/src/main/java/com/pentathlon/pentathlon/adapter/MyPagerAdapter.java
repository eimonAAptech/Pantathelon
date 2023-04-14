package com.pentathlon.pentathlon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.pentathlon.pentathlon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private List<String> imageUrls;
    private LayoutInflater layoutInflater;

    public MyPagerAdapter(Context context, List<String> imageUrls) {
        this.imageUrls = imageUrls;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        Picasso.with(container.getContext()).load(imageUrls.get(position)).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
