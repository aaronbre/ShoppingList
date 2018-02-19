package com.example.aaronbrecher.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by aaronbrecher on 2/18/18.
 * Model for a ListItem in the shopping list. This will be its own table with a refrence key to
 * the ShoppingList table to map all items to the proper list.
 * Each Item has 5 member variables
 * id - the id of the ListItem
 * itemName - the name of the ListItem
 * quantity - amount of the items needed
 * notes - any additional info about the item for ex. Brand etc. will be a simple string
 * listName - the link to the list the item belongs to will correspond to the name of the list
 */

@Entity(foreignKeys = @ForeignKey(entity = ShoppingList.class,
        parentColumns = "name",
        childColumns = "list_name"))
public class ListItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    @ColumnInfo
    String itemName;

    @ColumnInfo
    int quantity;

    @ColumnInfo
    String notes;

    @ColumnInfo(name = "list_name")
    String listName;

    // A full constructer to use when creating the item - will not be used by Room
    @Ignore
    public ListItem(@NonNull int id, String itemName, int quantity, String notes, String listName) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.notes = notes;
        this.listName = listName;
    }

    //empty constructer to be used by room
    public ListItem() {
    }

    //GETTERS AND SETTERS
    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
