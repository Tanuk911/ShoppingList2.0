package com.example.activityonesqlite.models;

public class ScheduleModel {

    private int id;
    private String date;
    private String location;

    public ScheduleModel(int id, String date, String location) {
        this.id = id;
        this.date = date;
        this.location = location;
    }

    @Override
    public String toString() {
        return "ScheduleModel{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
