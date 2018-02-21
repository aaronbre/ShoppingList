package com.example.aaronbrecher.shoppinglist.addList;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.model.ShoppingList;
import com.example.aaronbrecher.shoppinglist.viewmodel.AddShoppingListViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewListActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private AddShoppingListViewModel mViewModel;

    @BindView(R.id.add_new_list_name_input) EditText mNameInput;
    @BindView(R.id.add_new_list_description_input) EditText mDescriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        //set up butterknife
        ButterKnife.bind(this);

        //set up dependencyInjection
        ((ShoppingListApplication) getApplication())
                .getAppComponent()
                .inject(this);

        //create the view model
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(AddShoppingListViewModel.class);

        //set focusListener to set the placeholder for the editText (this is needed
        //to have the label for the editText when editting or edited)
        mNameInput.setOnFocusChangeListener(listener);
        mDescriptionInput.setOnFocusChangeListener(listener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_new_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.new_list_action_save){
            //TODO check that the name is unique
            ShoppingList list = createListFromUserInput();
            long listId = mViewModel.saveListToRoom(list);
            if(listId == -1){
                Toast.makeText(this, getString(R.string.insert_list_failed_toast), Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(this, getString(R.string.insert_list_success_toast), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Function to create a new shoppingList to be used to enter into db. Uses the user's
     * input to create the list and adds the date at time of creation
     * @return returns a new ShoppingList Object. If the List can't be created(i.e. no name) returns null
     */
    private ShoppingList createListFromUserInput(){
        ShoppingList list = new ShoppingList();
        String name = mNameInput.getText().toString().trim();
        String description = mDescriptionInput.getText().toString().trim();
        //if the name is empty it is invalid list so make toast and return null
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, getString(R.string.no_list_name_error_toast), Toast.LENGTH_SHORT).show();
            return null;
        }
        // if there is no description entered create a default description by prepending "A" to the name
        if (TextUtils.isEmpty(description)) description = "A " + name;

        list.setDate(System.currentTimeMillis());
        list.setName(name);
        list.setDescription(description);
        return list;
    }

    /**
     * An onFocusChangeListener to set the placeholder of the edit text on focus.
     * will be added to both editText Fields. Can use it for other editTexts as well
     */
    View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            EditText editText = (EditText) view;
            String placeholder = "placeholder";
            if (view == mDescriptionInput) placeholder = getString(R.string.new_list_description_placeholder);
            else if (view == mNameInput) placeholder = getString(R.string.new_list_name_placeholder);
            if (hasFocus) {
                editText.setHint(placeholder);
            } else {
                editText.setHint("");
            }
        }
    };
}
