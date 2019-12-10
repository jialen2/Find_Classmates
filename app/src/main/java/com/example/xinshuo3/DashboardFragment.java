package com.example.xinshuo3;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private ArrayList<People> peopleList;
    private String username;
    private String grade;
    private String major;
    private String first_course;
    private String second_course;
    private String third_course;
    private RecyclerView anotherRecyclerView;
    private AnotherAdapter anotherAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        peopleList = new ArrayList<>();
        String current = Savedata.currentName;
        Gson gson = new Gson();
        database db = new database(getActivity());
        SQLiteDatabase data = db.getWritableDatabase();
        String query = "Select * From registerusers where username = '"+current+ "'";
        Cursor cursor = data.rawQuery(query, null);
        cursor.moveToLast();
        String current_contacts = cursor.getString(cursor.getColumnIndex("contacts"));
        if (current_contacts.length() == 0) {
            return root;
        }
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> contacts = gson.fromJson(current_contacts, type);
        for (int i = 0; i < contacts.size(); i++) {
            username = contacts.get(i);
            if (username.equals(current)) {
                return root;
            }
            String anotherQuery = "Select * From registerusers where username = '"+username+ "'";
            Cursor anotherCursor = data.rawQuery(anotherQuery, null);
            anotherCursor.moveToLast();
            grade = anotherCursor.getString(cursor.getColumnIndex("grade"));
            major = anotherCursor.getString(cursor.getColumnIndexOrThrow("major"));
            first_course = anotherCursor.getString(cursor.getColumnIndex("first_course"));
            second_course = anotherCursor.getString(cursor.getColumnIndex("second_course"));
            third_course = anotherCursor.getString(cursor.getColumnIndex("third_course"));
            People newpeople = new People(username, major, grade, first_course, second_course, third_course);
            peopleList.add(newpeople);
        }
        anotherRecyclerView = root.findViewById(R.id.recyclerView);
        anotherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        anotherAdapter = new AnotherAdapter(peopleList, getActivity());
        anotherRecyclerView.setAdapter(anotherAdapter);
        return root;
    }
}