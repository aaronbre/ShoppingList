package com.example.aaronbrecher.shoppinglist.listdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.viewmodel.CustomViewModelFactory;
import com.example.aaronbrecher.shoppinglist.viewmodel.ListDetailViewModel;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class ListDetailActivity extends AppCompatActivity {

    @Inject
    CustomViewModelFactory mCustomViewModelFactory;
    ListDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        //set up butterknife
        ButterKnife.bind(this);

        //set up dependencyInjection
        ((ShoppingListApplication) getApplication())
                .getAppComponent()
                .inject(this);

        mViewModel = ViewModelProviders.of(this, mCustomViewModelFactory)
                .get(ListDetailViewModel.class);
    }
}
