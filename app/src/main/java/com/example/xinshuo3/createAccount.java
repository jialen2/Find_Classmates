package com.example.xinshuo3;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;


public class createAccount extends AppCompatActivity {
    private static int loginStep = 1;
    private TextInputLayout username;
    private EditText confirm;
    private EditText password;
    private database db;
    private static String name;
    private static String getPassword;
    private String confirmPassword;
    private TextInputLayout first_course;
    private TextInputLayout second_course;
    private TextInputLayout third_course;
    private TextInputLayout major;
    private TextInputLayout grade;
    private String getfirst_course;
    private String getsecond_course;
    private String getthird_course;
    private String getgrade;
    private String getmajor;
    private ConstraintLayout step_one;
    private ConstraintLayout step_two;
    private Map<String, String> userinfo = new HashMap<String, String>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_account);
        step_one = findViewById(R.id.step_one);
        step_two = findViewById(R.id.step_two);
        if (loginStep == 1) {
            step_one.setVisibility(View.VISIBLE);
        } else {
            step_two.setVisibility(View.VISIBLE);
        }
        Button create = step_one.findViewById(R.id.create);
        grade = step_two.findViewById(R.id.grade);
        major = step_two.findViewById(R.id.major);
        username = step_one.findViewById(R.id.userName);
        password = step_one.findViewById(R.id.password);
        confirm = step_one.findViewById(R.id.confirmPassword);
        first_course = step_two.findViewById(R.id.first_course);
        second_course = step_two.findViewById(R.id.second_course);
        third_course = step_two.findViewById(R.id.third_course);
        Button finish = step_two.findViewById(R.id.finish);
        db = new database(this);
        create.setOnClickListener(unused -> createClicked());
        finish.setOnClickListener(unused -> finishClicked());
    }
    protected void createClicked() {
        name = username.getEditText().getText().toString().trim().replace(" ", "");
        getPassword = password.getText().toString().trim().replace(" ", "");
        System.out.println("test " + name);
        System.out.println("test " + getPassword);
        confirmPassword = confirm.getText().toString().trim();
        SQLiteDatabase data = db.getWritableDatabase();
        if (name == null || name.length() == 0) {
            Toast.makeText(createAccount.this, "username blank should not be left empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String query = "Select * From registerusers where username = '"+name+ "'";
        Cursor cursor = data.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            Toast.makeText(createAccount.this, "Account already exists", Toast.LENGTH_SHORT).show();
        } else if (getPassword == null || getPassword.length() == 0) {
            Toast.makeText(createAccount.this, "password blank should not be left empty", Toast.LENGTH_SHORT).show();
        } else if (confirm == null || confirm.length() == 0) {
            Toast.makeText(createAccount.this, "comfirm password blank should not be left empty", Toast.LENGTH_SHORT).show();
        } else if(!confirmPassword.equals(getPassword)){
            Toast.makeText(createAccount.this,"Password Not matching",Toast.LENGTH_SHORT).show();
            password.setText("");
            confirm.setText("");
        } else if(getPassword.length() < 6) {
            Toast.makeText(createAccount.this,"Password should not have less than 6 characters",Toast.LENGTH_SHORT).show();
            password.setText("");
            confirm.setText("");
        } else {
            step_one.setVisibility(View.INVISIBLE);
            //db  = new database(this);
            //long val = db.addUser(name, getPassword);
            loginStep = 2;
            Intent intent = new Intent(this, createAccount.class);
            startActivity(intent);
            finish();
        }
    }
    protected void finishClicked() {
        System.out.println("name" + name);
        System.out.println("password" + getPassword);
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
            Toast.makeText(createAccount.this, "You should enter at least one course you are confident with", Toast.LENGTH_SHORT).show();
        } else if (getmajor == null || getmajor.length() == 0) {
            Toast.makeText(createAccount.this, "major blank should not be left empty", Toast.LENGTH_SHORT).show();
        } else if (getgrade == null || getgrade.length() == 0) {
            Toast.makeText(createAccount.this, "grade blank should not be left empty", Toast.LENGTH_SHORT).show();
        } else {
            db = new database(this);
            long val = db.addUser(name, getPassword, getmajor, getgrade, getfirst_course, getsecond_course, getthird_course);
            name = null;
            getPassword = null;
            Toast.makeText(createAccount.this, "You have successfully create your account", Toast.LENGTH_SHORT).show();
            Cursor cursor = db.fetch();
            System.out.println(cursor.getString(cursor.getColumnIndex("major")));
            step_two.setVisibility(View.INVISIBLE);
            loginStep = 1;
            NotificationsFragment fragment = new NotificationsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.step_two, fragment);
            transaction.commit();
            finish();
        }
    }
}
