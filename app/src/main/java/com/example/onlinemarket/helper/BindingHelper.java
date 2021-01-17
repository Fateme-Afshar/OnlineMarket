package com.example.onlinemarket.helper;

import android.graphics.Paint;
import android.text.Html;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.onlinemarket.R;

public class BindingHelper {

    @BindingAdapter("setImage")
    public static void loadImage(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext()).
                load(imgUrl).
                placeholder(R.drawable.img_place_holder).
                into(imageView);
    }

    @BindingAdapter("showDescription")
    public static void setDescription(TextView textView, String description){
        textView.setText(HtmlCompat.fromHtml(description,0));
    }

    @BindingAdapter("strikeThrough")
    public static void strikeThrough(TextView textView, Boolean strikeThrough) {
        if (strikeThrough) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

}
