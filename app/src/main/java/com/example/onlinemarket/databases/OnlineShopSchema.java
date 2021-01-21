package com.example.onlinemarket.databases;

public class OnlineShopSchema {
    public static final String NAME = "onlineShop.db";
    public static final int VERSION = 1;

    public static final class ProductTable {
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

    public static final class LocationTable{
        public static final String NAME="locationTable";

        public static final class LocationColumn{
            public static final String ID="id";
            public static final String LATITUDE="latitude";
            public static final String LONGITUDE="longitude";
            public static final String ADDRESS="address";
        }
    }
}
