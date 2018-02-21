package com.example.aaronbrecher.shoppinglist.listactivity;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.model.ShoppingList;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aaronbrecher on 2/20/18.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private ListItemClickListener mListItemClickListener;
    private List<ShoppingList> mShoppingLists;
    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public ShoppingListAdapter(List<ShoppingList> shoppingLists) {
        //mListItemClickListener = clickListener;
        mShoppingLists = shoppingLists;
    }

    @Override
    public ShoppingListAdapter.ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutId = R.layout.shopping_lists_list_item;
        View view = inflater.inflate(layoutId, parent, false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.ShoppingListViewHolder holder, int position) {
        ShoppingList list = mShoppingLists.get(position);
        if(list != null){
            holder.listName.setText(list.getName());
            holder.listDescription.setText(list.getDescription());
            //Format the date and add the text to the view
            String dateString = DateFormat.getDateInstance().format(list.getDate());
            holder.listDate.setText(dateString);

        }
    }

    @Override
    public int getItemCount() {
        if (mShoppingLists == null) return 0;
        else{
            return mShoppingLists.size();
        }
    }

    /**
     * public method to switch out the data set to a new data set being that we are using
     * Livedata this may be used frequently and a better implementation may be to use DiffUtils
     * rather than changing the whole dataset TODO use diffutils in observer callback
     * @param newLists the new set of data to use
     */
    public void swapLists(List<ShoppingList> newLists){
        mShoppingLists = newLists;
        notifyDataSetChanged();
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        //use butterknife to bind the views of the layout and keep a cache in
        //the variables
        @BindView(R.id.shopping_lists_text_main) TextView listName;
        @BindView(R.id.shopping_lists_text_secondary) TextView listDescription;
        @BindView(R.id.shopping_lists_text_date) TextView listDate;

        public ShoppingListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
