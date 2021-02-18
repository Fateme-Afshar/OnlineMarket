package com.example.onlinemarket.databases.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.onlinemarket.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query(value = "SELECT * FROM productTable WHERE id=:id")
    LiveData<Product> get(int id);

    @Query(value = "SELECT * FROM productTable")
    LiveData<List<Product>> getList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Delete
    void delete(Product product);

    @Update
    void update(Product product);

    @Query(value = "DELETE FROM productTable")
    void deleteAll();
}
