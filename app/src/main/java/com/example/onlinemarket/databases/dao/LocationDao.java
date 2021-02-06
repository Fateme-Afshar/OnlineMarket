package com.example.onlinemarket.databases.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.model.Product;

import java.util.List;

@Dao
public interface LocationDao {
    @Query(value = "SELECT * FROM locationTable WHERE id=:id")
    LiveData<CustomerLocation> get(int id);

    @Query(value = "SELECT * FROM locationTable")
    LiveData<List<CustomerLocation>> getList();

    @Insert
    void insert(CustomerLocation location);

    @Delete
    void delete(CustomerLocation location);

    @Update
    void update(CustomerLocation location);

    @Query(value = "DELETE FROM locationTable")
    void deleteAll();
}
