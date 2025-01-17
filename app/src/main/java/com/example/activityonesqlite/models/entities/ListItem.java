package com.example.activityonesqlite.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "ListItems",
        foreignKeys = @ForeignKey(
                entity = Schedule.class,
                parentColumns = "scheduleId",
                childColumns = "scheduleId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = {"scheduleId", "itemName"}, unique = true)})

public class ListItem {

    @PrimaryKey(autoGenerate = true)
    private int itemId;

    private int scheduleId;

    private String itemName;

    private float itemQuantity;

    private String itemUnit;

    public ListItem(int scheduleId, String itemName, float itemQuantity, String itemUnit) {
        this.scheduleId = scheduleId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemUnit = itemUnit;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public float getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(float itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
