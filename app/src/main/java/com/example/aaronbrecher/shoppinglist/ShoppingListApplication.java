package com.example.aaronbrecher.shoppinglist;

import android.app.Application;

import com.example.aaronbrecher.shoppinglist.dependencyinjection.AppComponent;
import com.example.aaronbrecher.shoppinglist.dependencyinjection.AppModule;
import com.example.aaronbrecher.shoppinglist.dependencyinjection.DaggerAppComponent;
import com.example.aaronbrecher.shoppinglist.dependencyinjection.RoomModule;

/**
 * Created by aaronbrecher on 2/19/18.
 */

public class ShoppingListApplication extends Application {
    private static ShoppingListApplication app;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appComponent = DaggerAppComponent.builder().roomModule(new RoomModule(this)).appModule(new AppModule(this)).build();
    }
    public static ShoppingListApplication getApp(){return app;}

    public AppComponent getAppComponent() {return appComponent;}
}
