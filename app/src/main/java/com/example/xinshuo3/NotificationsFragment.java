package com.example.xinshuo3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private String getUsername;

    private String getPassword;

    private TextInputLayout username;

    private EditText password;

    private database db;

    private NotificationsViewModel notificationsViewModel;

    private View root;

    public NotificationsFragment() {}

    private static boolean SigninOrnot = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        ConstraintLayout tologin = root.findViewById(R.id.toLogin);
        ConstraintLayout loggedin = root.findViewById(R.id.loginSuccess);
        if (SigninOrnot == false) {
            tologin.setVisibility(View.VISIBLE);
        } else {
            loggedin.setVisibility(View.VISIBLE);
        }
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        username = tologin.findViewById(R.id.username);
        password = tologin.findViewById(R.id.password);
        db = new database(getActivity());
        Button creatAccount = root.findViewById(R.id.createAccount);
        creatAccount.setOnClickListener(unused -> create());
        Button tosignin = root.findViewById(R.id.signin);
        tosignin.setOnClickListener(unused -> signIn());
        return root;
    }

    protected void create() {
        Intent intent = new Intent(getActivity(), createAccount.class);
        //db = new database(getActivity());
        List list = db.getAll();
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
            Cursor cursor = db.fetch();
            System.out.println(cursor.getString(cursor.getColumnIndex("username")));
        }
        startActivity(intent);
    }
    protected void signIn() {
        getUsername = username.getEditText().getText().toString().trim().replace(" ", "");
        getPassword = password.getText().toString().trim().replace(" ", "");
        //Intent intent = new Intent(getActivity(), HomeFragment.class);
        SQLiteDatabase data = db.getWritableDatabase();
        String query = "Select * From registerusers where username = '"+getUsername+ "'";
        Cursor cursor = data.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            String passwordEntered = cursor.getString(cursor.getColumnIndex("password"));
            if (!passwordEntered.equals(getPassword)) {
                Toast.makeText(getActivity(),"Password Not matching",Toast.LENGTH_SHORT).show();
                password.setText("");
            } else {
                HomeFragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.totest, fragment);
                SigninOrnot = true;
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Toast.makeText(getActivity(), "You have successfully signed in", Toast.LENGTH_SHORT).show();
                //getActivity().finish();
            }
        } else {
            Toast.makeText(getActivity(),"Account Doesn't exist",Toast.LENGTH_SHORT).show();
        }

    }
}