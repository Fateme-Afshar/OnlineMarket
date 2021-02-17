package com.example.onlinemarket.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.response.CatObj;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.network.retrofit.gson.ProductListGsonConverterCustomize;
import com.example.onlinemarket.utils.NetworkParams;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CategoryRepository {
    private static CategoryRepository sInstance;
    private RetrofitInterface mRetrofitInterface;

    private MainLoadingRepository mMainLoadingRepository;

    private List<Category> mCategoriesList=new ArrayList<>();
    private List<Product> mProductList=new ArrayList<>();

    private MutableLiveData<Boolean> mIsCompleteCategory=new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProducts =new MutableLiveData<>();

    private CategoryRepository() {
        Retrofit retrofit=RetrofitInstance.getRetrofit();

        mRetrofitInterface=retrofit.create(RetrofitInterface.class);
        mMainLoadingRepository=MainLoadingRepository.getInstance();
    }

    public static CategoryRepository getInstance() {
        if (sInstance==null)
            sInstance=new CategoryRepository();
        return sInstance;
    }

    @SuppressLint("CheckResult")
    public void requestToServerForCategories() {
        Retrofit retrofit =RetrofitInstance.getRetrofit();

        mRetrofitInterface = retrofit.create(RetrofitInterface.class);

        Observable<List<CatObj>> catObjects =
                mRetrofitInterface.getListCatObjects(NetworkParams.MAP_KEYS);
        catObjects.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(this::setCatObj, throwable ->new Exception("CategoryRepository : An error occurred when receive categories "));
    }

    private void setCatObj(List<CatObj> catObjList){
            for (CatObj catObj : catObjList) {
                Category category=new Category
                        (catObj.getId(),
                                catObj.getName(),
                                catObj.getImage().getSrc());

                mCategoriesList.add(category);
        }
            mMainLoadingRepository.setCategoryList(mCategoriesList);
            mIsCompleteCategory.setValue(true);
    }

    @SuppressLint("CheckResult")
    public void requestToServerForSpecificCatProduct(int catId) {
        Retrofit retrofit =RetrofitInstance.getRetrofit(new TypeToken<List<Product>>(){}.getType()
                ,new ProductListGsonConverterCustomize());

        mRetrofitInterface = retrofit.create(RetrofitInterface.class);
        Observable<List<Product>> productObjects=
                mRetrofitInterface.getListProductObjects
                        (NetworkParams.queryForReceiveSpecificCategoryProduct(catId));
        productObjects.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(this::setProducts, throwable ->new Exception("CategoryRepository : An error occurred when receive categories "));
    }

    public List<Category> getCategoriesList() {
        return mCategoriesList;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProducts(List<Product> productList) {
        mProductList.addAll(productList);
    }

    public MutableLiveData<Boolean> isCompleteCategory() {
        return mIsCompleteCategory;
    }
}
