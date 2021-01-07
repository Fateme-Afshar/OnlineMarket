package com.example.onlinemarket.databases;

public class OnlineShopSchema {
    public static final String NAME = "onlineShop.db";
    public static final int VERSION = 1;

    public static final class Product {
        public static final String NAME = "productTable";

        public static final class ProductColumn {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String PERMA_LINK = "permaLink";
            public static final String DATE_CREATED = "dateCreated";
            public static final String PRICE = "price";
            public static final String REGULAR_PRICE = "regularPrice";
            public static final String IMAGES_URL = "imagesUrl";
        }
    }

}
