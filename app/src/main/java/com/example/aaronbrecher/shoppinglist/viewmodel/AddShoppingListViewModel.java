package com.example.aaronbrecher.shoppinglist.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.aaronbrecher.shoppinglist.database.ShoppingListRepository;
import com.example.aaronbrecher.shoppinglist.model.ShoppingList;

import javax.inject.Inject;

/**
 * Created by aaronbrecher on 2/20/18.
 */

public class AddShoppingListViewModel extends ViewModel {

    private ShoppingListRepository repository;

    @Inject
    public AddShoppingListViewModel(ShoppingListRepository repository) {
        this.repository = repository;
    }

    public void saveListToRoom(ShoppingList list){
        repository.insertAllLists(list);
    }
}
