package com.example.aaronbrecher.shoppinglist.activitylistdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.viewmodel.CustomViewModelFactory;
import com.example.aaronbrecher.shoppinglist.viewmodel.ListDetailViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDetailActivity extends AppCompatActivity {

    @Inject
    CustomViewModelFactory mCustomViewModelFactory;
    ListDetailViewModel mViewModel;
    @BindView(R.id.list_detail_item_list) RecyclerView mListDetailItemList;
    @BindView(R.id.list_detail_new_item) FloatingActionButton mListDetailNewItem;

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
        //get the ViewModel
        mViewModel = ViewModelProviders.of(this, mCustomViewModelFactory)
                .get(ListDetailViewModel.class);
    }
}
