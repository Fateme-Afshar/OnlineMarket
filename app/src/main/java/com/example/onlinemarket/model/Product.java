package com.example.onlinemarket.model;

import android.graphics.Paint;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.onlinemarket.R;
import com.example.onlinemarket.databases.OnlineShopSchema;
import com.example.onlinemarket.databases.OnlineShopSchema.Product.ProductColumn;
import com.example.onlinemarket.utils.PersianNumberUtils;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
@Entity(tableName = OnlineShopSchema.Product.NAME)
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

    public Product(int id) {
        mId=id;
    }

    @Ignore
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
        return "<div>"+mDescription+"</div>";
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
