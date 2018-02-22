package com.example.aaronbrecher.shoppinglist.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.aaronbrecher.shoppinglist.database.ListItemRepository;

/**
 * Created by aaronbrecher on 2/22/18.
 */

public class EditListItemViewModel extends ViewModel {

    private ListItemRepository mListItemRepository;

    public EditListItemViewModel(ListItemRepository listItemRepository) {
        mListItemRepository = listItemRepository;
    }
}
