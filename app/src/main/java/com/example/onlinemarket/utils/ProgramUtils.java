package com.example.onlinemarket.utils;

import com.example.onlinemarket.model.customer.Billing;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.customer.Links;
import com.example.onlinemarket.model.customer.Shipping;

import java.util.ArrayList;

public class ProgramUtils {
    public static final String TAG = "OnlineShop";
    public static final String TEST_TAG="TEST_ONLINE_APP";

    public static Customer customerTesting(){
      Links links=new Links(new ArrayList<>(),
                new ArrayList<>());
        Billing billing=new Billing("Iran",
                "Zanjan",
                "9034213778",
                "",
                "","",
                "afshar",
                "utab",
                "",
                "fateme",
                "utab2019@yahoo.com");
        Shipping shipping=new Shipping("Turkish",
                "Ezmir",
                "","",
                "",
                "omidi",
                "utab",
                "",
                "mehrdad");
        return new Customer(links,billing,shipping,"djdjfdjfhdkhfkdfh","utabbbbbbbbbb",
                "afshar",
                "fateme",
                "ljlkhkhh@gmail.com");

    }
}
