package com.pentathlon.pentathlon.adapter;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.databinding.ImageItemBinding;
import com.pentathlon.pentathlon.databinding.OderhistoryitemBinding;
import com.pentathlon.pentathlon.util.CameraManagerActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;

public class ReviewImageAdapter extends RecyclerView.Adapter<ReviewImageAdapter.ViewHolder> {


    private Context context;
    private List<String> images;
    LayoutClick click;

    public ReviewImageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ReviewImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageItemBinding binding = ImageItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ReviewImageAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewImageAdapter.ViewHolder holder, final int position) {

//
//        Picasso.with(context)
//                .load(images.get(position))
//                .transform(new Transformation() {
//                    @Override
//                    public Bitmap transform(Bitmap source) {
//                        // Get the orientation of the image from its metadata
//                        ExifInterface exif;
//                        try {
//                            exif = new ExifInterface(images.get(position));
//                        } catch (IOException e) {
//                            return source;
//                        }
//                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//                        // Rotate the image based on its orientation value
//                        Matrix matrix = new Matrix();
//                        switch (orientation) {
//                            case ExifInterface.ORIENTATION_ROTATE_90:
//                                matrix.postRotate(90);
//                                break;
//                            case ExifInterface.ORIENTATION_ROTATE_180:
//                                matrix.postRotate(180);
//                                break;
//                            case ExifInterface.ORIENTATION_ROTATE_270:
//                                matrix.postRotate(270);
//                                break;
//                        }
//                        Bitmap rotatedBitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
//
//                        // Recycle the source bitmap
//                        if (rotatedBitmap != source) {
//                            source.recycle();
//                        }
//
//                        return rotatedBitmap;
//                    }
//
//                    @Override
//                    public String key() {
//                        return "rotate";
//                    }
//                })
//                .into(holder.binding.img);
//        Bitmap bitmap = decodeSampledBitmapFromUri(images.get(position), 300, 300); // Use a maximum width/height of 300 pixels
//        holder.binding.img.setImageBitmap(bitmap);
        Uri uri = Uri.parse(images.get(position));
        holder.binding.img.setImageURI(uri);


        holder.binding.imgDelete.setOnClickListener(view -> {
            click.onlayoutClick(images.get(position));
            // images.remove(position);
            // notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageItemBinding binding;

        public ViewHolder(@NonNull ImageItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
//            binding.imgDelete.setOnClickListener(
//                    view -> {
//                        images.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                      //  click.onlayoutClick(getAdapterPosition());
//                    }
//            );
        }
    }

    public interface LayoutClick {
        void onlayoutClick(String position);
    }

    public void onClick(LayoutClick click) {
        this.click = click;
    }
}
