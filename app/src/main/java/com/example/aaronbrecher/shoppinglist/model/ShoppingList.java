package com.example.aaronbrecher.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Model for a shoppingList object to be used to create a table in the Room database
 * each Shopping list has 3 member variables :
 * name - is the name of the Shopping list ex. Grocery this will also be the PrimaryKey and will not allow
 * two lists with the same name
 * description - a quick description of the list ex. Grocery list for shoprite...
 */

@Entity
public class ShoppingList {

    @PrimaryKey
    @NonNull
    private String name;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private long date;


    // A constructer in case we want to use the list object out of room
    public ShoppingList(String name, String description, long date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public ShoppingList() {
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
