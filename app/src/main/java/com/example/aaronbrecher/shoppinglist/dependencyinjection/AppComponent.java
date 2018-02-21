package com.example.aaronbrecher.shoppinglist.dependencyinjection;

import com.example.aaronbrecher.shoppinglist.addList.NewListActivity;
import com.example.aaronbrecher.shoppinglist.listactivity.ListActivity;
import com.example.aaronbrecher.shoppinglist.listdetail.ListDetailActivity;

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
}
