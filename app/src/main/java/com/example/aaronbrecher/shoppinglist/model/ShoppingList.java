package com.example.aaronbrecher.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by aaronbrecher on 2/18/18.
 */

@Entity
public class ShoppingList{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    int listId;

    @ColumnInfo
    String name;

    @Ignore
    public ShoppingList(int id, String name){
        listId = id;
        this.name = name;
    }

    public ShoppingList(){}

    @NonNull
    public int getListId() {
        return listId;
    }

    public void setListId(@NonNull int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
