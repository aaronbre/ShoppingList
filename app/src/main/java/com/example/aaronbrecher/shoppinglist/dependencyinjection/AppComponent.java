package com.example.aaronbrecher.shoppinglist.dependencyinjection;

import com.example.aaronbrecher.shoppinglist.activities.EditListItemActivity;
import com.example.aaronbrecher.shoppinglist.activities.NewListActivity;
import com.example.aaronbrecher.shoppinglist.activities.ListActivity;
import com.example.aaronbrecher.shoppinglist.activities.ListDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by aaronbrecher on 2/19/18.
 */

@Singleton
@Component(modules = {AppModule.class, RoomModule.class})
public interface AppComponent {
    void inject(ListActivity listActivity);
    void inject(NewListActivity newListActivity);
    void inject(ListDetailActivity listDetailActivity);
    void inject(EditListItemActivity editListItemActivity);
}
