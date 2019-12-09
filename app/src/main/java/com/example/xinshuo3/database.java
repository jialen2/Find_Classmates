package com.example.xinshuo3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class database extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "registers.db";
    private static final String TABLE_NAME = "registerusers";
    public database(Context context) { super(context, DATABASE_NAME, null, 1);}
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("Create Table registerusers (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, major TEXT, grade TEXT, first_course TEXT, second_course TEXT, third_course TEXT)");
    }
    public void onUpgrade(SQLiteDatabase database, int i, int j) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
    public long addUser(String user, String password, String major, String grade, String first_course, String second_course, String third_course) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //System.out.println()
        values.put("username", user);
        values.put("password", password);
        values.put("major", major);
        values.put("grade", grade);
        values.put("first_course", first_course);
        values.put("second_course", second_course);
        values.put("third_course", third_course);
        long res = db.insert(TABLE_NAME, null, values);
        System.out.println("saved");
        db.close();
        return res;
    }

    public Cursor fetch() {
        db = this.getWritableDatabase();
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{"username", "password", "major", "grade", "first_course", "second_course", "third_course"}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public List getAll() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from registerusers",null);
        List list = new ArrayList();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex("major"));
                list.add(name);
                cursor.moveToNext();
            }
        }
        //db.delete(TABLE_NAME, null, null);
        System.out.println("size:" + list.size());
        return list;
    }

    public int countAll() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from registerusers",null);
        int count = 0;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                count++;
                cursor.moveToNext();
            }
        }
        return count;
    }
}
