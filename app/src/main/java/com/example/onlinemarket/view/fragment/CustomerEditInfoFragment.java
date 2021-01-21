package com.example.onlinemarket.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerEditInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerEditInfoFragment extends Fragment {

    public CustomerEditInfoFragment() {
        // Required empty public constructor
    }

    public static CustomerEditInfoFragment newInstance() {
        CustomerEditInfoFragment fragment = new CustomerEditInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_edit_info, container, false);
    }
}