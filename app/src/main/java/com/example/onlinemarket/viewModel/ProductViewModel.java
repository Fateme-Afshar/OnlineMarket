package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.text.Editable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.ProductPurchasedRepository;
import com.example.onlinemarket.repository.ReviewRepository;

public class ProductViewModel extends AndroidViewModel {
    private Product mProduct;
    private Review mReview;

    private ProductPurchasedRepository mPurchasedRepository;

    private ReviewRepository mReviewRepository;

    {
        mPurchasedRepository = OnlineShopApplication.getProductPurchasedRepository();
        mReviewRepository = ReviewRepository.getInstance();
        mReview = new Review();
    }

    public ProductViewModel(@NonNull Application application) {
        super(application);
    }

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    public void onAddCartBtnClickListener() {
        mPurchasedRepository.insert(mProduct);
    }

    public void afterTextChangeReview(Editable editable) {
        mReview.setReview(editable.toString());
    }

    public void afterTextChangeReviewer(Editable editable) {
        mReview.setReviewer(editable.toString());
    }

    public void afterTextChangeRate(Editable editable) {
        int rate = Integer.parseInt(editable.toString());

        if (rate > 5)
            mReview.setRating(5);
        if (rate < 0)
            mReview.setRating(1);

        mReview.setRating(rate);
    }

    public void onPostCommentClickListener() {
        Customer customer = OnlineShopApplication.getsCustomer();

        if (customer==null)
            Toast.makeText(
                    getApplication(),
                    "ابتدا وارد حساب کاربری خود شوید" ,
                    Toast.LENGTH_LONG).show();

        mReview.setReviewerEmail(customer.getEmail());
        mReview.setProductId(mProduct.getId());

        mReviewRepository.postReviewToServer(mReview);
        Toast.
                makeText(getApplication(),
                        "دیدگاه شما با موفقیت ثبت شد و در دست بررسی است" ,
                        Toast.LENGTH_LONG).
                show();
    }

    public String getNormalText(String text) {
        if (text.length() <= 20)
            return text;
        else
            return text.substring(0, 21) + "...";
    }

}
