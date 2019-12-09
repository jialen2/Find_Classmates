package com.example.xinshuo3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xinshuo3.ui.PeopleAdapter;

import java.util.ArrayList;
import java.util.List;

public class Paring extends AppCompatActivity {
    private String username;
    private String grade;
    private String major;
    private String first_course;
    private String second_course;
    private String third_course;
    private RecyclerView peopleRecyclerView;
    private PeopleAdapter peopleAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.paring);
        String[] people = intent.getStringArrayExtra("People");
        List<People> peopleList = new ArrayList<>();
        for (int i = 0; i < people.length; i++) {
            if (people[i] == null) {
                break;
            }
            database db = new database(this);
            String User = people[i];
            System.out.println("USER " + User);
            String query = "Select * From registerusers where username = '" + User + "'";
            SQLiteDatabase data = db.getWritableDatabase();
            Cursor cursor = data.rawQuery(query, null);
            if (cursor == null) {
                System.out.println(true);
            }
            cursor.moveToLast();
            int test = cursor.getType(-1);
            grade = cursor.getString(cursor.getColumnIndex("grade"));
            major = cursor.getString(cursor.getColumnIndexOrThrow("major"));
            first_course = cursor.getString(cursor.getColumnIndex("first_course"));
            second_course = cursor.getString(cursor.getColumnIndex("second_course"));
            third_course = cursor.getString(cursor.getColumnIndex("third_course"));
            People newpeople = new People(User, major, grade, first_course, second_course, third_course, "0");
            peopleList.add(newpeople);
            System.out.println(cursor.getString(cursor.getColumnIndex("username")));
            System.out.println(cursor.getString(cursor.getColumnIndex("major")));
            System.out.println(cursor.getString(cursor.getColumnIndex("first_course")));
            System.out.println(cursor.getString(cursor.getColumnIndex("second_course")));
            System.out.println(cursor.getString(cursor.getColumnIndex("third_course")));

        }
        peopleRecyclerView = findViewById(R.id.recyclerView);
        peopleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        peopleAdapter = new PeopleAdapter(peopleList, this);
        peopleRecyclerView.setAdapter(peopleAdapter);
    }
}
