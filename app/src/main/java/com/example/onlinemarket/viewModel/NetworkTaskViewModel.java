package com.example.onlinemarket.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.CategoryRepository;
import com.example.onlinemarket.repository.FilterRepository;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.QueryParameters;
import com.example.onlinemarket.utils.Titles;
import com.example.onlinemarket.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NetworkTaskViewModel extends ViewModel {
    private ProductRepository mProductRepository;
    private CategoryRepository mCategoryRepository;
    private FilterRepository mFilterRepository;

    private List<Product> mNewestProductList=new ArrayList<>();
    private List<Product> mPopulateProductList=new ArrayList<>();
    private List<Product> mBestProductList=new ArrayList<>();
    private List<Product> mSpecialProductList=new ArrayList<>();
    private List<Product> mProductList=new ArrayList<>();

    private List<Category> mCategoryList=new ArrayList<>();

    private LiveData<List<AttributeInfo>> mColorAttributeInfoList;
    private LiveData<List<AttributeInfo>> mSizeAttributeInfoList;

    public NetworkTaskViewModel() {
        mProductRepository = ProductRepository.getInstance();
        mCategoryRepository=CategoryRepository.getInstance();
        mFilterRepository=FilterRepository.getInstance();

        mNewestProductList.addAll(mProductRepository.getNewestProductList()) ;
        mPopulateProductList.addAll(mProductRepository.getPopulateProductList()) ;
        mBestProductList.addAll(mProductRepository.getBestProductList());
        mSpecialProductList.addAll(mProductRepository.getSpecialProductList());
        mProductList.addAll(mProductRepository.getProducts());

        mCategoryList.addAll(mCategoryRepository.getCategoriesList());

        mColorAttributeInfoList=mFilterRepository.getColorAttributeListLiveData();
        mSizeAttributeInfoList=mFilterRepository.getSizeAttributeListLiveData();
    }

    public LiveData<Boolean> isCompleteLoadingProducts(){
        return mProductRepository.isCompleteProducts();
    }

    public LiveData<Boolean> isCompleteLoadingCategories(){
        return mCategoryRepository.isCompleteCategory();
    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap, Titles title){
        mProductRepository.requestToServerForReceiveProducts(queryMap,title);
    }

    public void requestToServerForSearchProducts(String title,String search){
        Map<String, String> queryParameter = NetworkParams.querySearch(title, search);
        mProductRepository.requestToServerForReceiveProducts(queryParameter);
    }

    public void requestToServerForCategories(){
        mCategoryRepository.requestToServerForCategories();
    }

    public void requestToServerForReceiveInfoColorAttribute(){
        mFilterRepository.requestToServerForReceiveInfoColorAttribute();
    }

    public void requestToServerForReceiveInfoSizeAttribute(){
        mFilterRepository.requestToServerForReceiveInfoSizeAttribute();
    }

    public List<Category> getCategoryList() {
        return mCategoryList;
    }

    public List<Product> geNewestProductLiveData() {
        return mNewestProductList;
    }

    public List<Product> getPopulateProductList() {
        return mPopulateProductList;
    }

    public List<Product> getBestProductList() {
        return mBestProductList;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public List<Product> getSpecialProductList() {
        return mSpecialProductList;
    }

    public LiveData<List<AttributeInfo>> getColorAttributeInfoList(){return mColorAttributeInfoList;}

    public LiveData<List<AttributeInfo>> getSizeAttributeInfoList() {
        return mSizeAttributeInfoList;
    }

    public Map<String, String> getQueryMapNewest() {
        Map<String, String> queryMapNewest = new HashMap<>();
        queryMapNewest.put(QueryParameters.ORDER_BY, QueryParameters.DATE);
        queryMapNewest.put(QueryParameters.ORDER, QueryParameters.ORDER_DESC);
        return queryMapNewest;
    }

    public Map<String, String> getQueryMapBest() {
        Map<String, String> queryMapBest = new HashMap<>();
        queryMapBest.put(QueryParameters.ORDER_BY, QueryParameters.RATE);
        queryMapBest.put(QueryParameters.ORDER, QueryParameters.ORDER_DESC);
        return queryMapBest;
    }

    public Map<String, String> getQueryMapPopulate() {
        Map<String, String> queryMapPopulate = new HashMap<>();
        queryMapPopulate.put(QueryParameters.ORDER_BY, QueryParameters.POPULARITY);
        queryMapPopulate.put(QueryParameters.ORDER, QueryParameters.ORDER_DESC);
        return queryMapPopulate;
    }

    public Map<String, String> getQueryMapSpecial() {
        Map<String, String> queryMapSpecial = new HashMap<>();
        queryMapSpecial.put(QueryParameters.ON_SALE, "true");
        return queryMapSpecial;
    }
}
