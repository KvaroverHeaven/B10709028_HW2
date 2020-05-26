package com.prismdawn.b10709028_hw2.Data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.prismdawn.b10709028_hw2.Data.SpwContract.SpwEntry;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db) {
        if (db == null) {
            return;
        }
        List<ContentValues> list = new ArrayList<>();

        ContentValues cv = new ContentValues();
        cv.put(SpwEntry.COLUMN_NAME, "John");
        cv.put(SpwEntry.COLUMN_SIZE, 12);
        list.add(cv);

        cv = new ContentValues();
        cv.put(SpwEntry.COLUMN_NAME, "Tim");
        cv.put(SpwEntry.COLUMN_SIZE, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(SpwEntry.COLUMN_NAME, "Jessica");
        cv.put(SpwEntry.COLUMN_SIZE, 99);
        list.add(cv);

        cv = new ContentValues();
        cv.put(SpwEntry.COLUMN_NAME, "Larry");
        cv.put(SpwEntry.COLUMN_SIZE, 1);
        list.add(cv);

        cv = new ContentValues();
        cv.put(SpwEntry.COLUMN_NAME, "Kim");
        cv.put(SpwEntry.COLUMN_SIZE, 45);
        list.add(cv);

        try {
            db.beginTransaction();
            db.delete(SpwEntry.TABLE_NAME, null, null);
            list.stream().forEach(c -> {
                db.insert(SpwEntry.TABLE_NAME, null, c);
            });
            db.setTransactionSuccessful();
        } catch (SQLException e) {
        } finally {
            db.endTransaction();
        }

    }
}
