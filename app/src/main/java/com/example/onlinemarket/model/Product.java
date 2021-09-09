package com.example.onlinemarket.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.onlinemarket.databases.OnlineShopSchema;
import com.example.onlinemarket.databases.OnlineShopSchema.ProductTable.ProductColumn;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
@Entity(tableName = OnlineShopSchema.ProductTable.NAME)
public class Product extends Observable implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = ProductColumn.ID)
    private int mId;
    @ColumnInfo(name = ProductColumn.NAME)
    private String mName;
    @ColumnInfo(name = ProductColumn.PERMA_LINK)
    private String permaLink;
    @ColumnInfo(name = ProductColumn.DATE_CREATED)
    private String mDateCreated;
    @ColumnInfo(name = ProductColumn.DESCRIPTION)
    private String mDescription;
    @ColumnInfo(name = ProductColumn.PRICE)
    private long mPrice;
    @ColumnInfo(name = ProductColumn.REGULAR_PRICE)
    private long mRegularPrice;
    @ColumnInfo(name = ProductColumn.IMAGES_URL)
    private List<String> mImgUrls;

    private float mAverageRating;

    public Product() {
    }

    @Ignore
    public Product(int id,
                   String name,
                   String permaLink,
                   String dateCreated,
                   String description,
                   long price,
                   long regularPrice,
                   List<String> imgUrls,
                   float averageRating) {

        mId = id;
        mName = name;
        this.permaLink = permaLink;
        mDateCreated = dateCreated;
        mDescription = description;
        mPrice = price;
        mRegularPrice = regularPrice;
        mImgUrls = imgUrls;
        mAverageRating = averageRating;
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
        return "<div>"+mDescription+"</div>";
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public float getAverageRating() {
        return mAverageRating;
    }

    public void setAverageRating(float averageRating) {
        mAverageRating = averageRating;
    }
}
