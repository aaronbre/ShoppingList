package com.example.aaronbrecher.shoppinglist.dependencyinjection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.aaronbrecher.shoppinglist.database.ListItemDao;
import com.example.aaronbrecher.shoppinglist.database.ListItemRepository;
import com.example.aaronbrecher.shoppinglist.database.ShoppingListDao;
import com.example.aaronbrecher.shoppinglist.database.ShoppingListDatabase;
import com.example.aaronbrecher.shoppinglist.database.ShoppingListRepository;
import com.example.aaronbrecher.shoppinglist.viewmodel.CustomViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aaronbrecher on 2/19/18.
 * Module to supply for injection all Room related classes
 * used by Dagger 2
 */

@Module
public class RoomModule {

    Application application;

    public RoomModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    ShoppingListDatabase provideShoppingListDatabase(Application application){
        return Room.databaseBuilder(application,
                ShoppingListDatabase.class,
                "shopping-list-database")
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    ShoppingListDao provideShoppingListDao(ShoppingListDatabase database){
        return database.shoppingListDao();
    }

    @Provides
    @Singleton
    ListItemDao provideListItemDao(ShoppingListDatabase database){
        return database.listItemDao();
    }

    @Provides
    @Singleton
    ShoppingListRepository provideShoppingListRepository(ShoppingListDao shoppingListDao){
        return new ShoppingListRepository(shoppingListDao);
    }

    @Provides
    @Singleton
    ListItemRepository provideListItemRepository(ListItemDao listItemDao){
        return new ListItemRepository(listItemDao);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(ListItemRepository listItemRepository, ShoppingListRepository shoppingListRepository){
        return new CustomViewModelFactory(listItemRepository, shoppingListRepository);
    }
}
