package com.example.aaronbrecher.shoppinglist.listactivity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.viewmodel.ListViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    //use dagger to get the ViewModelFactory will setup automatically
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    ListViewModel mViewModel;
    @BindView(R.id.shopping_lists_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //set up butterknife to bind the views so we don't have to do findViewById
        ButterKnife.bind(this);
        //set up dependencyInjection
        ((ShoppingListApplication) getApplication())
                .getAppComponent()
                .inject(this);

        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(ListViewModel.class);

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false));
    }
}
