package com.example.onlinemarket.model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private int mId;
    private String mName;
    private String permaLink;
    private String mDateCreated;
    private String mDescription;
    private long mPrice;
    private long mRegularPrice;
    private List<String> mImgUrls;

    public Product(int id) {
        mId=id;
    }

    public Product(int id,
                   String name,
                   String permaLink,
                   String dateCreated,
                   String description,
                   long price,
                   long regularPrice,
                   List<String> imgUrls) {
        mId = id;
        mName = name;
        mDescription=description;
        this.permaLink = permaLink;
        mDateCreated = dateCreated;
        mPrice = price;
        mRegularPrice = regularPrice;
        mImgUrls = imgUrls;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }

    public long getPrice() {
        return mPrice;
    }

    public void setPrice(long price) {
        mPrice = price;
    }

    public long getRegularPrice() {
        return mRegularPrice;
    }

    public void setRegularPrice(long regularPrice) {
        mRegularPrice = regularPrice;
    }

    public List<String> getImgUrls() {
        return mImgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        mImgUrls = imgUrls;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
