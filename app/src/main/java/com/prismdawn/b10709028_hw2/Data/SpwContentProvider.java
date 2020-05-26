package com.prismdawn.b10709028_hw2.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static com.prismdawn.b10709028_hw2.Data.SpwContract.SpwEntry.TABLE_NAME;

public class SpwContentProvider extends ContentProvider {

    public static final int ITEMLIST = 100;
    public static final int ITEMLIST_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SpwDbHelper mSpwDbHelper;

    public SpwContentProvider() {
    }

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(SpwContract.AUTHORITY, SpwContract.PATH_ITEMLIST, ITEMLIST);
        uriMatcher.addURI(SpwContract.AUTHORITY, SpwContract.PATH_ITEMLIST + "/#", ITEMLIST_WITH_ID);

        return uriMatcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mSpwDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int itemDeleted;

        switch (match) {
            case ITEMLIST_WITH_ID:
                String id = uri.getPathSegments().get(1);
                itemDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (itemDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return itemDeleted;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mSpwDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case ITEMLIST:
                long id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(SpwContract.SpwEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mSpwDbHelper = new SpwDbHelper(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mSpwDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case ITEMLIST:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
