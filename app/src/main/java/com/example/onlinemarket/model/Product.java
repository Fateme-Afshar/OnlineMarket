package com.example.onlinemarket.model;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.onlinemarket.R;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

public class Product extends Observable implements Serializable {
    private int mId;
    private String mName;
    private String permaLink;
    private String mDateCreated;
    private String mDescription;
    private long mPrice;
    private long mRegularPrice;
    private List<String> mImgUrls;

    public Product(int id) {
        mId=id;
    }

    public Product(int id,
                   String name,
                   String permaLink,
                   String dateCreated,
                   String description,
                   long price,
                   long regularPrice,
                   List<String> imgUrls) {
        mId = id;
        mName = name;
        mDescription=description;
        this.permaLink = permaLink;
        mDateCreated = dateCreated;
        mPrice = price;
        mRegularPrice = regularPrice;
        mImgUrls = imgUrls;
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

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }

    public long getPrice() {
        return mPrice;
    }

    public void setPrice(long price) {
        mPrice = price;
    }

    public long getRegularPrice() {
        return mRegularPrice;
    }

    public void setRegularPrice(long regularPrice) {
        mRegularPrice = regularPrice;
    }

    public List<String> getImgUrls() {
        return mImgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        mImgUrls = imgUrls;
    }

    public String getDescription() {
        return "<div style='dir:rtl;' >"+mDescription+"</div>";
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @BindingAdapter("setImage")
    public static void loadImage(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext()).
                load(imgUrl).
                placeholder(R.drawable.img_place_holder).
                into(imageView);
    }

    @BindingAdapter("showDescription")
    public static void setDescription(WebView webView,String description){
        webView.loadDataWithBaseURL(null,
                description,
                "text/html",
                "utf-8",
                null);
    }
}
