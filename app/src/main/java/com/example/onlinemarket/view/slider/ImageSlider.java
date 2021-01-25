package com.example.onlinemarket.view.slider;

import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.onlinemarket.R;

import java.util.List;

public class ImageSlider {
   private ViewFlipper mViewFlipper;
            
    public ImageSlider(ViewFlipper viewFlipper) {
        mViewFlipper=viewFlipper;
    }

    public  void startSlider(){
        int[] images={R.drawable.slide_1,R.drawable.slide_2,R.drawable.slide_3};

        for (int image : images) {
            flipperImages(image);
        }
    }

    public  void startSlider(List<String> imagesUrl){

        for (String image : imagesUrl) {
            flipperImages(image);
        }
    }
    
    private  void flipperImages(int image){
        ImageView imageView=new ImageView(mViewFlipper.getContext());
        imageView.setBackgroundResource(image);

        mViewFlipper.addView(imageView);
        mViewFlipper.setFlipInterval(4000);
        mViewFlipper.setAutoStart(true);

        mViewFlipper.setInAnimation(mViewFlipper.getContext(), R.anim.slide_in_left);
    }

    public  void flipperImages(String imgUrl){
        ImageView imageView=new ImageView(mViewFlipper.getContext());

        Glide.with(imageView.getContext()).
                load(imgUrl).
                into(imageView);

        mViewFlipper.addView(imageView);
        mViewFlipper.setFlipInterval(4000);
        mViewFlipper.setAutoStart(true);

        mViewFlipper.setInAnimation(mViewFlipper.getContext(), android.R.anim.slide_in_left);
        mViewFlipper.setInAnimation(mViewFlipper.getContext(), android.R.anim.slide_out_right);
    }

}
