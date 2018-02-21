package com.example.aaronbrecher.shoppinglist.dependencyinjection;

import com.example.aaronbrecher.shoppinglist.listactivity.ListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by aaronbrecher on 2/19/18.
 */

@Singleton
@Component(modules = {AppModule.class, RoomModule.class})
public interface AppComponent {
    void inject(ListActivity listActivity);
}
