package com.example.aaronbrecher.shoppinglist.activities;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.model.ListItem;
import com.example.aaronbrecher.shoppinglist.viewmodel.CustomViewModelFactory;
import com.example.aaronbrecher.shoppinglist.viewmodel.EditListItemViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditListItemActivity extends AppCompatActivity {

    private static final String TAG = EditListItemActivity.class.getSimpleName();
    @BindView(R.id.edit_list_item_name) EditText mEditListItemName;
    @BindView(R.id.edit_list_item_category) EditText mEditListItemCategory;
    @BindView(R.id.edit_list_item_quantity) TextView mEditListItemQuantity;
    @BindView(R.id.edit_list_item_notes) EditText mEditListItemNotes;
    @BindView(R.id.edit_list_item_quantity_decrement) Button mDecrementButton;
    @BindView(R.id.edit_list_item_quantity_increment) Button mIncrementButton;

    @Inject
    CustomViewModelFactory mCustomViewModelFactory;

    @Inject
    SharedPreferences mSharedPreferences;

    private EditListItemViewModel mViewModel;
    private int mQuantityCount= 1;

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
        if (getIntent().hasExtra(getString(R.string.edit_list_item_list_item_key))) {
            //get the item from the intent
            ListItem item = getIntent().getParcelableExtra("item");
            mViewModel.setListName(item.getListName());
            setTitle(getString(R.string.edit_list_item_title));
            mViewModel.setItemId(item.getId());
            //occupy all the textFields with the text from the item
            mEditListItemName.setText(item.getItemName());
            mEditListItemCategory.setText(item.getCategory());
            mEditListItemQuantity.setText(String.valueOf(item.getQuantity()));
            mQuantityCount = item.getQuantity();
            mEditListItemNotes.setText(item.getNotes());
        } else {
            setTitle(R.string.new_list_item_title);
            mViewModel.setListName(getIntent().getStringExtra(getString(R.string.edit_list_item_list_name_key)));
        }

        mIncrementButton.setOnClickListener(quantityChangeListener);
        mDecrementButton.setOnClickListener(quantityChangeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_with_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.list_action_save){
            ListItem listItem = setupListItem();
            long id = mViewModel.addNewListItem(listItem);
            Toast.makeText(this, "the item was entered successfully id is " + id, Toast.LENGTH_LONG).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ListItem setupListItem(){
        ListItem listItem = new ListItem();
        listItem.setItemName(mEditListItemName.getText().toString());
        listItem.setCategory(mEditListItemCategory.getText().toString());
        listItem.setNotes(mEditListItemNotes.getText().toString());
        int quantity = Integer.parseInt(mEditListItemQuantity.getText().toString());
        listItem.setQuantity(quantity);
        return listItem;
    }

    /**
     * Listener for the quantity change buttons
     * TODO Implement
     */
    View.OnClickListener quantityChangeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String operator = ((Button) view).getText().toString().trim();
            if (operator.equals("+")){
                mQuantityCount++;
                updateQuantity();
            }
            else if(operator.equals("-")){
                mQuantityCount--;
                updateQuantity();
            }
        }
    };

    private void updateQuantity(){
        mEditListItemQuantity.setText(String.format("%d", mQuantityCount));
    }
}