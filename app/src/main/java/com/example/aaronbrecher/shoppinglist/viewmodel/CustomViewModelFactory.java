package com.example.aaronbrecher.shoppinglist.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.aaronbrecher.shoppinglist.activities.EditListItemActivity;
import com.example.aaronbrecher.shoppinglist.database.ListItemRepository;
import com.example.aaronbrecher.shoppinglist.database.ShoppingListRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by aaronbrecher on 2/19/18.
 * This custom factory enables us to add the repository classes to the ViewModel
 * it will be injected using dagger. Can add either the ShoppingListRepo or the
 * listItemRepo or both
 */

@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final ListItemRepository listItemRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final SharedPreferences sharedPreferences;
    @Inject
    public CustomViewModelFactory(ListItemRepository listItemRepository,
                                  ShoppingListRepository shoppingListRepository, SharedPreferences sharedPreferences) {
        this.shoppingListRepository = shoppingListRepository;
        this.listItemRepository = listItemRepository;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ListViewModel.class)){
            return (T) new ListViewModel(shoppingListRepository);
        }else if(modelClass.isAssignableFrom(AddShoppingListViewModel.class)){
            return (T) new AddShoppingListViewModel(shoppingListRepository);
        } else if(modelClass.isAssignableFrom(ListDetailViewModel.class)){
            return (T) new ListDetailViewModel(shoppingListRepository, listItemRepository);
        } else if (modelClass.isAssignableFrom(EditListItemViewModel.class)){
            return (T) new EditListItemViewModel(listItemRepository, sharedPreferences);
        }
        else {
            throw new IllegalArgumentException("ViewModel not found");
        }
    }
}
