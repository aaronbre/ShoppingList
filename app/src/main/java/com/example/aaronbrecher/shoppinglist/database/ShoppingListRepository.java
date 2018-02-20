package com.example.aaronbrecher.shoppinglist.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.aaronbrecher.shoppinglist.model.ShoppingList;

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
public class ShoppingListRepository {

    private ShoppingListDao shoppingListDao;

    @Inject
    public ShoppingListRepository(ShoppingListDao shoppingListDao) {
        this.shoppingListDao = shoppingListDao;
    }

    public void insertAllLists(List<ShoppingList> lists){
        shoppingListDao.insertAll(lists);
    }

    public void insertAllLists(ShoppingList... lists){
        shoppingListDao.insertAll(lists);
    }

    public LiveData<List<ShoppingList>> getAllShoppingLists(){
        return shoppingListDao.getShoppingLists();
    }
}
