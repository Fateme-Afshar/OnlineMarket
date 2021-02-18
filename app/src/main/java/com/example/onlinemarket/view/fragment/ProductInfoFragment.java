package com.example.onlinemarket.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ReviewAdapter;
import com.example.onlinemarket.databinding.FragmentProductInfoBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.utils.LoadingUtils;
import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.utils.UiUtils;
import com.example.onlinemarket.view.slider.ImageSlider;
import com.example.onlinemarket.viewModel.ProductViewModel;
import com.example.onlinemarket.viewModel.ReviewViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductInfoFragment extends Fragment{
    public static final int REQUEST_CODE_EDIT = 1;
    public static final String TAG_PRODUCT_INFO_FRAGMENT = "ProductInfoFragment";
    private FragmentProductInfoBinding mBinding;

    private ProductViewModel mProductViewModel;
    private ReviewViewModel mReviewViewModel;

    private ReviewAdapter mReviewAdapter;

    private ImageSlider mImageSlider;

   private int mProductId;

    public ProductInfoFragment() {
        // Required empty public constructor
    }

    public static ProductInfoFragment newInstance() {
        ProductInfoFragment fragment = new ProductInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("CheckResult")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductViewModel = new ViewModelProvider(getActivity()).
                get(ProductViewModel.class);

        if (getArguments() != null) {
            mProductId = ProductInfoFragmentArgs.fromBundle(getArguments()).getProductId();
            Observable<Product> productObservable =
                    mProductViewModel.requestToServerForReceiveProductById(mProductId);

            productObservable.subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(product -> {
                        mProductViewModel.setProduct(product);
                        setupLoadProduct();
                    }, throwable -> {
                        Log.e(ProgramUtils.TAG, throwable.getMessage());
                    });
        }
        mReviewViewModel = new ViewModelProvider(this).
                get(ReviewViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_product_info,
                container,
                false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode!= Activity.RESULT_OK || data==null)
            return;
        if (requestCode==REQUEST_CODE_EDIT){
            mReviewViewModel.requestToReceiveProductReview
                    (data.getIntExtra(EditReviewBottomSheetFragment.EXTRA_REVIEW_ID,0));

            mReviewViewModel.getReviewLiveData().observe(this, new Observer<Review>() {
                @Override
                public void onChanged(Review review) {
                    //TODO : just update this review
                    setupReviewsForProduct();
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupLoadProduct() {
        if (LoadingUtils.checkHasInternet(getActivity().getSystemService(ConnectivityManager.class))) {
            setupReviewsForProduct();

            if (mProductViewModel.getProduct() != null){
                mImageSlider = new ImageSlider(mBinding.imgSlider);
                mImageSlider.startSlider(mProductViewModel.getProduct().getImgUrls());
            }
            mBinding.setViewModel(mProductViewModel);
        }else {
            UiUtils.returnToast(getContext(),"اینترنت قطع است");
        }
    }

    private void setupReviewAdapter(List<Review> reviews) {
        mReviewAdapter=new ReviewAdapter(reviews,mReviewViewModel,getActivity());

        mBinding.recyclerViewReviewer.setAdapter(mReviewAdapter);
        mBinding.recyclerViewReviewer.setLayoutManager
                (new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        true));

        if (reviews.size()==0)
            mBinding.tvFirstComment.setVisibility(View.VISIBLE);
        else
            mBinding.tvFirstComment.setVisibility(View.GONE);
    }

    private void setupReviewsForProduct() {
        mReviewViewModel.
                requestToReceiveProductReviewList(mProductId);
        mReviewViewModel.getListLiveData().observe(getActivity(), new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                setupReviewAdapter(reviews);
            }
        });

        mReviewViewModel.setCallback(new ReviewViewModel.ReviewViewModelCallback() {
            @Override
            public void onDeleteReviewClickListener(int reviewId) {
                ProductInfoFragment.this.onDeleteReviewClickListener(reviewId);
            }

            @Override
            public void onEditBtnClickListener(Review review) {
                alertBottomSheetDialog(review);
            }
        });
    }

    private void alertBottomSheetDialog(Review review) {
        EditReviewBottomSheetFragment fragment=
                EditReviewBottomSheetFragment.newInstance(review);

        //create parent-child relationship between ProductInfoFragment and EditReviewBottomSheetFragment
        fragment.setTargetFragment(ProductInfoFragment.this, REQUEST_CODE_EDIT);

        fragment.show(ProductInfoFragment.this.getParentFragmentManager(),
                TAG_PRODUCT_INFO_FRAGMENT);
    }

    private void onDeleteReviewClickListener(int reviewId) {
        AlertDialog questionDialog = new AlertDialog.Builder(getContext()).
                setTitle("مطمئنی میخوای حذف کنی پیام ملتو؟ ").
                setIcon(R.drawable.ic_luncher).
                setPositiveButton("حذفش کن بره", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mReviewViewModel.deleteReview(reviewId);
                        mReviewViewModel.requestToReceiveProductReviewList(mProductViewModel.getProduct().getId());

                        Toast.
                                makeText(getActivity(),"این چه کاری بود عاخههه؟" ,Toast.LENGTH_LONG).
                                show();
                    }
                }).
                setNegativeButton("خو حالا بزار باشه",null).
                create();

        questionDialog.show();
    }
}