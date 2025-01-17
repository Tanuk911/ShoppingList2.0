package com.example.activityonesqlite.repositories.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.activityonesqlite.models.entities.ListItem;

import java.util.List;

@Dao
public interface ListItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertListItem(ListItem listItem);

    @Update
    void updateListItem(ListItem listItem);

    @Delete
    void deleteListItem(ListItem listItem);

    @Query("SELECT * FROM listitems WHERE scheduleId = :scheduleId")
    List<ListItem> getListItemsByScheduleId(int scheduleId);

    @Query("DELETE FROM listitems WHERE scheduleId = :scheduleId AND itemName = :itemName")
    void deleteListItem(int scheduleId, String itemName);
}
