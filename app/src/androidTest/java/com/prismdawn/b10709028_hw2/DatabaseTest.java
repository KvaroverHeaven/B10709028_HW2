package com.prismdawn.b10709028_hw2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.prismdawn.b10709028_hw2.Data.SpwContract.SpwEntry;
import com.prismdawn.b10709028_hw2.Data.SpwDbHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private final Context mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    private final Class mDbHelperClass = SpwDbHelper.class;

    @Before
    public void setUp() {
        deleteTheDatabase();
    }


    @Test
    public void create_database_test() throws Exception {

        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();


        String databaseIsNotOpen = "The database should be open and isn't";
        assertEquals(databaseIsNotOpen,
                true,
                database.isOpen());

        Cursor tableNameCursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='" +
                        SpwEntry.TABLE_NAME + "'",
                null);


        String errorInCreatingDatabase =
                "Error: This means that the database has not been created correctly";
        assertTrue(errorInCreatingDatabase,
                tableNameCursor.moveToFirst());

        assertEquals("Error: Your database was created without the expected tables.",
                SpwEntry.TABLE_NAME, tableNameCursor.getString(0));

        tableNameCursor.close();
    }

    @Test
    public void insert_single_record_test() throws Exception {

        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(SpwEntry.COLUMN_NAME, "test name");
        testValues.put(SpwEntry.COLUMN_SIZE, 99);

        long firstRowId = database.insert(
                SpwEntry.TABLE_NAME,
                null,
                testValues);

        assertNotEquals("Unable to insert into the database", -1, firstRowId);

        Cursor wCursor = database.query(
                SpwEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        String emptyQueryError = "Error: No Records returned from waitlist query";
        assertTrue(emptyQueryError,
                wCursor.moveToFirst());

        wCursor.close();
        dbHelper.close();
    }


    @Test
    public void autoincrement_test() throws Exception {

        insert_single_record_test();

        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(SpwEntry.COLUMN_NAME, "test name");
        testValues.put(SpwEntry.COLUMN_SIZE, 99);

        long firstRowId = database.insert(
                SpwEntry.TABLE_NAME,
                null,
                testValues);

        long secondRowId = database.insert(
                SpwEntry.TABLE_NAME,
                null,
                testValues);

        assertEquals("ID Autoincrement test failed!",
                firstRowId + 1, secondRowId);


    }


    @Test
    public void upgrade_database_test() throws Exception {

        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(SpwEntry.COLUMN_NAME, "test name");
        testValues.put(SpwEntry.COLUMN_SIZE, 99);

        long firstRowId = database.insert(
                SpwEntry.TABLE_NAME,
                null,
                testValues);

        long secondRowId = database.insert(
                SpwEntry.TABLE_NAME,
                null,
                testValues);

        dbHelper.onUpgrade(database, 0, 1);
        database = dbHelper.getReadableDatabase();

        Cursor tableNameCursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='" +
                        SpwEntry.TABLE_NAME + "'",
                null);

        assertTrue(tableNameCursor.getCount() == 1);


        Cursor wCursor = database.query(
                SpwEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        assertFalse("Database doesn't seem to have been dropped successfully when upgrading",
                wCursor.moveToFirst());

        tableNameCursor.close();
        database.close();
    }

    void deleteTheDatabase() {
        try {
            Field f = mDbHelperClass.getDeclaredField("DATABASE_NAME");
            f.setAccessible(true);
            mContext.deleteDatabase((String) f.get(null));
        } catch (NoSuchFieldException ex) {
            fail("Make sure you have a member called DATABASE_NAME in the WaitlistDbHelper");
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

    }
}
