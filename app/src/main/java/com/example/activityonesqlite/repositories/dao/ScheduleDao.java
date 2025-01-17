package com.example.activityonesqlite.repositories.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.activityonesqlite.models.entities.Schedule;

import java.util.List;

@Dao
public interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertSchedule(Schedule schedule);

    @Update
    void updateSchedule(Schedule schedule);

    @Delete
    void deleteSchedule(Schedule schedule);

    @Query("SELECT * FROM schedules WHERE scheduleId = :scheduleId")
    Schedule getScheduleById(int scheduleId);

    @Query("SELECT * FROM schedules")
    List<Schedule> getAllSchedules();

    @Query("SELECT scheduleId FROM schedules WHERE scheduleDate = :date AND scheduleLocation = :location")
    Integer getScheduleId(String date, String location);
}
