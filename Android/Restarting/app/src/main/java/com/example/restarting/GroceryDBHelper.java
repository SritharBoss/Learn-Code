package com.example.restarting;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.restarting.ItemContract.*;

public class GroceryDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="groceryList.db";
    private static final int DB_VERSION=1;
    private static final String onCreateQuery="CREATE TABLE " +
            ContractEntry.TABLE_NAME + " (" +
            ContractEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ContractEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            ContractEntry.COLUMN_COUNT + " INTEGER NOT NULL, " +
            ContractEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");";
    private static final String onUpgradeQuery="DROP TABLE IF EXISTS "+ContractEntry.TABLE_NAME;

    public GroceryDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(onCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(onUpgradeQuery);
        onCreate(db);
    }
}
