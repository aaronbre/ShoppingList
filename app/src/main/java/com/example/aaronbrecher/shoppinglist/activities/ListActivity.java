package com.example.aaronbrecher.shoppinglist.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.adapters.ShoppingListAdapter;
import com.example.aaronbrecher.shoppinglist.model.ShoppingList;
import com.example.aaronbrecher.shoppinglist.viewmodel.ListViewModel;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Activity controls the list of all Shopping Lists. From here the user can delete a list (or all lists)
 * and tap on a list to view all the items in that list. To implement the tap the class implements the ListItemClickListener
 * Interface supplied by the recyclerView adapter. This allows the adapter to call the onListItemClicked function to
 * start a new intent
 */
public class ListActivity extends AppCompatActivity implements ShoppingListAdapter.ListItemClickListener {

    //use dagger to get the ViewModelFactory will setup automatically
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    SharedPreferences mSharedPreferences;

    ListViewModel mViewModel;
    List<ShoppingList> mShoppingLists;
    ShoppingListAdapter mAdapter;
    @BindView(R.id.shopping_lists_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.add_new_shopping_list_fab) FloatingActionButton mAddNewList;

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

        //do initial app setup on first run...
        if(mSharedPreferences.getBoolean("firstrun", true)){
            firstTimeSetup();
            mSharedPreferences.edit().putBoolean("firstrun", false).apply();
        }
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
                    mAdapter = new ShoppingListAdapter(lists, ListActivity.this);
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

        //TODO need to add swipe functionality to delete a list

        //setup the fab button to add a new shopping list
        mAddNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, NewListActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Set up the options menu will have one action that deletes all the lists
     * currently in the database (doing so will also delete all the items by cascading
     * see the listItem Entity(model)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shopping_lists_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.lists_activity_action_delete_all){
            mViewModel.deleteAllShoppingLists();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO need to write function to bring up dialog on delete to make sure you want to delete

    //function to react to a click on the recycler view haveing this here
    //allows us to start an intent without passing in the context to the adapter
    @Override
    public void onListItemClick(String name) {
        Intent intent = new Intent(this, ListDetailActivity.class);
        intent.putExtra("listName", name);
        startActivity(intent);
    }

    /**
     * A function to be called on the first load of the app. It initializes the shared preferences
     * with a set of categories to be used in the editItem spinner for categories
     */
    private void firstTimeSetup(){
        if (mSharedPreferences.getBoolean("firstLogin", true)){
            Set<String> categories = new HashSet<String>();
            String [] c = getResources().getStringArray(R.array.item_categories);
            Collections.addAll(categories, c);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putStringSet(getString(R.string.shared_preferences_category_key), categories);
            editor.apply();
        }
    }
}
