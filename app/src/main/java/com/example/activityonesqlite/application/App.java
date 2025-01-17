package com.example.activityonesqlite.application;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.activityonesqlite.databases.DBMigration;
import com.example.activityonesqlite.databases.ShoppingListDB;

public class App extends Application {

    private static final String DATABASE_NAME = "shoppinglist_db";
    private static App instance;
    private ShoppingListDB shoppingListDB;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        getDatabaseInstance();
    }

    public static App getInstance() {
        return instance;
    }

    public ShoppingListDB getDatabaseInstance(){
        if (shoppingListDB == null) {
            shoppingListDB = Room.databaseBuilder(this,
                            ShoppingListDB.class, DATABASE_NAME)
                            .build();

//                After making a schema change add  .addMigrations(DBMigration.MIGRATION_1_2) to add custom migration
        }

        return shoppingListDB;
    }
}
