package com.example.onlinemarket.model;

public class Categorie {
    private int mId;
    private String mName;
    private String mImagePath;

    public Categorie(int id, String name, String imagePath) {
        mId = id;
        mName = name;
        mImagePath = imagePath;
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

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }
}
