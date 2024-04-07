package edu.msu.cse476.phrazes.phrazes;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private static String tableName = "default";
    private DatabaseContract() {}

    public static class CardEntry implements BaseColumns {
        public static final String COLUMN_CONTENT = "content";
    }

    public static void setTableName(String name) {
        tableName = name;
    }

    public static String getTableName() {
        return tableName;
    }
    public static final String SQL_CREATE_TABLE_CARDS =
            "CREATE TABLE " + tableName + " (" +
                    CardEntry._ID + " INTEGER PRIMARY KEY," +
                    CardEntry.COLUMN_CONTENT + " TEXT)";

    public static final String SQL_DELETE_TABLE_CARDS =
            "DROP TABLE IF EXISTS " + tableName;
}
