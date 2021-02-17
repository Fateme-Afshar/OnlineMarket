package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.ProductPurchasedRepository;
import com.example.onlinemarket.repository.ProductRepository;
import com.example.onlinemarket.repository.ReviewRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.UiUtils;

public class ProductViewModel extends AndroidViewModel {
    private Product mProduct;
    private Product mLastProduct=new Product();

    private Review mReview;

    private LiveData<Product> mProductLiveData;

    private ProductPurchasedRepository mPurchasedRepository;
    private ProductRepository mProductRepository;

    private ReviewRepository mReviewRepository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mPurchasedRepository = ProductPurchasedRepository.getInstance(getApplication());
        mReviewRepository = ReviewRepository.getInstance();
        mProductRepository=ProductRepository.getInstance();
        mReview = new Review();
    }


    public void requestToServerForReceiveProductById(int productId){
        mProductRepository.requestToServerForReceiveProductById(productId);
    }

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mLastProduct=mProduct;
        if (!product.equals(mLastProduct))
            mProduct = product;
        else
            mProduct=null;
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
        Customer customer = OnlineShopSharePref.getCustomer(getApplication());

        if (customer==null) {
          UiUtils.returnToast(getApplication(),"ابتدا وارد حساب کاربری خود شوید");
            return;
        }

        mReview.setReviewerEmail(customer.getEmail());
        mReview.setProductId(mProduct.getId());

        mReviewRepository.postReviewToServer(mReview);
       UiUtils.returnToast(getApplication(),"دیدگاه شما با موفقیت ثبت شد و در دست بررسی است");
    }

    public Product getProductLiveData() {
        return mProductRepository.getProductLiveData();
    }
}
