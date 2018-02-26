package com.example.aaronbrecher.shoppinglist.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.example.aaronbrecher.shoppinglist.R;

/**
 * Created by aaronbrecher on 2/26/18.
 */

public class AddCategoryDialog extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     */

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    private View mView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.mView = inflater.inflate(R.layout.dialog_add_category, null);
        //set the title for the dialog
        builder.setTitle(R.string.category_dialog_title)
                //set the mView to our custom dialog mView
                .setView(mView)
                //set the negative button to exit the dialog
                .setNegativeButton(R.string.category_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddCategoryDialog.this.getDialog().cancel();
                    }
                })
                .setPositiveButton(R.string.category_dialog_add_category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogPositiveClick(AddCategoryDialog.this);
                    }
                });
        return builder.create();
    }

    public View getView() {
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
