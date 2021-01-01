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

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CategoryAdapter;
import com.example.onlinemarket.databinding.FragmentCategoriesBinding;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.view.IOnBackPress;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.List;

public class CategoriesFragment extends Fragment implements IOnBackPress {
    private FragmentCategoriesBinding mBinding;
    private CategoryAdapter mCatAdapter;

    private CategoriesFragmentCallbacks mCallbacks;

    private NetworkTaskViewModel mViewModel;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Category> categories=mViewModel.requestToServerForCategories();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(categories);
                    }
                });
            }
        }).start();

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

    private void setupAdapter(List<Category> models) {
        if (mCatAdapter==null) {
            mCatAdapter = new CategoryAdapter(getContext(),models);
            mBinding.recyclerView.setAdapter(mCatAdapter);
            mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            mCatAdapter.setCallbacks(new CategoryAdapter.CatAdapterCallbacks() {
                @Override
                public void OnSelectedItem(int catId) {
                    mCallbacks.onCatSelected(catId);
                }
            });
        }else {
            mCatAdapter.setCategories(models);
            mCatAdapter.notifyDataSetChanged();
        }
    }

    public void updateUI(List<Category> models){
        if (mCatAdapter==null)
            setupAdapter(models);
        else {
            mCatAdapter.setCategories(models);
            mCatAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    public interface CategoriesFragmentCallbacks{
        void onCatSelected(int catId);
    }
}