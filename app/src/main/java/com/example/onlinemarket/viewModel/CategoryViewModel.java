package com.example.onlinemarket.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository mCategoryRepository;
    private LiveData<List<Category>> mCategoryLiveData;
    private List<Category> mCategory;

    {
        mCategoryRepository=CategoryRepository.getInstance();
        mCategoryLiveData=mCategoryRepository.getCategoryLiveData();
    }

    public LiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }

    public List<Category> getCategory() {
        return mCategory;
    }

    public void setCategory(List<Category> category) {
        mCategory = category;
    }

    public void requestToServerForCategories(){
        mCategoryRepository.requestToServerForCategories();
    }
}
