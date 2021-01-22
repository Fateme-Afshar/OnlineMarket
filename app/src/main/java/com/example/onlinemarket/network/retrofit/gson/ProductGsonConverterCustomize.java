package com.example.onlinemarket.network.retrofit.gson;

import com.example.onlinemarket.model.Product;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductGsonConverterCustomize implements JsonDeserializer<Product> {
    @Override
    public Product deserialize(JsonElement json,
                                     Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObjProduct = json.getAsJsonObject();
        List<String> imgUrls = new ArrayList<>();

        int id = jsonObjProduct.get("id").getAsInt();
        float averageRating=jsonObjProduct.get("average_rating").getAsFloat();
        String name = jsonObjProduct.get("name").getAsString();
        String permaLink = jsonObjProduct.get("permalink").getAsString();
        String dateCreated = jsonObjProduct.get("date_created").getAsString();
        String description = jsonObjProduct.get("description").getAsString();
        long price;
        long regularPrice;
        JsonArray jsonArrayImages;
        try {
            price = jsonObjProduct.get("price").getAsLong();
            regularPrice = jsonObjProduct.get("regular_price").getAsLong();
            jsonArrayImages = jsonObjProduct.get("images").getAsJsonArray();
        } catch (Exception e) {
            price = 0;
            regularPrice = 0;
            jsonArrayImages=new JsonArray();
        }


        for (JsonElement jsonImgElem : jsonArrayImages) {
            JsonObject jsonObjectImg = jsonImgElem.getAsJsonObject();

            String url = jsonObjectImg.get("src").getAsString();

            imgUrls.add(url);
        }

        Product productModel = new Product(
                id,
                name,
                permaLink,
                dateCreated,
                description,
                price,
                regularPrice,
                imgUrls,
                averageRating);
        return productModel;
    }
}
