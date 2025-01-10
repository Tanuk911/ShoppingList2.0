package com.example.activityonesqlite.adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.R;

public class ItemListViewHolder extends RecyclerView.ViewHolder {

    TextView txtItemName, txtItemQty, txtItemUnit;
    ImageButton imgBtnMinus;
    public ItemListViewHolder(@NonNull View itemView) {
        super(itemView);

        txtItemName = itemView.findViewById(R.id.list_item_view_txtItemName);
        txtItemQty = itemView.findViewById(R.id.list_item_view_txtItemQty);
        txtItemUnit = itemView.findViewById(R.id.list_item_view_txtItemUnit);
        imgBtnMinus = itemView.findViewById(R.id.list_item_view_imgBtnMinus);
    }
}
