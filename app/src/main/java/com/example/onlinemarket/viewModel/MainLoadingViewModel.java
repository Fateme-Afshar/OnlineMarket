package com.example.onlinemarket.viewModel;

import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.repository.MainLoadingRepository;

import java.util.List;

import javax.inject.Inject;

public class MainLoadingViewModel{
    private MainLoadingRepository mRepository;

    @Inject
    public MainLoadingViewModel(MainLoadingRepository repository) {
        mRepository = repository;
    }

    public void setColorAttributeInfoList(List<AttributeInfo> colorAttributeInfoList) {
        mRepository.setColorAttributeInfoList(colorAttributeInfoList);
    }

    public void setSizeAttributeInfoList(List<AttributeInfo> sizeAttributeInfoList) {
        mRepository.setSizeAttributeInfoList(sizeAttributeInfoList);
    }
}
