package com.example.aaronbrecher.shoppinglist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aaronbrecher.shoppinglist.R;
import com.example.aaronbrecher.shoppinglist.activities.EditListItemActivity;
import com.example.aaronbrecher.shoppinglist.model.ListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aaronbrecher on 2/22/18.
 */

public class ListDetailAdapter extends RecyclerView.Adapter <ListDetailAdapter.ListDetailViewHolder>{
    private List<ListItem> mListItems;
    //store the context to be able to launch an intent from inside the adapter
    //not sure if this is best practice...
    private Context mContext;


    public ListDetailAdapter(List<ListItem> listItems) {
        mListItems = listItems;
    }

    @Override
    public ListDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_detail_list_item, parent, false);
        return new ListDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListDetailViewHolder holder, int position) {
        ListItem item = mListItems.get(position);
        if (item != null) {
            holder.itemName.setText(item.getItemName());
            holder.itemCategory.setText(item.getCategory());
            holder.itemNotes.setText(item.getNotes());
            holder.itemQuantity.setText(item.getQuantity());
        }
    }

    @Override
    public int getItemCount() {
        if (mListItems == null) {
            return 0;
        }
        return mListItems.size();
    }

    /**
     * The ViewHolder for the adapter. This viewHolder will implement the on click listener
     * for the enclosed view. It uses the context member variable to launch a new activity to see the
     * details of the listItem and be able to edit it... again not sure if this is best practice...
     */
    public class ListDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.list_detail_item_name) TextView itemName;
        @BindView(R.id.list_detail_item_category) TextView itemCategory;
        @BindView(R.id.list_detail_item_notes) TextView itemNotes;
        @BindView(R.id.list_detail_item_quantity) TextView itemQuantity;

        public ListDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPostition = getAdapterPosition();
            ListItem item = mListItems.get(adapterPostition);
            Intent intent = new Intent(mContext, EditListItemActivity.class);
            intent.putExtra("item", item);
            mContext.startActivity(intent);
        }
    }
    public void swapList(List<ListItem> newList){
        mListItems = newList;
    }
}
