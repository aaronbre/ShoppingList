package com.example.aaronbrecher.shoppinglist.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.aaronbrecher.shoppinglist.database.ListItemRepository;
import com.example.aaronbrecher.shoppinglist.database.ShoppingListRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by aaronbrecher on 2/19/18.
 */

@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final ListItemRepository listItemRepository;
    private final ShoppingListRepository shoppingListRepository;

    @Inject
    public CustomViewModelFactory(ListItemRepository listItemRepository, ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.listItemRepository = listItemRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ListViewModel.class)){
            return (T) new ListViewModel(shoppingListRepository);
        } else {
            throw new IllegalArgumentException("ViewModel not found");
        }
    }
}
