package com.example.onlinemarket.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digikalaapp.R;
import com.example.digikalaapp.adapter.CatAdapter;
import com.example.digikalaapp.databinding.FragmentCategoriesBinding;
import com.example.digikalaapp.model.CategoriesModel;
import com.example.digikalaapp.viewModel.NetworkTaskViewModel;

import java.util.List;

public class CategoriesFragment extends Fragment {
    private FragmentCategoriesBinding mBinding;
    private CatAdapter mCatAdapter;

    private CategoriesFragmentCallbacks mCallbacks;

    private NetworkTaskViewModel mViewModel;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static com.example.digikalaapp.view.fragment.CategoriesFragment newInstance() {
        com.example.digikalaapp.view.fragment.CategoriesFragment fragment = new com.example.digikalaapp.view.fragment.CategoriesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof  CategoriesFragmentCallbacks)
            mCallbacks=(CategoriesFragmentCallbacks) context;
        else
            throw new ClassCastException(
                    "Must implement CategoriesFragmentCallbacks interface");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel=new ViewModelProvider(this).get(NetworkTaskViewModel.class);

        if (mViewModel.checkNetworkState()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<CategoriesModel>categoriesModelList=
                            mViewModel.requestToServerForCategories();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUI(categoriesModelList);
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_categories,
                container,
                false);
        return mBinding.getRoot();
    }

    private void setupAdapter(List<CategoriesModel> models) {
        if (mCatAdapter==null) {
            mCatAdapter = new CatAdapter(getContext());
            mCatAdapter.setCategoriesModels(models);
            mBinding.recyclerView.setAdapter(mCatAdapter);
            mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            mCatAdapter.setCallbacks(new CatAdapter.CatAdapterCallbacks() {
                @Override
                public void OnSelectedItem(int catId) {
                    mCallbacks.onCatSelected(catId);
                }
            });
        }else {
            mCatAdapter.setCategoriesModels(models);
            mCatAdapter.notifyDataSetChanged();
        }
    }

    public void updateUI(List<CategoriesModel> models){
        if (mCatAdapter==null)
            setupAdapter(models);
        else {
            mCatAdapter.setCategoriesModels(models);
            mCatAdapter.notifyDataSetChanged();
        }
    }

    public interface CategoriesFragmentCallbacks{
        void onCatSelected(int catId);
    }
}