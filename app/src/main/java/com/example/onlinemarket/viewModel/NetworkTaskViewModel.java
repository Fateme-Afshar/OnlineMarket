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

import javax.inject.Inject;

import io.reactivex.Observable;

public class NetworkTaskViewModel extends ViewModel {
    private final ProductRepository mProductRepository;
    private final CategoryRepository mCategoryRepository;
    private final FilterRepository mFilterRepository;

    private final List<Product> mProductList=new ArrayList<>();

    @Inject
    public NetworkTaskViewModel(ProductRepository productRepository,CategoryRepository categoryRepository,FilterRepository filterRepository) {
        mProductRepository = productRepository;
        mCategoryRepository=categoryRepository;
        mFilterRepository=filterRepository;

        mProductList.addAll(mProductRepository.getProducts());

        List<Category> categoryList = new ArrayList<>();
        categoryList.addAll(mCategoryRepository.getCategoriesList());

        List<AttributeInfo> colorAttributeInfoList = new ArrayList<>();
        colorAttributeInfoList.addAll(mFilterRepository.getColorAttributeList());
        List<AttributeInfo> sizeAttributeInfoList = new ArrayList<>();
        sizeAttributeInfoList.addAll(mFilterRepository.getSizeAttributeList());
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

    public Observable<List<Product>> requestToServerForSearchProducts(String title,String search){
        Map<String, String> queryParameter = NetworkParams.querySearch(title, search);
        return mProductRepository.requestToServerForReceiveProducts(queryParameter);
    }

    public void requestToServerForCategories(){
        mCategoryRepository.requestToServerForCategories();
    }

    public Observable<List<AttributeInfo>> requestToServerForReceiveInfoColorAttribute(){
        return mFilterRepository.requestToServerForReceiveInfoColorAttribute();
    }

    public Observable<List<AttributeInfo>> requestToServerForReceiveInfoSizeAttribute(){
        return mFilterRepository.requestToServerForReceiveInfoSizeAttribute();
    }

    public List<Product> getProductList() {
        return mProductList;
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
