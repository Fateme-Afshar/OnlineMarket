package com.example.onlinemarket.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.response.CatObj;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryRepository {
    private static CategoryRepository sInstance;
    private RetrofitInterface mRetrofitInterface;

    private MutableLiveData<List<Category>> mCategoryLiveData=new MutableLiveData<>();

    private CategoryRepository() {
        Retrofit retrofit=RetrofitInstance.getRetrofit();

        mRetrofitInterface=retrofit.create(RetrofitInterface.class);
    }

    public static CategoryRepository getInstance() {
        if (sInstance==null)
            sInstance=new CategoryRepository();
        return sInstance;
    }

    public List<Category> requestToServerForCategories() {
        List<Category> categoriesList = new ArrayList<>();
        Retrofit retrofit =RetrofitInstance.getRetrofit();

        int page = 1;

        mRetrofitInterface = retrofit.create(RetrofitInterface.class);

        List<CatObj> catObjList = null;
        try {
            catObjList = getCatObjs(page);

            while (catObjList.size() != 0) {

                for (CatObj catObj : catObjList) {
                    Category categoriesModel = new Category(
                            catObj.getId(),
                            catObj.getName(),
                            catObj.getImage().getSrc());

                    categoriesList.add(categoriesModel);
                }

                catObjList = getCatObjs(++page);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return categoriesList;
    }

    private  List<CatObj> getCatObjs(int pageNumber) throws IOException {
        Log.d(ProgramUtils.TAG, "Start request to Server for receive Categories");

        Call<List<CatObj>> catObjects = mRetrofitInterface.
                getListCatObjects("categories", NetworkParams.queryForReceivePages(pageNumber));
        Response<List<CatObj>> response = catObjects.execute();

        return response.body();
    }

    public MutableLiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }
}
