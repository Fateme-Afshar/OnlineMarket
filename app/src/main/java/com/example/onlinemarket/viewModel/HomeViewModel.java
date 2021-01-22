package com.example.onlinemarket.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.QueryParameters;

import java.util.HashMap;
import java.util.Map;

public class HomeViewModel extends ViewModel {

    public Map<String, String> getQueryMapNewest() {
        Map<String, String> queryMapNewest = new HashMap<>();
        queryMapNewest.put(QueryParameters.ORDER_BY, "date");
        queryMapNewest.put(QueryParameters.ORDER, NetworkParams.ORDER_DESC);
        return queryMapNewest;
    }

    public Map<String, String> getQueryMapBest() {
        Map<String, String> queryMapBest = new HashMap<>();
        queryMapBest.put(QueryParameters.ORDER_BY, "rating");
        queryMapBest.put(QueryParameters.ORDER, NetworkParams.ORDER_DESC);
        return queryMapBest;
    }

    public Map<String, String> getQueryMapPopulate() {
        Map<String, String> queryMapPopulate = new HashMap<>();
        queryMapPopulate.put(QueryParameters.ORDER_BY, "popularity");
        queryMapPopulate.put(QueryParameters.ORDER, NetworkParams.ORDER_DESC);
        return queryMapPopulate;
    }

    public Map<String, String> getQueryMapSpecial() {
        Map<String, String> queryMapSpecial = new HashMap<>();
        queryMapSpecial.put(QueryParameters.ON_SALE, "true");
        return queryMapSpecial;
    }
}
