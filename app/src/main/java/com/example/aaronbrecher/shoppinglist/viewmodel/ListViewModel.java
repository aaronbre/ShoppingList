package com.example.aaronbrecher.shoppinglist.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.aaronbrecher.shoppinglist.database.ShoppingListRepository;
import com.example.aaronbrecher.shoppinglist.model.ShoppingList;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by aaronbrecher on 2/19/18.
 */

public class ListViewModel extends ViewModel {
    private ShoppingListRepository repository;

    @Inject
    public ListViewModel(ShoppingListRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<ShoppingList>> getShoppingLists(){
        return repository.getAllShoppingLists();
    }

    public void deleteAllShoppingLists(){
        repository.deleteAll();
    }

    public void deleteList(ShoppingList list){
        repository.deleteList(list);
    }
}

