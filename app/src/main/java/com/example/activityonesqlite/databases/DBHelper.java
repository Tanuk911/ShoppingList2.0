package com.example.activityonesqlite.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.activityonesqlite.models.ItemModel;
import com.example.activityonesqlite.models.ScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String SCHEDULE_TABLE = "SCHEDULE_TABLE";
    public static final String DATE_COLUMN = "DATE";
    public static final String LOCATION_COLUMN = "LOCATION";
    public static final String LIST_TABLE = "ITEM_LIST_TABLE";
    public static final String LIST_SCHEDULE_ID = "LIST_SCHEDULE_ID";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_QTY = "ITEM_QTY";
    public static final String ITEM_UNIT = "ITEM_UNIT";

    Context context;

    public DBHelper(@Nullable Context context) {
        super(context, "shoppingList.db", null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createScheduleTableStatement = "CREATE TABLE " + SCHEDULE_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE_COLUMN + " TEXT, " + LOCATION_COLUMN + " TEXT, UNIQUE (" + DATE_COLUMN + ", " + LOCATION_COLUMN + "))";
        String createItemListTableStatement = "CREATE TABLE " + LIST_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + LIST_SCHEDULE_ID + " INTEGER, " + ITEM_NAME + " TEXT, " + ITEM_QTY + " REAL, " + ITEM_UNIT + " TEXT, FOREIGN KEY(" + LIST_SCHEDULE_ID + ") REFERENCES " + SCHEDULE_TABLE + "(ID))";

        db.execSQL(createScheduleTableStatement);
        db.execSQL(createItemListTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);
        onCreate(db);
    }

    public boolean addSchedule(ScheduleModel scheduleModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String date = scheduleModel.getDate();
        String location = scheduleModel.getLocation();

        if (checkForDuplicateSchedules(db, date, location)) {
            Toast.makeText(context, "This Schedule Already Exists", Toast.LENGTH_LONG).show();
            return false;
        }

        cv.put(DATE_COLUMN, date);
        cv.put(LOCATION_COLUMN, location);

        long insert = db.insert(SCHEDULE_TABLE, null, cv);
        if (insert == -1) {
            Toast.makeText(context, "Schedule Adding Unsuccessful", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(context, "Schedule Added Successfully", Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean addListItem(ItemModel itemModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int scheduleId = itemModel.getScheduleId();
        String itemName = itemModel.getItemName();
        String itemUnit = itemModel.getItemUnit();
        float itemQty = itemModel.getItemQty();

        if (checkForDuplicateListItems(db, scheduleId, itemName)) {
            Toast.makeText(context, "This Item already exists in this List", Toast.LENGTH_LONG).show();
            return false;
        }

        cv.put(LIST_SCHEDULE_ID, scheduleId);
        cv.put(ITEM_NAME, itemName);
        cv.put(ITEM_UNIT, itemUnit);
        cv.put(ITEM_QTY, itemQty);

        long insert = db.insert(LIST_TABLE, null, cv);
        if (insert == -1) {
            Toast.makeText(context, "List Adding Unsuccessful", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(context, "List Added Successfully", Toast.LENGTH_LONG).show();
        return true;
    }

    public List<ItemModel> getAllLists(int scheduleID) {

        List<ItemModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + LIST_TABLE + " WHERE " + LIST_SCHEDULE_ID + " = '" + scheduleID + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int itemScheduleID = cursor.getInt(1);
                String itemName = cursor.getString(2);
                float itemQty = cursor.getFloat(3);
                String itemUnit = cursor.getString(4);

                ItemModel itemModel = new ItemModel(itemScheduleID, itemName, itemQty, itemUnit);
                returnList.add(itemModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<ScheduleModel> getAllSchedules() {
        List<ScheduleModel> returnList = new ArrayList<>();

        //getting data from DB
        String queryString = "SELECT * FROM " + SCHEDULE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new schedule objects.
            do {
                int scheduleID = cursor.getInt(0);
                String scheduleDate = cursor.getString(1);
                String scheduleLocation = cursor.getString(2);

                ScheduleModel newSchedule = new ScheduleModel(scheduleID, scheduleDate, scheduleLocation);
                returnList.add(newSchedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public int getScheduleId(String scheduleDate, String scheduleLocation) {
        String queryString = "SELECT ID FROM " + SCHEDULE_TABLE + " WHERE " + DATE_COLUMN + " = ? AND " + LOCATION_COLUMN + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[]{scheduleDate, scheduleLocation});

        int scheduleID = -1; // Default value if no result is found
        if (cursor.moveToFirst()) {
            scheduleID = cursor.getInt(0); // Get the ID from the first column
        }
        cursor.close();
        return scheduleID;
    }

    public boolean deleteSchedule(String scheduleDate, String scheduleLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isDeleted = false;

        try {
            db.beginTransaction();
            Cursor cursor = db.rawQuery("SELECT ID FROM " + SCHEDULE_TABLE + " WHERE " + DATE_COLUMN + " = ? AND " + LOCATION_COLUMN + " = ?", new String[]{scheduleDate, scheduleLocation});

            if (cursor.moveToFirst()) {
                int scheduleId = cursor.getInt(0);
                db.delete(LIST_TABLE, LIST_SCHEDULE_ID + " = ?", new String[]{String.valueOf(scheduleId)});

                int rowsAffected = db.delete(SCHEDULE_TABLE, DATE_COLUMN + " = ? AND " + LOCATION_COLUMN + " = ?", new String[]{scheduleDate, scheduleLocation});

                if (rowsAffected > 0) {
                    db.setTransactionSuccessful();
                    isDeleted = true;
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            isDeleted = false; // Handle exceptions if necessary
        } finally {
            // End the transaction (commit if successful, otherwise roll back)
            db.endTransaction();
        }
        return isDeleted;
    }

    public boolean deleteListItem(int scheduleId, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(LIST_TABLE, LIST_SCHEDULE_ID + " = ? AND " + ITEM_NAME + " = ?", new String[]{String.valueOf(scheduleId), name});
        return rowsAffected > 0; // Returns true if rows were deleted
    }

    private boolean checkForDuplicateSchedules(SQLiteDatabase db, String date, String location) {
        Cursor cursor = db.rawQuery("SELECT EXISTS (SELECT 1 FROM " + SCHEDULE_TABLE + " WHERE " + DATE_COLUMN + " = ? AND " + LOCATION_COLUMN + " = ?)", new String[]{date, location});
        boolean exists = false;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                exists = cursor.getInt(0) == 1; // Get the result (1 = exists, 0 = does not exist)
            }
            cursor.close(); // Close the cursor to free resources
        }
        return exists;
    }

    private boolean checkForDuplicateListItems(SQLiteDatabase db, int scheduleId, String name) {
        Cursor cursor = db.rawQuery("SELECT EXISTS (SELECT 1 FROM " + LIST_TABLE + " WHERE " + LIST_SCHEDULE_ID + " = ? AND " + ITEM_NAME + " = ?)", new String[]{String.valueOf(scheduleId), name});

        boolean exists = false;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                exists = cursor.getInt(0) == 1; // Get the result (1 = exists, 0 = does not exist)
            }
            cursor.close(); // Close the cursor to free resources
        }
        return exists;
    }
}
