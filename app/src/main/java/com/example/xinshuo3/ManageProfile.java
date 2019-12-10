package com.example.xinshuo3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

public class ManageProfile extends AppCompatActivity {

    private String getPassword;
    private database db;
    private static int loginStep = 1;
    private TextInputLayout name;
    private EditText confirm;
    private EditText passwords;
    private Button next;
    private String getfirst_course;
    private String getsecond_course;
    private String getthird_course;
    private String getgrade;
    private String getmajor;
    private TextInputLayout first_course;
    private TextInputLayout second_course;
    private TextInputLayout third_course;
    private TextInputLayout major;
    private TextInputLayout grade;
    private String getName;
    private String getPasswords;
    private ConstraintLayout step_two;
    private  ConstraintLayout step_one;
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(loginStep);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        step_two = findViewById(R.id.step_two);
        step_two.setVisibility(View.VISIBLE);
        String current_name = Savedata.currentName;
        database db = new database(this);
        SQLiteDatabase data = db.getWritableDatabase();
        String query = "Select * From registerusers where username = '"+current_name+ "'";
        Cursor cursor = data.rawQuery(query, null);
        cursor.moveToLast();
        grade = step_two.findViewById(R.id.grade);
        major = step_two.findViewById(R.id.major);
        first_course = step_two.findViewById(R.id.first_course);
        second_course = step_two.findViewById(R.id.second_course);
        third_course = step_two.findViewById(R.id.third_course);
        Button finish = step_two.findViewById(R.id.finish);
        String current_grade = cursor.getString(cursor.getColumnIndex("grade"));
        String current_major = cursor.getString(cursor.getColumnIndex("major"));
        String current_firstCourse = cursor.getString(cursor.getColumnIndex("first_course"));
        String current_secondCourse = cursor.getString(cursor.getColumnIndex("second_course"));
        String current_thirdCourse = cursor.getString(cursor.getColumnIndex("third_course"));
        grade.getEditText().setText(current_grade);
        major.getEditText().setText(current_major);
        first_course.getEditText().setText(current_firstCourse);
        second_course.getEditText().setText(current_secondCourse);
        third_course.getEditText().setText(current_thirdCourse);
        finish.setText("save");
        finish.setOnClickListener(unused -> save());
    }
    protected void save() {
        getgrade = grade.getEditText().getText().toString().trim().replace(" ", "");
        getmajor = major.getEditText().getText().toString().trim().replace(" ", "");
        getfirst_course = first_course.getEditText().getText().toString().trim().replace(" ", "");
        getsecond_course = second_course.getEditText().getText().toString().trim().replace(" ", "");
        getthird_course = third_course.getEditText().getText().toString().trim().replace(" ", "");
        if (getsecond_course == null) {
            getsecond_course = "";
        }
        if (getthird_course == null) {
            getthird_course = "";
        }
        if (getfirst_course == null || getfirst_course.length() == 0) {
            Toast.makeText(this, "You should enter at least one course you are confident with", Toast.LENGTH_SHORT).show();
        } else if (getmajor == null || getmajor.length() == 0) {
            Toast.makeText(this, "major blank should not be left empty", Toast.LENGTH_SHORT).show();
        } else if (getgrade == null || getgrade.length() == 0) {
            Toast.makeText(this, "grade blank should not be left empty", Toast.LENGTH_SHORT).show();
        } else {
            db = new database(this);
            SQLiteDatabase data = db.getWritableDatabase();
            data.execSQL("UPDATE registerusers SET major = '"+ getmajor + "' WHERE username = '"+Savedata.currentName+ "'");
            data.execSQL("UPDATE registerusers SET grade = '"+ getgrade + "' WHERE username = '"+Savedata.currentName+ "'");
            data.execSQL("UPDATE registerusers SET first_course = '"+ getfirst_course + "' WHERE username = '"+Savedata.currentName+ "'");
            data.execSQL("UPDATE registerusers SET second_course = '"+ getsecond_course + "' WHERE username = '"+Savedata.currentName+ "'");
            data.execSQL("UPDATE registerusers SET third_course = '"+ getthird_course + "' WHERE username = '"+Savedata.currentName+ "'");
            name = null;
            getPassword = null;
            Toast.makeText(this, "You have successfully changed your profile", Toast.LENGTH_SHORT).show();
            Cursor cursor = db.fetch();
            step_two.setVisibility(View.INVISIBLE);
            loginStep = 1;
            NotificationsFragment fragment = new NotificationsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.profile, fragment);
            transaction.commit();
            finish();
            System.out.println("finish");
        }
    }

}
