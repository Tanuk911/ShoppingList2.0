package com.example.activityonesqlite.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.databases.DBHelper;
import com.example.activityonesqlite.R;
import com.example.activityonesqlite.activities.ExpandedViewActivity;
import com.example.activityonesqlite.models.entities.ScheduleModel;
import com.example.activityonesqlite.utilites.DialogUtility;

import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListViewHolder> {

    Context context;
    List<ScheduleModel> allSchedules;

    public ScheduleListAdapter(Context context, List<ScheduleModel> allSchedules) {
        this.context = context;
        this.allSchedules = allSchedules;
    }

    @NonNull
    @Override
    public ScheduleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleListViewHolder(LayoutInflater.from(context).inflate(R.layout.schedule_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleListViewHolder holder, int position) {
        String date = allSchedules.get(holder.getAdapterPosition()).getDate();
        String location = allSchedules.get(holder.getAdapterPosition()).getLocation();

        holder.txtDate.setText(date);
        holder.txtLocation.setText(location);

        holder.mainLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExpandedViewActivity.class);
                intent.putExtra("PositionDate", date);
                intent.putExtra("PositionLocation", location);
                context.startActivity(intent);
            }
        });

        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtility dialogUtility = new DialogUtility(context);
                dialogUtility.setAlertDialog(new DialogUtility.DialogCallback() {
                    @Override
                    public void onResult(boolean proceed) {
                        if (proceed) {
                            new DBHelper(context).deleteSchedule(date, location);

                            allSchedules.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            notifyItemRangeChanged(holder.getAdapterPosition(), allSchedules.size());
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return allSchedules.size();
    }
}
