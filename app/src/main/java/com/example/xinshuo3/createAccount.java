package com.example.xinshuo3;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.xinshuo3.ui.notifications.NotificationsFragment;
import com.example.xinshuo3.ui.notifications.database;
import com.google.android.material.textfield.TextInputLayout;


public class createAccount extends AppCompatActivity {
    TextInputLayout username;
    EditText confirm;
    EditText password;
    database db;
    String name;
    String getPassword;
    String confirmPassword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_account);
        Button create = findViewById(R.id.create);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirmPassword);
        db = new database(this);
        create.setOnClickListener(unused -> createClicked());
    }
    protected void createClicked() {
        name = username.getEditText().getText().toString().trim();
        getPassword = password.getText().toString().trim();
        confirmPassword = confirm.getText().toString().trim();
        if(!confirmPassword.equals(getPassword)){
            Toast.makeText(createAccount.this,"Password Not matching",Toast.LENGTH_SHORT).show();
            password.setText("");
            confirm.setText("");
        } else {
            db  = new database(this);
            long val = db.addUser(name, getPassword);
            System.out.println(name + getPassword);
            NotificationsFragment fragment = new NotificationsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.shi, fragment);
            transaction.commit();
            Cursor cursor = db.fetch();
            cursor.moveToFirst();
            System.out.println(cursor.toString());
            finish();
        }
        //Intent intent = new Intent(createAccount.this, NotificationsFragment.class);
        //startActivity(intent);
    }
}
