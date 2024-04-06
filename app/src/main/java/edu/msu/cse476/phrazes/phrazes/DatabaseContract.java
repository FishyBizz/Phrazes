package edu.msu.cse476.phrazes.phrazes;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() {}

    public static class CardEntry implements BaseColumns {
        public static String TABLE_NAME = null;
        public static final String COLUMN_CATEGORY = null;
        public static final String COLUMN_CONTENT = null;
    }

    public static final String SQL_CREATE_TABLE_CARDS =
            "CREATE TABLE " + CardEntry.TABLE_NAME + " (" +
                    CardEntry._ID + " INTEGER PRIMARY KEY," +
                    CardEntry.COLUMN_CATEGORY + " TEXT," +
                    CardEntry.COLUMN_CONTENT + " TEXT)";

    public static final String SQL_DELETE_TABLE_CARDS =
            "DROP TABLE IF EXISTS " + CardEntry.TABLE_NAME;
}
