package com.example.onlinemarket.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.onlinemarket.databases.OnlineShopSchema;
import com.example.onlinemarket.databases.OnlineShopSchema.LocationTable.LocationColumn;

@Entity(tableName = OnlineShopSchema.LocationTable.NAME)
public class CustomerLocation {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LocationColumn.ID)
    private int id;
    @ColumnInfo(name = LocationColumn.ADDRESS)
    private String address;
    @ColumnInfo(name = LocationColumn.LATITUDE)
    private double latitude;
    @ColumnInfo(name = LocationColumn.LONGITUDE)
    private double longitude;

    public CustomerLocation() {
    }

    public CustomerLocation(double latitude, double longitude,String address) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CustomerLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
