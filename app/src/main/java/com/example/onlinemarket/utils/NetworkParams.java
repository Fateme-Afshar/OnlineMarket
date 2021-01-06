package com.example.onlinemarket.utils;

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

    public static Map<String, String> queryForReceivePages(int pageNumber) {
        Map<String, String> MAP_KEYS_PAGE = new HashMap<>();

        MAP_KEYS_PAGE.putAll(MAP_KEYS);
        MAP_KEYS_PAGE.put("page", String.valueOf(pageNumber));

        return MAP_KEYS_PAGE;
    }

    public static Map<String, String>
    queryForReceiveSpecificCategoryProduct(int catId, int pageNumber) {
        Map<String, String> MAP_PARAMETERS_CATEGORY = new HashMap<>();

        MAP_PARAMETERS_CATEGORY.putAll(MAP_KEYS);
        MAP_PARAMETERS_CATEGORY.put("category", String.valueOf(catId));
        MAP_PARAMETERS_CATEGORY.put("page", String.valueOf(pageNumber));

        return MAP_PARAMETERS_CATEGORY;
    }

    public static Map<String,String> queryForReceiveProduct(Map<String,String> queryParameter){
        Map<String,String> MAP_PARAMETERS=new HashMap<>();

        MAP_PARAMETERS.putAll(MAP_KEYS);
        MAP_PARAMETERS.putAll(queryParameter);

        return MAP_PARAMETERS;
    }
}