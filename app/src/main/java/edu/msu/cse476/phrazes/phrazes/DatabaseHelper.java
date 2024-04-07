package edu.msu.cse476.phrazes.phrazes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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

    @SuppressLint("Range")
    public ArrayList<String> getWordsForCategory() {
        ArrayList<String> words = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Use DatabaseContract to get the current table name
        String tableName = DatabaseContract.getTableName();
        if (tableName == null) {
            // Handle the case where tableName has not been set yet
            return words; // Return an empty list
        }

        String[] projection = { DatabaseContract.CardEntry.COLUMN_CONTENT };

        Cursor cursor = db.query(
                tableName,     // Use the dynamically set table name
                projection,    // The columns to return
                null,          // The columns for the WHERE clause (null for all rows)
                null,          // The values for the WHERE clause
                null,          // Don't group the rows
                null,          // Don't filter by row groups
                null           // The sort order
        );

        int columnIndex = cursor.getColumnIndex(DatabaseContract.CardEntry.COLUMN_CONTENT);
        if(columnIndex != -1) {
            if (cursor.moveToFirst()) {
                do {
                    words.add(cursor.getString(cursor.getColumnIndex(
                            DatabaseContract.CardEntry.COLUMN_CONTENT)));
                } while (cursor.moveToNext());
            }
        } else {
            return words; // Return an empty list
        }
        cursor.close();
        db.close();
        return words;
    }
}