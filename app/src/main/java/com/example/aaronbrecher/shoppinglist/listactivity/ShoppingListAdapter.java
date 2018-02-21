package com.example.aaronbrecher.shoppinglist.listactivity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aaronbrecher on 2/20/18.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {
    @Override
    public ShoppingListAdapter.ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.ShoppingListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder{

        public ShoppingListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
