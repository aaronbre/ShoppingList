package com.example.aaronbrecher.shoppinglist.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.aaronbrecher.shoppinglist.model.ShoppingList;

import java.util.List;

/**
 * Created by aaronbrecher on 2/18/18.
 */

@Dao
public interface ShoppingListDao {

    @Insert
    void insertAll(List<ShoppingList> lists);

    @Insert
    void insertAll(ShoppingList... lists);

    @Query("SELECT * FROM shoppinglist ORDER BY date")
    LiveData<List<ShoppingList>> getShoppingLists();
}
