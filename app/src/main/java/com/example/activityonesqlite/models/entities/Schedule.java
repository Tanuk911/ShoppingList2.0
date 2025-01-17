package com.example.activityonesqlite.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Schedules",
indices = {@Index(value = {"scheduleDate", "scheduleLocation"}, unique = true)})
public class Schedule {

    @PrimaryKey(autoGenerate = true)
    private int scheduleId;

    private String scheduleDate;

    private String scheduleLocation;

    public Schedule(String scheduleDate, String scheduleLocation) {
        this.scheduleDate = scheduleDate;
        this.scheduleLocation = scheduleLocation;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleLocation() {
        return scheduleLocation;
    }

    public void setScheduleLocation(String scheduleLocation) {
        this.scheduleLocation = scheduleLocation;
    }
}
