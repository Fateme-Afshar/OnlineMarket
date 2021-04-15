package com.example.onlinemarket.viewModel;

import android.text.Editable;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.R;
import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.model.Coupons;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.orders.LineItemsItem;
import com.example.onlinemarket.model.orders.Orders;
import com.example.onlinemarket.repository.CouponsRepository;
import com.example.onlinemarket.repository.ProductPurchasedRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CartViewModel extends ViewModel {
    private ProductPurchasedRepository mPurchasedRepository ;
    private CouponsRepository mCouponsRepository;
    private ContextModule mContextModule;

    private LiveData<Coupons> mCouponsLiveData;

    private int mTotalPrice=0;

    private List<Product> mProductList=new ArrayList<>();

    private LifecycleOwner mLifecycleOwner;

    private String mCouponCode="";

    private Orders mOrders;
    private Customer mCustomer;

    @Inject
    public CartViewModel(ContextModule contextModule,ProductPurchasedRepository productPurchasedRepository,CouponsRepository couponsRepository) {
        mCustomer= OnlineShopSharePref.getCustomer(contextModule.provideContext().getApplicationContext());
        mPurchasedRepository=productPurchasedRepository;
        mCouponsRepository=couponsRepository;

        mContextModule=contextModule;

        mCouponsLiveData=mCouponsRepository.getCouponsMutableLiveData();
    }


    public LiveData<List<Product>> getProductRegisteredForPurchase(){
        return mPurchasedRepository.getList();
    }

    public void onBuyBtnClickListener() {
        if (mCustomer == null) {
                UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),"ابتدا وارد حساب کاربری خود شوید");
            return;
        }
        List<LineItemsItem> productLines = new ArrayList<>();

        for (Product product : mProductList) {
            LineItemsItem lineItemsItem = new LineItemsItem(
                    String.valueOf(product.getRegularPrice()),
                    (int) product.getPrice(),
                    product.getId(),
                    product.getName());

            productLines.add(lineItemsItem);
        }
        mOrders = new Orders
                (String.valueOf(mTotalPrice),
                        mCustomer.getId(),
                        productLines,
                        mCustomer.getUsername());

        postOrdersToServer(mOrders);
    }

    public void onRecordCouponBtnClickListener(){
        if (mCouponCode.equals("")) {
            UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),"لطفا کد تخفیف را وارد کنید");
            return;
        }
        mCouponsRepository.searchCouponsCode(mCouponCode);

        mCouponsLiveData.observe(mLifecycleOwner, new Observer<Coupons>() {
            @Override
            public void onChanged(Coupons coupons) {
                if (coupons==null || !mCouponCode.equals(coupons.getCode())){
                    UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),"کد تخفیف نامعتبر است ");
                }else{
                    if (mTotalPrice<coupons.getAmount() ||
                            mTotalPrice<coupons.getMinimumAmount() ||
                            mTotalPrice > coupons.getMaximumAmount()){
                        UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),"به خرید شما کد تخفیف تعلق نمیگیرد");
                    }else {
                        mTotalPrice=mTotalPrice-coupons.getAmount();
                        UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),"تخفیف با موفقیت اعمال شد");
                    }
                }
            }
        });
    }

    private void postOrdersToServer(Orders orders) {
        mPurchasedRepository.postOrdersToServer(orders);
        mPurchasedRepository.getResponseCode().observe(mLifecycleOwner, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==201) {
                    Log.d(ProgramUtils.TAG,
                            "CartViewModel : Orders post successfully" + integer);
                   UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),R.string.order_success);

                    mPurchasedRepository.deleteAll();
                } else {
                    Log.e(ProgramUtils.TAG,
                            "CartViewModel : Orders post fail response code is  " + integer);
                    UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),R.string.order_fail);
                }
            }
        });
    }

    public void afterTextChangeCoupon(Editable editable) {
            mCouponCode = editable.toString();
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public void setTotalPrice(int totalPrice){
        mTotalPrice=totalPrice;
    }

    public String getTotalPrice() {
        return String.valueOf(mTotalPrice);
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }
}
