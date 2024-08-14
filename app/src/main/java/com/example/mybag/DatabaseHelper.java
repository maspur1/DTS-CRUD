package com.example.mybag;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Constants for the User table
    public static final String USER_DATABASE_NAME = "AppMHS";
    public static final String USER_TABLE_NAME = "tbluser";
    public static final String USER_COL_1 = "ID";
    public static final String USER_COL_2 = "USER";
    public static final String USER_COL_3 = "PASSWORD";

    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, USER_DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user table
        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + " (" +
                USER_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_COL_2 + " TEXT, " +
                USER_COL_3 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade user table
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }

    // User table methods
    public boolean insertUser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_2, user);
        contentValues.put(USER_COL_3, password);
        long result = db.insert(USER_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE USER = ?", new String[]{user});
        return cursor.getCount() > 0;
    }

    public boolean checkUserPassword(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE USER = ? AND PASSWORD = ?", new String[]{user, password});
        return cursor.getCount() > 0;
    }
}
