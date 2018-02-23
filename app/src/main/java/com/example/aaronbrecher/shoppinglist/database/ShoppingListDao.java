package com.example.aaronbrecher.shoppinglist.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.aaronbrecher.shoppinglist.model.ShoppingList;

import java.util.List;

import javax.inject.Singleton;

/**
 * Created by aaronbrecher on 2/18/18.
 */
@Dao
public interface ShoppingListDao {

    @Insert
    void insertAll(List<ShoppingList> lists);

    @Insert
    long insertList(ShoppingList list);

    @Query("SELECT * FROM shoppinglist ORDER BY date")
    LiveData<List<ShoppingList>> getShoppingLists();

    @Query("SELECT * FROM shoppinglist WHERE name = :listName")
    ShoppingList getShoppingList(String listName);
    @Query("DELETE FROM shoppinglist")
    void deleteAll();

    @Delete
    void deleteList(ShoppingList list);
}
