package com.example.aaronbrecher.shoppinglist.dependencyinjection;

import android.app.Application;
import android.content.Context;

import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aaronbrecher on 2/19/18.
 */

@Module
public class AppModule {

    private ShoppingListApplication application;

    public AppModule(ShoppingListApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    ShoppingListApplication providesShoppingListApp(){
        return application;
    }

    @Provides
    @Singleton
    Application providesApp(){return application;}
}
