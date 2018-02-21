package com.example.aaronbrecher.shoppinglist.listactivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.model.ShoppingList;
import com.example.aaronbrecher.shoppinglist.viewmodel.ListViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    //use dagger to get the ViewModelFactory will setup automatically
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    ListViewModel mViewModel;
    List<ShoppingList> mShoppingLists;
    ShoppingListAdapter mAdapter;
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

        //set up the view model for the activity
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(ListViewModel.class);

        /**set up the observable for the LiveData in the view model, the list
         * we are watching will be used to set up the recyclerView and will update
         * on changes
         */
        final Observer<List<ShoppingList>> shoppingListsObserver = new Observer<List<ShoppingList>>() {
            //will be executed on the initialization of the LiveData object, here we will also
            // initialize the adapter and set it on the recyclerView
            @Override
            public void onChanged(@Nullable List<ShoppingList> lists) {
                if(mShoppingLists == null){
                    mShoppingLists = lists;
                    mAdapter = new ShoppingListAdapter(lists);
                    mRecyclerView.setAdapter(mAdapter);
                }else {
                    mAdapter.swapLists(lists);
                }
            }
        };
        //observe the livedata for this activity
        mViewModel.getShoppingLists().observe(this, shoppingListsObserver);

        //setup the layoutManager for the recyclerView using a simple vertical layout
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false));
    }
}
