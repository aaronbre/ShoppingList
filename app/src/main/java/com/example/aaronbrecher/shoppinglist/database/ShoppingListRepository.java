package com.example.aaronbrecher.shoppinglist.database;

import android.content.Context;

import com.example.aaronbrecher.shoppinglist.model.ShoppingList;

import java.util.List;

/**
 * Created by aaronbrecher on 2/18/18.
 * An abstraction class over the DAO's to do the database queries. This will allow us to change the
 * underlying database without changing code throughout the app. If the backend is changed all that needs
 * to be changed is this file. As of now the actual functions are the exact same. This will be useful when
 * we switch to Firebase...
 */

public class ShoppingListRepository {

    private Context context;
    private ShoppingListDao shoppingListDao = ShoppingListDatabase.getInstance(context).shoppingListDao();

    public ShoppingListRepository(Context context) {
        this.context = context;
    }

    public void insertAllLists(List<ShoppingList> lists){
        shoppingListDao.insertAll(lists);
    }

    public void insertAllLists(ShoppingList... lists){
        shoppingListDao.insertAll(lists);
    }

    public void getAllShoppingLists(){
        shoppingListDao.getShoppingLists();
    }
}
