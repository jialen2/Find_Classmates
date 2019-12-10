package com.example.xinshuo3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private String getUsername;

    private String getPassword;

    private TextInputLayout username;

    private TextView showUsername;

    private EditText password;

    private ConstraintLayout login;

    private ConstraintLayout loggedIn;

    private database db;

    private NotificationsViewModel notificationsViewModel;

    private View root;

    private static boolean SigninOrnot = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        showUsername = root.findViewById(R.id.showUsername);
        login = root.findViewById(R.id.toLogin);
        loggedIn = root.findViewById(R.id.loginSuccess);
        username = login.findViewById(R.id.username);
        password = login.findViewById(R.id.password);
        ListView option = root.findViewById(R.id.options);
        String[] options = new String[] {"Manage Your Profile", "Log out"};
        List<String> options_list = new ArrayList<String>(Arrays.asList(options));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, options_list);
        option.setAdapter(arrayAdapter);
        option.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (selectedItem.equals("Log out")) {
                    loggedIn.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    showUsername.setText(Savedata.currentName);
                    password.setText("");
                    SigninOrnot = false;
                } else if (selectedItem.equals("Manage Your Profile")) {
                    Intent intent = new Intent(getActivity(), ManageProfile.class);
                    startActivity(intent);
                    //getActivity().finish();
                }
            }
        });
        if (SigninOrnot == false) {
            login.setVisibility(View.VISIBLE);
        } else {
            loggedIn.setVisibility(View.VISIBLE);
        }
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
        Savedata.currentName = getUsername;
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
                SigninOrnot = true;
                Toast.makeText(getActivity(), "You have successfully signed in", Toast.LENGTH_SHORT).show();
                loggedIn.setVisibility(View.VISIBLE);
                showUsername.setText(getUsername);
                login.setVisibility(View.GONE);
                //getActivity().finish();
            }
        } else {
            Toast.makeText(getActivity(),"Account Doesn't exist",Toast.LENGTH_SHORT).show();
        }

    }

}