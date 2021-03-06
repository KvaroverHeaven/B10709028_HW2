package com.prismdawn.b10709028_hw2.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prismdawn.b10709028_hw2.Data.SpwContract.SpwEntry;

public class SpwDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "itemlist.db";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + SpwEntry.TABLE_NAME + " (" +
            SpwEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SpwEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            SpwEntry.COLUMN_SIZE + " INTEGER NOT NULL, " +
            SpwEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            "); ";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SpwEntry.TABLE_NAME;

    public SpwDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
