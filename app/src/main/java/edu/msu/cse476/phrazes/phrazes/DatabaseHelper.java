package edu.msu.cse476.phrazes.phrazes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "phrazes.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your table(s) here
        db.execSQL(DatabaseContract.SQL_CREATE_TABLE_CARDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade your database here if needed
        db.execSQL(DatabaseContract.SQL_DELETE_TABLE_CARDS);
        onCreate(db);
    }
}
