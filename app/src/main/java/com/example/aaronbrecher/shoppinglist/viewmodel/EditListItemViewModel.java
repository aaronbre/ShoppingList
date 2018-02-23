package com.example.aaronbrecher.shoppinglist.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.aaronbrecher.shoppinglist.database.ListItemRepository;
import com.example.aaronbrecher.shoppinglist.model.ListItem;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * Created by aaronbrecher on 2/22/18.
 */

public class EditListItemViewModel extends ViewModel {

    private ListItemRepository mListItemRepository;
    private String listName;
    private int itemId = -1;

    @Inject
    public EditListItemViewModel(ListItemRepository listItemRepository) {
        mListItemRepository = listItemRepository;
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
}
