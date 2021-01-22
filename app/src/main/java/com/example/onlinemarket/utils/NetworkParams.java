package com.example.onlinemarket.utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {
    public static final String BASE_URL =
            "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONS_KEY = "" +
            "ck_373d2b7f68efab7ceba67788d017dfcc8e80cab6";
    public static final String CONS_SECRET =
            "cs_45ceb9de983308785f5affc3b5648a48101b4b67";

    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";

    public static final Map<String, String> MAP_KEYS =
            new HashMap<String, String>() {{
                put("consumer_key", CONS_KEY);
                put("consumer_secret", CONS_SECRET);
            }};

    public static Map<String, String>
    queryForReceiveSpecificCategoryProduct(int catId) {
        Map<String, String> MAP_PARAMETERS_CATEGORY = new HashMap<>();

        MAP_PARAMETERS_CATEGORY.putAll(MAP_KEYS);
        MAP_PARAMETERS_CATEGORY.put("category", String.valueOf(catId));

        return MAP_PARAMETERS_CATEGORY;
    }

    public static Map<String,String> queryForReceiveProduct(Map<String,String> queryParameter){
        Map<String,String> MAP_PARAMETERS=new HashMap<>();

        MAP_PARAMETERS.putAll(MAP_KEYS);
        MAP_PARAMETERS.putAll(queryParameter);

        return MAP_PARAMETERS;
    }

    public static Map<String ,String> queryForFindCustomer(String email){
        Map<String,String> MAP_PARAMETERS=new HashMap<>();

        MAP_PARAMETERS.putAll(MAP_KEYS);
        MAP_PARAMETERS.put(QueryParameters.ATTRIBUTE,"email");
        MAP_PARAMETERS.put(QueryParameters.SEARCH,email);

        return MAP_PARAMETERS;
    }

    @NotNull
    public static Map<String, String> querySearch(String title, String search) {
        Map<String,String> queryParameter=new HashMap<>();
        queryParameter.putAll(MAP_KEYS);
        queryParameter.put("attribute",title);
        queryParameter.put("search",search);
        return queryParameter;
    }
}
