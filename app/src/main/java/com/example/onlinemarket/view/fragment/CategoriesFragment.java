package com.example.onlinemarket.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CategoryAdapter;
import com.example.onlinemarket.databinding.FragmentCategoriesBinding;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.viewModel.CategoryViewModel;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.List;

public class CategoriesFragment extends Fragment{
    private FragmentCategoriesBinding mBinding;
    private CategoryAdapter mCatAdapter;

    private CategoriesFragmentCallbacks mCallbacks;

    private CategoryViewModel mViewModel;

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

        mViewModel=new ViewModelProvider(this).get(CategoryViewModel.class);
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
        setupAdapter(mViewModel.getCategoryList());
        setupBackButton();
        return mBinding.getRoot();
    }

    private void setupAdapter(List<Category> models) {
        if (mCatAdapter==null) {
            mCatAdapter = new CategoryAdapter(getContext(),models);
            mBinding.recyclerView.setAdapter(mCatAdapter);
            mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

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

    private void setupBackButton() {
        OnBackPressedCallback onBackPressedCallback=new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController=
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment);

                navController.navigate(R.id.nav_home);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }

    public interface CategoriesFragmentCallbacks{
        void onCatSelected(int catId);
    }
}