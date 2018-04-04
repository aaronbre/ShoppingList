package com.example.aaronbrecher.shoppinglist.activities;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.fragments.AddCategoryDialog;
import com.example.aaronbrecher.shoppinglist.model.ListItem;
import com.example.aaronbrecher.shoppinglist.viewmodel.CustomViewModelFactory;
import com.example.aaronbrecher.shoppinglist.viewmodel.EditListItemViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditListItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AddCategoryDialog.NoticeDialogListener{

    private static final String TAG = EditListItemActivity.class.getSimpleName();
    @BindView(R.id.edit_list_item_name) EditText mEditListItemName;
    @BindView(R.id.edit_list_item_quantity) TextView mEditListItemQuantity;
    @BindView(R.id.edit_list_item_notes) EditText mEditListItemNotes;
    @BindView(R.id.edit_list_item_quantity_decrement) Button mDecrementButton;
    @BindView(R.id.edit_list_item_quantity_increment) Button mIncrementButton;
    @BindView(R.id.edit_list_item_category_spinner) Spinner mCategorySpinner;
    @BindView(R.id.edit_list_item_category_button_new) Button mNewCategoryButton;

    @Inject
    CustomViewModelFactory mCustomViewModelFactory;

    @Inject
    SharedPreferences mSharedPreferences;

    //adapter for the spinner
    private ArrayAdapter<String> mCategoriesSpinnerAdapter;
    //variable to keep track of the category from the spinner
    private String mCategory;

    private EditListItemViewModel mViewModel;

    //variable to keep track of the quantity so as to make less operations
    //of parsing the TextView to an integer
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

        //method call to set up the spinner UI
        setUpSpinner();

        //set up the activity depending on if it is editing an existing item or adding new
        setUpForEditOrNew();

        //set listeners for buttons to add or subtract from quantity
        mIncrementButton.setOnClickListener(quantityChangeListener);
        mDecrementButton.setOnClickListener(quantityChangeListener);

        //set listener for the new category button
        mNewCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new AddCategoryDialog();
                dialog.show(getSupportFragmentManager(), "AddCategoryDialog");
            }
        });

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
        listItem.setCategory(mCategory);
        listItem.setNotes(mEditListItemNotes.getText().toString());
        int quantity = Integer.parseInt(mEditListItemQuantity.getText().toString());
        listItem.setQuantity(quantity);
        return listItem;
    }

    /**
     * Listener for the quantity change buttons
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

    /**
     * Functions to update the category when the spinner selects a different category
     * Implementation of onItemSelectedListener
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        mCategory = (String) adapterView.getItemAtPosition(position);


    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    /**
     * Updates the spinner as well as the sharedPreference set with a new category option,
     * @param category the string value of the category to be added
     */
    private void updateCategories(String category){
        //If the set already contains the category we can do nothing
        if(mViewModel.getCategorySet().contains(category)) return;
        //add the new category to the set (to be added to sharedprefs
        mViewModel.getCategorySet().add(category);
        //add the category to the spinner and notify added
        mCategoriesSpinnerAdapter.add(category);
        mCategoriesSpinnerAdapter.notifyDataSetChanged();
        //occupy the spinner with the added category and update the variable
        int position = mCategoriesSpinnerAdapter.getPosition(category);
        mCategorySpinner.setSelection(position);
        mCategory = category;
    }

    private void setUpSpinner(){
        mCategoriesSpinnerAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item,
                new ArrayList<String>(mViewModel.getCategorySet())
                );
        mCategorySpinner.setAdapter(mCategoriesSpinnerAdapter);
        mCategorySpinner.setSelection(0);
        mCategory = mCategoriesSpinnerAdapter.getItem(0);
        mCategorySpinner.setOnItemSelectedListener(this);
    }

    /**
     * if the the intent has the extra we set the title to edit list item, and
     * occupy the fields with the information from the list item.
     * if not then set the title to add new list item
     */
    private void setUpForEditOrNew(){
        if (getIntent().hasExtra(getString(R.string.edit_list_item_list_item_key))) {
            //get the item from the intent
            ListItem item = getIntent().getParcelableExtra("item");
            mViewModel.setListName(item.getListName());
            setTitle(getString(R.string.edit_list_item_title));
            mViewModel.setItemId(item.getId());
            //occupy all the textFields with the text from the item
            mEditListItemName.setText(item.getItemName());
            //TODO change this to check if the set has the category if not update this is needed when the app gets
            //a list item from a different user i.e. when we integrate firebase...
            int categoryPosition = mCategoriesSpinnerAdapter.getPosition(item.getCategory());
            mCategorySpinner.setSelection(categoryPosition);
            mEditListItemQuantity.setText(String.valueOf(item.getQuantity()));
            mQuantityCount = item.getQuantity();
            mEditListItemNotes.setText(item.getNotes());
        } else {
            setTitle(R.string.new_list_item_title);
            mViewModel.setListName(getIntent().getStringExtra(getString(R.string.edit_list_item_list_name_key)));
        }
    }

    /**
     * Function to implement the dialog listener to add the new category to the category list
     * @param dialog the dialog fragment for adding a category
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        dialog = (AddCategoryDialog) dialog;
        TextInputEditText editText = dialog.getView().findViewById(R.id.add_category_edit_text);
        String newCategory = editText.getText().toString();
        updateCategories(newCategory);
    }
}