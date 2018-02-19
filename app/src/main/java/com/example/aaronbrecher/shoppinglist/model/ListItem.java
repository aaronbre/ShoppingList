package com.example.aaronbrecher.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by aaronbrecher on 2/18/18.
 */

@Entity(foreignKeys = @ForeignKey(entity = ShoppingList.class,
        parentColumns = "id",
        childColumns = "list_id"))
public class ListItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    @ColumnInfo
    String itemName;

    @ColumnInfo
    int quantity;

    @ColumnInfo
    String Description;

    @ColumnInfo(name = "list_id")
    int listId;
}
