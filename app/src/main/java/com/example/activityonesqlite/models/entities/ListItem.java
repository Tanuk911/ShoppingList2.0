package com.example.activityonesqlite.models.entities;

public class ListItem {

    int scheduleId;
    String itemName, itemUnit;
    float itemQty;

    public ListItem(int scheduleId, String itemName, float itemQty, String itemUnit) {
        this.scheduleId = scheduleId;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemUnit = itemUnit;
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

    public float getItemQty() {
        return itemQty;
    }

    public void setItemQty(float itemQty) {
        this.itemQty = itemQty;
    }
}
