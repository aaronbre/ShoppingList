package com.example.aaronbrecher.shoppinglist.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.adapters.ListDetailAdapter;
import com.example.aaronbrecher.shoppinglist.model.ListItem;
import com.example.aaronbrecher.shoppinglist.viewmodel.CustomViewModelFactory;
import com.example.aaronbrecher.shoppinglist.viewmodel.ListDetailViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDetailActivity extends AppCompatActivity {

    @Inject
    CustomViewModelFactory mCustomViewModelFactory;

    //refrence to the ViewModel which will do all non UI related things and hold onto LiveData
    private ListDetailViewModel mViewModel;
    //variable to keep track of the List of items(maybe should be in the viewModel...)
    private List<ListItem> mListItems;
    //recycler adapter
    private ListDetailAdapter mAdapter;

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

        //get the list name to query the database and add it to the viewModel
        mViewModel.setListName(getIntent().getStringExtra("listName"));
        //set the title to be the list name
        setTitle(mViewModel.getListName());
        //set up the recycler view NOTE: the onClick of the recyclerView is taken care of by the adapter!
        mListDetailItemList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        /**set up the observable for the LiveData in the view model, the list
         * we are watching will be used to set up the recyclerView and will update
         * on changes
         */
        final Observer<List<ListItem>> listItemsObserver = new Observer<List<ListItem>>() {
            //will be executed on the initialization of the LiveData object, here we will also
            // initialize the adapter and set it on the recyclerView
            @Override
            public void onChanged(@Nullable List<ListItem> items) {
                if(mListItems == null){
                    mListItems = items;
                    mAdapter = new ListDetailAdapter(items);
                    mListDetailItemList.setAdapter(mAdapter);
                }else {
                    mAdapter.swapList(items);
                }
            }
        };
        mViewModel.getAllItemsForCurrentList().observe(this, listItemsObserver);

        //Set listener for the FAB to launch the new EditListItem activity - which will double
        //as new item activity as well...
        mListDetailNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListDetailActivity.this, EditListItemActivity.class);
                intent.putExtra("ListName", mViewModel.getListName());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_details_menu, menu);
        return true;
    }

    //TODO show delete dialog
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.list_details_action_delete_list){
            mViewModel.deleteList();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
