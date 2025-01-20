package com.example.activityonesqlite.listeners;

import com.example.activityonesqlite.models.entities.ListItem;

import java.util.List;

public interface OnFragmentEditListener {
    void onEditDate(int scheduleId, String date);

    void onEditLocation(int scheduleId, String location);

    void onEditListItem(List<ListItem> changedItems);
}
