package com.example.aaronbrecher.shoppinglist.database;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.aaronbrecher.shoppinglist.model.ListItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by aaronbrecher on 2/18/18.
 * An abstraction class over the DAO's to do the database queries. This will allow us to change the
 * underlying database without changing code throughout the app. If the backend is changed all that needs
 * to be changed is this file. As of now the actual functions are the exact same. This will be useful when
 * we switch to Firebase...
 */

@Singleton
public class ListItemRepository {

    ListItemDao listItemDao;

    @Inject
    public ListItemRepository(ListItemDao listItemDao) {
        this.listItemDao = listItemDao;
    }

    public LiveData<ListItem> getListItemForId(int id){
        return listItemDao.findById(id);
    }

    public LiveData<List<ListItem>> getListItemsForShoppingList(String listName){
        return listItemDao.findItemsForList(listName);
    }

    public void insertListItems(List<ListItem> items){
        listItemDao.insertAll(items);
    }

    public long insertListItem(ListItem item){
        return listItemDao.insertItem(item);
    }
}
