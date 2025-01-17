package com.example.activityonesqlite.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.activityonesqlite.models.entities.ListItem;
import com.example.activityonesqlite.models.entities.Schedule;
import com.example.activityonesqlite.repositories.dao.ListItemDao;
import com.example.activityonesqlite.repositories.dao.ScheduleDao;


@Database(entities = {
        Schedule.class,
        ListItem.class}, version = 1, exportSchema = false)

public abstract class ShoppingListDB extends RoomDatabase {

    public abstract ScheduleDao scheduleDao();

    public abstract ListItemDao listItemDao();
}
