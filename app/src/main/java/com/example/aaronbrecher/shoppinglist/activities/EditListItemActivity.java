
package com.example.aaronbrecher.shoppinglist.activities;

import android.arch.lifecycle.ViewModel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.ShoppingListApplication;
import com.example.aaronbrecher.shoppinglist.viewmodel.CustomViewModelFactory;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class EditListItemActivity extends AppCompatActivity {

    @Inject
    CustomViewModelFactory mCustomViewModelFactory;
    private ViewModel mViewModel;
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
    }
}
