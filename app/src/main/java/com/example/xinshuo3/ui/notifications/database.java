package com.example.xinshuo3.ui.notifications;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class database extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "register.db";
    private static final String TABLE_NAME = "registeruser";
    public database(Context context) { super(context, DATABASE_NAME, null, 1);}
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("Create Table registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password Text)");
    }
    public void onUpgrade(SQLiteDatabase database, int i, int j) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
    public long addUser(String user, String password) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user);
        values.put("password", password);
        long res = db.insert(TABLE_NAME, null, values);
        System.out.println("saved");
        //db.close();
        return res;
    }

    //public void getData(String username) {
        /*
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int index = cursor.getColumnIndex(username);
        String password = cursor.getString(index);
        System.out.println(password);
        if (cursor.moveToFirst()) {
            do {
                if (username.equals(cursor.getColumnIndex(username)))
            } while(cursor.moveToNext());
        }
        */
    public Cursor fetch() {
        //db = this.getWritableDatabase();
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{"username", "password"}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public List getAll() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from registeruser",null);
        List list = new ArrayList();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex("username"));
                list.add(name);
                cursor.moveToNext();
            }
        }
        System.out.println(list.size());
        return list;
    }
}
