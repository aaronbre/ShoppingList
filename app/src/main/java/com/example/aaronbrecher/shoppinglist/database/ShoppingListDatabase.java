package com.example.aaronbrecher.shoppinglist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.aaronbrecher.shoppinglist.model.ListItem;
import com.example.aaronbrecher.shoppinglist.model.ShoppingList;

/**
 * Created by aaronbrecher on 2/18/18.
 */

@Database(entities = {ShoppingList.class, ListItem.class}, version = 1)
public abstract class ShoppingListDatabase extends RoomDatabase {

    private static ShoppingListDatabase instance;

    public abstract ShoppingListDao shoppingListDao();
    public abstract ListItemDao listItemDao();

    public static ShoppingListDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ShoppingListDatabase.class, "shopping-list-database")
                    .build();
        }
        return instance;
    }

    public static void destroyInstance(){
        instance = null;
    }
}
