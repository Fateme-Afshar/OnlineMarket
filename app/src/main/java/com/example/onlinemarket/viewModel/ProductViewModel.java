package com.example.onlinemarket.viewModel;

import android.text.Editable;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.repository.MainLoadingRepository;
import com.example.onlinemarket.repository.ProductPurchasedRepository;
import com.example.onlinemarket.repository.ProductRepository;
import com.example.onlinemarket.repository.ReviewRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.UiUtils;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ProductViewModel extends ViewModel {
    private Product mProduct;
    private Product mLastProduct = new Product();

    private Review mReview;

    private ProductPurchasedRepository mPurchasedRepository;
    private ProductRepository mProductRepository;
    private ReviewRepository mReviewRepository;

    private ContextModule mContextModule;

    @Inject
    public ProductViewModel(ContextModule contextModule,
                            ProductPurchasedRepository productPurchasedRepository,
                            ReviewRepository reviewRepository,
                            ProductRepository productRepository) {
        mPurchasedRepository = productPurchasedRepository;
        mReviewRepository = reviewRepository;
        mProductRepository = productRepository;
        mContextModule=contextModule;
        mReview = new Review();
    }


    public Observable<Product> requestToServerForReceiveProductById(int productId){
        return mProductRepository.requestToServerForReceiveProductById(productId);
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
        Customer customer = OnlineShopSharePref.getCustomer(mContextModule.provideContext());

        if (customer==null) {
          UiUtils.returnToast(mContextModule.provideContext(),"ابتدا وارد حساب کاربری خود شوید");
            return;
        }

        mReview.setReviewerEmail(customer.getEmail());
        mReview.setProductId(mProduct.getId());

        mReviewRepository.postReviewToServer(mReview);
       UiUtils.returnToast(mContextModule.provideContext(),"دیدگاه شما با موفقیت ثبت شد و در دست بررسی است");
    }
}
