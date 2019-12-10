package com.example.xinshuo3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private TextInputLayout weak_subject;

    private String getWeak;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        Button paring = root.findViewById(R.id.startParing);
        weak_subject = root.findViewById(R.id.weakSubject);
            paring.setOnClickListener(unused -> topair());
        return root;
    }
    protected void topair() {
        Intent intent = new Intent(getActivity(), Paring.class);
        database db = new database(getActivity());;
        String[] people = new String[db.countAll()];
        getWeak = weak_subject.getEditText().getText().toString().trim().replace(" ", "");
        if (getWeak == null || getWeak.length() == 0) {
            Toast.makeText(getActivity(), "Subject blank should not be left empty", Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery("select * from registerusers",null);
        if (cursor == null) {
            System.out.println(true);
        }
        if (cursor.moveToFirst()) {
            int count = 0;
            while (!cursor.isAfterLast()) {
                if (getWeak.equals(cursor.getString(cursor.getColumnIndex("first_course")).trim().replace(" ", ""))
                || getWeak.equals(cursor.getString(cursor.getColumnIndex("second_course")).trim().replace(" ", ""))
                || getWeak.equals(cursor.getString(cursor.getColumnIndex("third_course")).trim().replace(" ", ""))) {
                    if (!(cursor.getString(cursor.getColumnIndex("username")).trim().replace(" ", "").equals(Savedata.currentName))){
                        people[count] = cursor.getString(cursor.getColumnIndex("username"));
                        count++;
                    }
                }
                cursor.moveToNext();
            }
        }
        if (people == null || people.length == 0 || people[0] == null) {
            Toast.makeText(getActivity(), "Appropriate match was not found", Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.putExtra("People", people);
            startActivity(intent);
        }
    }
}