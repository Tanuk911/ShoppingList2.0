package com.example.activityonesqlite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.R;
import com.example.activityonesqlite.application.App;
import com.example.activityonesqlite.models.entities.ListItem;
import com.example.activityonesqlite.utils.DialogUtility;
import com.example.activityonesqlite.utils.ExecutorUtility;

import java.util.ArrayList;
import java.util.List;

public class ItemListEditAdapter extends RecyclerView.Adapter<itemListEditViewHolder> {

    Context context;
    List<ListItem> allListItems;

    public ItemListEditAdapter(Context context, List<ListItem> allListItems){
        this.context = context;
        this.allListItems = allListItems;
    }

    @NonNull
    @Override
    public itemListEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new itemListEditViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_edit_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull itemListEditViewHolder holder, int position) {
        ListItem currentListItem = allListItems.get(holder.getAdapterPosition());

        holder.edTxtItemName.setText(currentListItem.getItemName());
        holder.edTxtItemQuantity.setText(Float.toString(currentListItem.getItemQuantity()));
        holder.txtUnits.setText(currentListItem.getItemUnit());

        holder.imgBtnMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogUtility dialogUtility = new DialogUtility(context);

                dialogUtility.setAlertDialog(new DialogUtility.DialogCallback() {
                    @Override
                    public void onResult(boolean proceed) {
                        if (proceed) {

                            ExecutorUtility.runOnBackgroundThread(() -> {
                                App.getInstance().getDatabaseInstance().listItemDao().deleteListItem(currentListItem.getScheduleId(), currentListItem.getItemName());

                                ExecutorUtility.runOnMainThread(() -> {
                                    allListItems.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyItemRangeChanged(holder.getAdapterPosition(), allListItems.size());
                                });
                            });
                        }
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return allListItems.size();
    }
}
