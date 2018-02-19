package com.example.aaronbrecher.shoppinglist.database;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.aaronbrecher.shoppinglist.model.ListItem;

import java.util.List;

/**
 * Created by aaronbrecher on 2/18/18.
 * An abstraction class over the DAO's to do the database queries. This will allow us to change the
 * underlying database without changing code throughout the app. If the backend is changed all that needs
 * to be changed is this file. As of now the actual functions are the exact same. This will be useful when
 * we switch to Firebase...
 */

public class ListItemRepository {

    private Context context;
    private ListItemDao listItemDao = ShoppingListDatabase.getInstance(context).listItemDao();

    public ListItemRepository(Context context) {
        this.context = context;
    }

    public LiveData<ListItem> getListItemForId(int id){
        return listItemDao.findById(id);
    }

    public LiveData<List<ListItem>> getListItemForShoppingList(String listName){
        return listItemDao.findItemsForList(listName);
    }

    public void insertListItems(List<ListItem> items){
        listItemDao.insertAll(items);
    }

    public void insertListItems(ListItem... items){
        listItemDao.insertAll(items);
    }
}
