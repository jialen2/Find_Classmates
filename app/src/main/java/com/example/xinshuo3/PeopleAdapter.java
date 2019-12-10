package com.example.xinshuo3;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private List<People> mPeopleList;
    private Context mContext;
    private AppCompatActivity newActivity;
    public PeopleAdapter(List<People> peopleList, Context context) {
        mPeopleList = peopleList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chunk_people, parent, false);
        newActivity = (AppCompatActivity) view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        People peopleList = mPeopleList.get(position);
        holder.name.append(peopleList.getUsername());
        holder.major.append(peopleList.getMajor());
        holder.firstCourse.append(peopleList.getFirst_course());
        holder.secondCourse.append(peopleList.getSecond_course());
        holder.thirdCourse.append(peopleList.getThird_course());
        holder.grade.append(peopleList.getGrade());
        String chosed_name = holder.name.getText().toString().trim();
        String[] choosed_name = chosed_name.split(":");
        holder.contact.setOnClickListener(unused -> contact(choosed_name[1].trim()));
    }

    public void contact(String given_name) {
        System.out.println("Button working");
        Gson gson = new Gson();
        String current = Savedata.currentName;
        if (current == null) {
            Toast.makeText(mContext, "Please sign in before making a match", Toast.LENGTH_SHORT).show();
            return;
        }
        database db = new database(mContext);
        SQLiteDatabase data = db.getWritableDatabase();
        String query = "Select * From registerusers where username = '"+current+ "'";
        Cursor cursor = data.rawQuery(query, null);
        cursor.moveToLast();
        String current_contacts = cursor.getString(cursor.getColumnIndex("contacts"));
        String newcontacts;
        if (current_contacts.length() != 0) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> contacts = gson.fromJson(current_contacts, type);
            contacts.clear();
            contacts.add(given_name);
            newcontacts= gson.toJson(contacts);
        } else {
            ArrayList<String> newone = new ArrayList<String>();
            newone.add(given_name);
            newcontacts = gson.toJson(newone);
        }
        data.execSQL("UPDATE registerusers SET contacts = '"+ newcontacts + "' WHERE username = '"+current+ "'");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> contact = gson.fromJson(current_contacts, type);
        Toast.makeText(mContext, "You hava add a contact successfully", Toast.LENGTH_SHORT).show();
        /*
        NotificationsFragment fragment = new NotificationsFragment();
        FragmentTransaction transaction = newActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.toSwitch, fragment);
        transaction.commit();
        System.out.println("get here");

         */
        newActivity.finish();
        //((AppCompatActivity) mContext).finish();
    }

    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView firstCourse;
        TextView secondCourse;
        TextView thirdCourse;
        TextView name;
        TextView major;
        Button contact;
        TextView grade;

        public ViewHolder(View peopleList) {
            super(peopleList);
            firstCourse = peopleList.findViewById(R.id.firstCourse);
            secondCourse = peopleList.findViewById(R.id.secondCourse);
            thirdCourse = peopleList.findViewById(R.id.thirdCourse);
            name = peopleList.findViewById(R.id.name);
            major = peopleList.findViewById(R.id.major);
            contact = peopleList.findViewById(R.id.contact);
            grade = peopleList.findViewById(R.id.grade);
        }
        public TextView getName() {
            return name;
        }
    }
}
