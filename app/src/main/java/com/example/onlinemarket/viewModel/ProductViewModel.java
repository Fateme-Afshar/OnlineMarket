package com.example.onlinemarket.viewModel;

import android.text.Editable;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.ProductPurchasedRepository;
import com.example.onlinemarket.repository.ReviewRepository;

public class ProductViewModel extends ViewModel {
    private Product mProduct;
    private Review mReview;

    private ProductPurchasedRepository mPurchasedRepository;

    private ReviewRepository mReviewRepository;

    {
        mPurchasedRepository = OnlineShopApplication.getProductPurchasedRepository();
        mReviewRepository = ReviewRepository.getInstance();
        mReview = new Review();
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
        mReview.setReviewerEmail(customer.getEmail());
        mReview.setProductId(mProduct.getId());

        mReviewRepository.postReviewToServer(mReview);
    }

    public String getNormalText(String text) {
        if (text.length() <= 20)
            return text;
        else
            return text.substring(0, 21) + "...";
    }

}
