package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.OnlineMarketRepository;

import java.util.List;
import java.util.Map;


public class NetworkTaskViewModel extends AndroidViewModel {
    private OnlineMarketRepository mOnlineMarketRepository;
    private LiveData<List<Product>> mProductLiveData;

    public NetworkTaskViewModel(@NonNull Application application) {
        super(application);

        mOnlineMarketRepository = OnlineMarketRepository.getInstance();
        mProductLiveData=mOnlineMarketRepository.getProductLiveData() ;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkNetworkState(){
        ConnectivityManager connectivityManager=getApplication().
                getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo.isConnected();
    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap){
        mOnlineMarketRepository.requestToServerForReceiveProducts(queryMap);
    }

    public LiveData<List<Product>> getProductLiveData() {
        return mProductLiveData;
    }

    /*    public LiveData<List<ProductModel>> getProductListLiveData() {
        mCategoryListLiveData=mDigiKalaRepository.getCategoryListMutableLiveData();
        return mProductListLiveData;
    }

    public LiveData<List<CategoriesModel>> getCategoryListLiveData() {
        mCategoryListLiveData=mDigiKalaRepository.getCategoryListMutableLiveData();
        return mCategoryListLiveData;
    }*/

}
