package com.example.aaronbrecher.shoppinglist.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.viewmodel.ListViewModel;

import javax.inject.Inject;

public class ListActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    ListViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ((ShoppingListApplication) getApplication())
                .getAppComponent()
                .inject(this);

        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(ListViewModel.class);
    }
}
