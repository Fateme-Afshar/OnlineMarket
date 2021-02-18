package com.example.onlinemarket.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.network.retrofit.gson.ProductGsonConverterCustomize;
import com.example.onlinemarket.network.retrofit.gson.ProductListGsonConverterCustomize;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.utils.Titles;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ProductRepository {
    private static ProductRepository sInstance;
    private MainLoadingRepository mMainLoadingRepository;

    private List<Product> mNewestProductList = new ArrayList<>();
    private List<Product> mPopulateProductList = new ArrayList<>();
    private List<Product> mBestProductList = new ArrayList<>();
    private List<Product> mSpecialProductList = new ArrayList<>();
    private List<Product> mProducts = new ArrayList<>();

    private Product mProduct=new Product();

    private RetrofitInterface mRetrofitInterface;

    private MutableLiveData<Boolean> mIsCompleteProducts =new MutableLiveData<>();

    private boolean[] flags=new boolean[4];

    private ProductRepository() {
        Retrofit retrofit = RetrofitInstance.getRetrofit
                (new TypeToken<List<Product>>() {
                        }.getType()
                        , new ProductListGsonConverterCustomize());
        mRetrofitInterface=retrofit.create(RetrofitInterface.class);
        mMainLoadingRepository=MainLoadingRepository.getInstance();
    }

    public static ProductRepository getInstance() {
        if (sInstance == null)
            sInstance = new ProductRepository();
        return sInstance;
    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap, Titles title) {
        Observable<List<Product>> observable = getListCall(queryMap);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Product>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Product> productList) {
                setProductsForEveryPart(productList, title);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(ProgramUtils.TAG,"ProductRepository : Fail receive Product.");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void setProductsForEveryPart(List<Product> products, Titles title) {
        switch (title) {
            case BEST_PRODUCT:
                mMainLoadingRepository.setBestProductList(products);
                flags[0]=true;
                isCompleteLoading();
                break;
            case NEWEST_PRODUCT:
                mMainLoadingRepository.setNewestProductList(products);
                flags[1]=true;
                isCompleteLoading();
                break;
            case MORE_REVIEWS_PRODUCT:
                mMainLoadingRepository.setPopulateProductList(products);
                flags[2]=true;
                isCompleteLoading();
                break;
            case SPECIAL_PRODUCT:
                mMainLoadingRepository.setSpecialProductList(products);
                flags[3]=true;
                isCompleteLoading();
            default:
                break;
        }
    }

    private void isCompleteLoading(){
        if (flags[0]&&flags[1]&&flags[2]&&flags[3])
            mIsCompleteProducts.setValue(true);
    }

    public MutableLiveData<Boolean> isCompleteProducts() {
        return mIsCompleteProducts;
    }

    public Observable<List<Product>> requestToServerForReceiveProducts(Map<String, String> queryMap) {
       return getListCall(queryMap);
    }

    public void setProducts(List<Product> products) {
        mProducts.addAll(products);
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    @SuppressLint("CheckResult")
    public Observable<Product> requestToServerForReceiveProductById(int productId){
        Retrofit retrofit=RetrofitInstance.getRetrofit
                (Product.class,new ProductGsonConverterCustomize());
        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);

        return retrofitInterface.
                getProduct(productId,NetworkParams.MAP_KEYS);

        /*retrofitInterface.getProduct(productId,NetworkParams.MAP_KEYS).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(this::setProduct,throwable -> Log.e(ProgramUtils.TAG,"ProductRepository : Fail receive Product List."));*/
    }

    private Observable<List<Product>> getListCall(Map<String, String> queryMap) {
        return mRetrofitInterface.getListProductObjects(NetworkParams.queryForReceiveProduct(queryMap));
    }

    public List<Product> getNewestProductList() {
        return mNewestProductList;
    }

    public List<Product> getPopulateProductList() {
        return mPopulateProductList;
    }

    public List<Product> getBestProductList() {
        return mBestProductList;
    }

    public List<Product> getSpecialProductList() {
        return mSpecialProductList;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public Product getProductLiveData() {
        return mProduct;
    }
}
