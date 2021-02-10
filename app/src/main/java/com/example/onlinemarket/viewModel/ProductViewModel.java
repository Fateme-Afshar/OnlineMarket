package com.example.onlinemarket.viewModel;

import android.text.Editable;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.ProductPurchasedRepository;
import com.example.onlinemarket.repository.ProductRepository;
import com.example.onlinemarket.repository.ReviewRepository;

public class ProductViewModel extends ViewModel {
    private Product mProduct;
    private Review mReview;

    private LiveData<Product> mProductLiveData;

    private ProductPurchasedRepository mPurchasedRepository;
    private ProductRepository mProductRepository;

    private ReviewRepository mReviewRepository;

    public ProductViewModel() {
        mPurchasedRepository = OnlineShopApplication.getProductPurchasedRepository();
        mReviewRepository = ReviewRepository.getInstance();
        mProductRepository=ProductRepository.getInstance();

        mProductLiveData=mProductRepository.getProductLiveData();
        mReview = new Review();
    }

    public void requestToServerForReceiveProductById(int productId){
        mProductRepository.requestToServerForReceiveProductById(productId);
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
        Customer customer = OnlineShopApplication.getCustomer();

        if (customer==null) {
          OnlineShopApplication.getUiUtils().returnToast("ابتدا وارد حساب کاربری خود شوید");
            return;
        }

        mReview.setReviewerEmail(customer.getEmail());
        mReview.setProductId(mProduct.getId());

        mReviewRepository.postReviewToServer(mReview);
       OnlineShopApplication.getUiUtils().returnToast("دیدگاه شما با موفقیت ثبت شد و در دست بررسی است");
    }

    public LiveData<Product> getProductLiveData() {
        return mProductLiveData;
    }
}
