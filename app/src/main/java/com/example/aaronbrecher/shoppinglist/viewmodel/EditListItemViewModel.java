package com.example.aaronbrecher.shoppinglist.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.aaronbrecher.shoppinglist.database.ListItemRepository;
import com.example.aaronbrecher.shoppinglist.model.ListItem;

import javax.inject.Inject;

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

    public void addNewListItem(ListItem item){
        item.setListName(listName);
        if(itemId != -1) item.setId(itemId);
        mListItemRepository.insertListItems(item);
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
