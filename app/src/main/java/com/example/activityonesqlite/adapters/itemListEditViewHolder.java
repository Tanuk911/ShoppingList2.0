package com.example.activityonesqlite.adapters;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.R;

public class itemListEditViewHolder extends RecyclerView.ViewHolder {

    EditText edTxtItemName, edTxtItemQuantity;
    TextView txtUnits;
    ImageButton imgBtnMinus;

    public itemListEditViewHolder(@NonNull View itemView) {
        super(itemView);
        edTxtItemName = itemView.findViewById(R.id.list_item_edit_view_edTxtItemName);
        edTxtItemQuantity = itemView.findViewById(R.id.list_item_edit_view_edTxtItemQty);
        txtUnits = itemView.findViewById(R.id.list_item_edit_view_txtItemUnit);
        imgBtnMinus = itemView.findViewById(R.id.list_item_edit_view_imgBtnMinus);
    }
}
