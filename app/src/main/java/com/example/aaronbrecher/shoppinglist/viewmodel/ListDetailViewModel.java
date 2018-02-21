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

    @Inject
    public ListDetailViewModel(ShoppingListRepository shoppingListRepository, ListItemRepository listItemRepository){
        mListsRepository = shoppingListRepository;
        mItemRepository = listItemRepository;
    }

    public LiveData<List<ListItem>> getAllItemsForList(String listName){
        return mItemRepository.getListItemForShoppingList(listName);
    }
}
