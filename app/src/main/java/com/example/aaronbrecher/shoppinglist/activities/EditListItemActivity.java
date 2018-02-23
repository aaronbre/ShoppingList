package com.example.aaronbrecher.shoppinglist.activities;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.model.ListItem;
import com.example.aaronbrecher.shoppinglist.viewmodel.CustomViewModelFactory;
import com.example.aaronbrecher.shoppinglist.viewmodel.EditListItemViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditListItemActivity extends AppCompatActivity {

    @BindView(R.id.edit_list_item_name) EditText mEditListItemName;
    @BindView(R.id.edit_list_item_category) EditText mEditListItemCategory;
    @BindView(R.id.edit_list_item_quantity) EditText mEditListItemQuantity;
    @BindView(R.id.edit_list_item_notes) EditText mEditListItemNotes;
    private boolean mIsNewListItem = true;
    @Inject
    CustomViewModelFactory mCustomViewModelFactory;
    private ViewModel mViewModel;
    private String mListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_item);

        //set up butterknife
        ButterKnife.bind(this);

        //set up dependencyInjection
        ((ShoppingListApplication) getApplication())
                .getAppComponent()
                .inject(this);

        //set up the view model
        mViewModel = ViewModelProviders.of(this, mCustomViewModelFactory)
                .get(EditListItemViewModel.class);

        /**
         * if the the intent has the extra we set the title to edit list item, and
         * occupy the fields with the information from the list item.
         * if not then set the title to add new list item
         */
        if (getIntent().hasExtra("item")) {
            //get the item from the intent
            ListItem item = getIntent().getParcelableExtra("item");
            mListName = item.getListName();
            setTitle(getString(R.string.edit_list_item_title));
            mIsNewListItem = false;
            //occupy all the textFields with the text from the item
            mEditListItemName.setText(item.getItemName());
            mEditListItemCategory.setText(item.getCategory());
            mEditListItemQuantity.setText(item.getQuantity());
            mEditListItemNotes.setText(item.getNotes());
        } else {
            setTitle(R.string.new_list_item_title);
            mListName = getIntent().getStringExtra("listName");
        }
    }
}
