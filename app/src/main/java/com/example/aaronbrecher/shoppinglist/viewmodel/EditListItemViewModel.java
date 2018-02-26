package com.example.aaronbrecher.shoppinglist.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.database.ListItemRepository;
import com.example.aaronbrecher.shoppinglist.model.ListItem;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * Created by aaronbrecher on 2/22/18.
 */

public class EditListItemViewModel extends ViewModel {

    private ListItemRepository mListItemRepository;
    private String listName;
    private int itemId = -1;
    private SharedPreferences sharedPreferences;

    //refrence to the set from sharedPrefrences will be used to update sharedPrefrences
    //when a new item is added..
    private Set<String> categorySet;

    @Inject
    public EditListItemViewModel(ListItemRepository listItemRepository, SharedPreferences sharedPreferences) {
        mListItemRepository = listItemRepository;
        this.sharedPreferences = sharedPreferences;
        categorySet = sharedPreferences.getStringSet("itemCategories", new HashSet<String>());
    }



    public long addNewListItem(ListItem item){
        item.setListName(listName);
        if(itemId != -1) item.setId(itemId);
        return mListItemRepository.insertListItem(item);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Set<String> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<String> categorySet) {
        this.categorySet = categorySet;
    }

    /**
     * Will update the shared preferences to include all the added categories
     * doing this in onCleared so we don't have to update after every new Category add
     */
    @Override
    protected void onCleared() {

        super.onCleared();
    }
}
