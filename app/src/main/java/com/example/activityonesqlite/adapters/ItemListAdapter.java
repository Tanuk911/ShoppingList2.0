package com.example.activityonesqlite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.R;
import com.example.activityonesqlite.databases.DBHelper;
import com.example.activityonesqlite.models.entities.ListItem;
import com.example.activityonesqlite.utilites.DialogUtility;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListViewHolder> {

    Context context;
    List<ListItem> allItemLists;

    public ItemListAdapter(Context context, List<ListItem> allItemLists) {
        this.context = context;
        this.allItemLists = allItemLists;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        DBHelper dbHelper = new DBHelper(context);
        ListItem currentListItem = allItemLists.get(holder.getAdapterPosition());

        holder.txtItemName.setText(currentListItem.getItemName());
        holder.txtItemQty.setText(Float.toString(currentListItem.getItemQty()));
        holder.txtItemUnit.setText(currentListItem.getItemUnit());

        holder.imgBtnMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogUtility dialogUtility = new DialogUtility(context);

                dialogUtility.setAlertDialog(new DialogUtility.DialogCallback() {
                    @Override
                    public void onResult(boolean proceed) {
                        if (proceed) {
                            boolean isDeleted = dbHelper.deleteListItem(currentListItem.getScheduleId(), currentListItem.getItemName());
                            if (isDeleted) {
                                allItemLists.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), allItemLists.size());
                            } else {
                                Toast.makeText(context, "Item Deletion Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return allItemLists.size();
    }
}
