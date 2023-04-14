package com.pentathlon.pentathlon.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.models.homecategory.ChildrenDataItem;
import com.pentathlon.pentathlon.models.productList.DataItem;
import com.pentathlon.pentathlon.util.Util;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DataItem> productlist;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private boolean isLoadingAdded = false;
    LayoutClick click;

    public PaginationAdapter(Context context) {
        this.context = context;
        productlist = new LinkedList<>();
    }

    public void setproductlist(List<DataItem> productlist) {
        this.productlist = productlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.product_list_item, parent, false);
                viewHolder = new ViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // Movie movie = productlist.get(position);
        DataItem item = productlist.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                ViewHolder viewHolder = (ViewHolder) holder;
                String url = Util.imgUrl + item.getImage();
                Glide.with(context).load(url).placeholder(context.getDrawable(R.drawable.demoimg))
                        .into(viewHolder.img_product);
                viewHolder.product_name.setText(item.getName());
                if (Double.parseDouble(item.getRating()) <= 0) {
                    viewHolder.txt_star.setVisibility(View.GONE);
                } else {
                    viewHolder.txt_star.setText(context.getResources().getString(R.string.star) + item.getRating());
                    viewHolder.txt_star.setVisibility(View.VISIBLE);
                }
                if (item.getSpecialPrice().isEmpty()) {
                    //   String newValue = Double.toString(Math.floor(val1));
                    ;

                    viewHolder.txt_discount_price.setText(Util.getPrice(item.getPrice()));
                    viewHolder.txt_actual_price.setVisibility(View.GONE);
                } else {
                    double val1 = Double.parseDouble(item.getPrice());
                    //  String newValue1 = Double.toString(Math.floor(val1));

                    double val2 = Double.parseDouble(item.getSpecialPrice());
                    //  String newValue2 = Double.toString(Math.floor(val2));

                    viewHolder.txt_actual_price.setText(Util.getPrice(item.getPrice()));
                    viewHolder.txt_discount_price.setText(Util.getPrice(item.getSpecialPrice()));
                    viewHolder.txt_actual_price.setVisibility(View.VISIBLE);
                    viewHolder.txt_actual_price.setPaintFlags
                            (viewHolder.txt_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                viewHolder.rl_layout.setOnClickListener(view -> {
                    click.onSubCatClick(item);
                });
//                ViewHolder ViewHolder = (ViewHolder) holder;
//                ViewHolder.movieTitle.setText(movie.getTitle());
//                Glide.with(context).load(movie.getImageUrl()).apply(RequestOptions.centerCropTransform()).into(ViewHolder.movieImage);
                break;

            case LOADING:
                //    LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                //  loadingViewHolder.progressBar.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return productlist == null ? 0 : productlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == productlist.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new DataItem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = productlist.size() - 1;
        DataItem result = getItem(position);

        if (result != null) {
            productlist.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(DataItem movie) {
        productlist.add(movie);
        notifyItemInserted(productlist.size() - 1);
    }

    public void addAll(List<DataItem> moveResults) {
        for (DataItem result : moveResults) {
            add(result);
        }
    }

    public DataItem getItem(int position) {
        return productlist.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView product_name, txt_discount_price, txt_actual_price, txt_star;
        private ImageView img_product;
        RelativeLayout rl_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.product_name);
            img_product = (ImageView) itemView.findViewById(R.id.img_product);
            txt_discount_price = (TextView) itemView.findViewById(R.id.txt_discount_price);
            txt_actual_price = (TextView) itemView.findViewById(R.id.txt_actual_price);
            txt_star = (TextView) itemView.findViewById(R.id.txt_star);
            rl_layout = itemView.findViewById(R.id.rl_layout);

        }
    }

    public interface LayoutClick {
        void onSubCatClick(DataItem item);
    }

    public void onClick(LayoutClick click) {
        this.click = click;
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);

        }
    }


}