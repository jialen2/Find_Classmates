package com.example.xinshuo3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AnotherAdapter extends RecyclerView.Adapter<AnotherAdapter.ViewHolder>{
    private List<People> mPeopleList;
    private Context mContext;
    private AppCompatActivity newActivity;
    public AnotherAdapter(List<People> peopleList, Context context) {
        mPeopleList = peopleList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_list, parent, false);
        newActivity = (AppCompatActivity) view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnotherAdapter.ViewHolder holder, int position) {
        People peopleList = mPeopleList.get(position);
        holder.name.append(peopleList.getUsername());
        holder.major.append(peopleList.getMajor());
        holder.firstCourse.append(peopleList.getFirst_course());
        holder.secondCourse.append(peopleList.getSecond_course());
        holder.thirdCourse.append(peopleList.getThird_course());
        holder.grade.append(peopleList.getGrade());
        String chosed_name = holder.name.getText().toString().trim();
        String[] choosed_name = chosed_name.split(":");
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
        TextView grade;

        public ViewHolder(View peopleList) {
            super(peopleList);
            firstCourse = peopleList.findViewById(R.id.firstCourse);
            secondCourse = peopleList.findViewById(R.id.secondCourse);
            thirdCourse = peopleList.findViewById(R.id.thirdCourse);
            name = peopleList.findViewById(R.id.name);
            major = peopleList.findViewById(R.id.major);
            grade = peopleList.findViewById(R.id.grade);
        }
        public TextView getName() {
            return name;
        }
    }
}
