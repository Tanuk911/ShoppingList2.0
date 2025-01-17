package com.example.activityonesqlite.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.application.App;
import com.example.activityonesqlite.R;
import com.example.activityonesqlite.activities.ExpandedViewActivity;
import com.example.activityonesqlite.models.entities.Schedule;
import com.example.activityonesqlite.utils.DialogUtility;
import com.example.activityonesqlite.utils.ExecutorUtility;

import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListViewHolder> {

    Context context;
    List<Schedule> allSchedules;

    public ScheduleListAdapter(Context context, List<Schedule> allSchedules) {
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
        String date = allSchedules.get(holder.getAdapterPosition()).getScheduleDate();
        String location = allSchedules.get(holder.getAdapterPosition()).getScheduleLocation();

        holder.txtDate.setText(date);
        holder.txtLocation.setText(location);

        holder.mainLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorUtility.runOnBackgroundThread(() -> {
                    int scheduleId = App.getInstance().getDatabaseInstance().scheduleDao().getScheduleId(date, location);

                    ExecutorUtility.runOnMainThread(() -> {

                        Intent intent = new Intent(context, ExpandedViewActivity.class);
                        intent.putExtra("ScheduleId", scheduleId);
                        context.startActivity(intent);
                    });
                });
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
                            ExecutorUtility.runOnBackgroundThread(() -> {
                                App.getInstance().getDatabaseInstance().scheduleDao().deleteSchedule(allSchedules.get(holder.getAdapterPosition()));

                                ExecutorUtility.runOnMainThread(() -> {
                                    allSchedules.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyItemRangeChanged(holder.getAdapterPosition(), allSchedules.size());
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
        return allSchedules.size();
    }
}
