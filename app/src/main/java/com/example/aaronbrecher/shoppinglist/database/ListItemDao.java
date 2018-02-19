package com.example.aaronbrecher.shoppinglist.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.aaronbrecher.shoppinglist.model.ListItem;

import java.util.List;

/**
 * Created by aaronbrecher on 2/18/18.
 * The Data Access Object for the ListItem table. Will be used to query the database for both
 * single items as well as a List of items. Also will be used to insert Items into the database
 */

@Dao
public interface ListItemDao {

    //Insert a List of ListItems to the database
    @Insert
    void insertAll(List<ListItem> items);

    //Insert a few or single ListItem into the database
    @Insert
    void insertAll(ListItem... items);

    //query function for retrieving a single List item
    @Query("SELECT * FROM ListItem WHERE id = :id ORDER BY itemName")
    LiveData<ListItem> findById(int id);

    //query to retrieve all the List items for a specific list
    @Query("SELECT * FROM ListItem WHERE list_name = :listName ORDER BY itemName")
    LiveData<List<ListItem>> findItemsForList(String listName);


}
