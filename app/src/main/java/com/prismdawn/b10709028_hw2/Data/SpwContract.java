package com.prismdawn.b10709028_hw2.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class SpwContract {

    public static final String AUTHORITY = "com.prismdawn.b10709028_hw2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_ITEMLIST = "itemlist";

    private SpwContract() {
    }

    public static final class SpwEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEMLIST).build();
        public static final String TABLE_NAME = "itemlist";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_SIZE = "Size";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
