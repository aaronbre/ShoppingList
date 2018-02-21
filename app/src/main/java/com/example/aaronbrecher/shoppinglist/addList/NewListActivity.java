package com.example.aaronbrecher.shoppinglist.addList;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
            //TODO save the list to the Room database
            ShoppingList list = createListFromUserInput();
        }
        return super.onOptionsItemSelected(item);
    }

    private ShoppingList createListFromUserInput(){
        ShoppingList list = new ShoppingList();
        list.setDate(System.currentTimeMillis()/1000);

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
