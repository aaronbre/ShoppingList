package com.example.aaronbrecher.shoppinglist.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.aaronbrecher.shoppinglist.database.ListItemRepository;
import com.example.aaronbrecher.shoppinglist.database.ShoppingListRepository;
import com.example.aaronbrecher.shoppinglist.model.ListItem;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by aaronbrecher on 2/21/18.
 */

public class ListDetailViewModel extends ViewModel {

    ShoppingListRepository mListsRepository;
    ListItemRepository mItemRepository;
    private String mListName;

    @Inject
    public ListDetailViewModel(ShoppingListRepository shoppingListRepository, ListItemRepository listItemRepository){
        mListsRepository = shoppingListRepository;
        mItemRepository = listItemRepository;
    }

    public LiveData<List<ListItem>> getAllItemsForCurrentList(){
        return mItemRepository.getListItemsForShoppingList(mListName);
    }

    public void deleteList(){
        mListsRepository.deleteList(mListsRepository.getShoppingList(mListName));
    }

    public String getListName() {
        return mListName;
    }

    public void setListName(String listName) {
        mListName = listName;
    }
}
