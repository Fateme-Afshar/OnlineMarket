package com.example.onlinemarket.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.onlinemarket.R;

public class Category {
    private int mId;
    private String mName;
    private String mImageUrl;

    public Category(int id, String name, String imagePath) {
        mId = id;
        mName = name;
        mImageUrl = imagePath;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView imageView, String imgUrl){
        Glide.with(imageView.getContext()).
                load(imgUrl).
                placeholder(R.drawable.img_place_holder).
                into(imageView);
    }
}
