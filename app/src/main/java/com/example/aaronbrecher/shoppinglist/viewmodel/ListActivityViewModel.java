package com.example.aaronbrecher.shoppinglist.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.aaronbrecher.shoppinglist.database.ShoppingListRepository;

/**
 * Created by aaronbrecher on 2/19/18.
 */

public class ListActivityViewModel extends AndroidViewModel{
    ShoppingListRepository mRepository;

    public ListActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ShoppingListRepository(application.getApplicationContext());
    }
}
