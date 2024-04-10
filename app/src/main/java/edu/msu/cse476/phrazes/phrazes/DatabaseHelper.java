package edu.msu.cse476.phrazes.phrazes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "phrazes.db";
    private static final int DATABASE_VERSION = 1;

    private DatabaseReference rdatabase = FirebaseDatabase.getInstance().getReference();
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your table(s) here
        String defaultTableName = "Phazes_Default";
        DatabaseContract.setTableName(defaultTableName);
        String SQL_CREATE_TABLE_CARDS = "CREATE TABLE " + DatabaseContract.getTableName()
                + " (" +
                DatabaseContract.CardEntry._ID + " INTEGER PRIMARY KEY," +
                DatabaseContract.CardEntry.COLUMN_CONTENT + " TEXT)";
        db.execSQL(SQL_CREATE_TABLE_CARDS);
        setDefault(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade your database here if needed
        db.execSQL(DatabaseContract.SQL_DELETE_TABLE_CARDS);
        onCreate(db);
    }
    public void setDefault(SQLiteDatabase db){
        String[] defaultData = {"Dog", "Cat", "Shard", "Lion", "Hawk", "Rat", "Dolphin",
                "Alligator", "Giraffe", "Panther", "Sparty", "Turtle", "Raven", "Whale", "Octopus",
                "Seagull", "Llama", "Cow", "Snake", "Pig", "Kangaroo", "Monkey", "Dragon", "Zebra"};
        for (String data : defaultData){
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.CardEntry.COLUMN_CONTENT, data);
            db.insert(DatabaseContract.getTableName(), null, values);
        }
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

    public void downloadCategoryData(String categoryName, Context context) {
        // Get reference to the specific category node in Firebase
        DatabaseReference categoryRef = rdatabase.child("categories").child(categoryName);
        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseContract.setTableName(categoryName);
                String createTableQuery = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.getTableName()
                        + " (" +
                        DatabaseContract.CardEntry._ID + " INTEGER PRIMARY KEY," +
                        DatabaseContract.CardEntry.COLUMN_CONTENT + " TEXT)";
                database = getWritableDatabase();
                database.execSQL(createTableQuery);
                SQLiteDatabase db = getWritableDatabase();
                // Clear existing data for this category in the local SQLite database
                db.delete(DatabaseContract.getTableName(), null, null);
                // Iterate through the snapshot to get each card and save it to the local database
                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                    String cardContent = cardSnapshot.getValue().toString();
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.CardEntry.COLUMN_CONTENT, cardContent);
                    db.insert(DatabaseContract.getTableName(), null, values);
                }
                Toast.makeText(context, "Category data downloaded and stored locally", Toast.LENGTH_SHORT).show();
                db.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
