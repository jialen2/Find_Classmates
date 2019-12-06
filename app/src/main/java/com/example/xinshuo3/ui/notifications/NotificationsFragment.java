package com.example.xinshuo3.ui.notifications;

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

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.xinshuo3.R;
import com.example.xinshuo3.createAccount;
import com.example.xinshuo3.signIn;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public NotificationsFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        TextInputLayout username = root.findViewById(R.id.username);
        EditText password = root.findViewById(R.id.password);
        String getUsername = username.getEditText().getText().toString().trim();
        String getPassword = password.getText().toString().trim();
        Button creatAccount = root.findViewById(R.id.createAccount);
        creatAccount.setOnClickListener(unused -> create());
        Button tosignin = root.findViewById(R.id.signin);
        tosignin.setOnClickListener(unused -> signIn());
        return root;
    }

    protected void create() {
        Intent intent = new Intent(getActivity(), createAccount.class);
        database db = new database(getActivity());
        List list = db.getAll();
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        startActivity(intent);
    }
    protected void signIn() {
        Intent intent = new Intent(getActivity(), signIn.class);
        startActivity(intent);
    }
}