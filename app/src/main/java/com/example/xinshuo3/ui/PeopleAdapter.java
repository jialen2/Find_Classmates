package com.example.xinshuo3.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xinshuo3.People;
import com.example.xinshuo3.R;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private List<People> mPeopleList;
    private Context mContext;
    public PeopleAdapter(List<People> peopleList, Context context) {
        mPeopleList = peopleList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chunk_people, parent, false);
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
        holder.score.append(peopleList.getScore());
    }

    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView score;
        TextView firstCourse;
        TextView secondCourse;
        TextView thirdCourse;
        TextView name;
        TextView major;

        public ViewHolder(View peopleList) {
            super(peopleList);
            score = peopleList.findViewById(R.id.score);
            firstCourse = peopleList.findViewById(R.id.first_course);
            secondCourse = peopleList.findViewById(R.id.second_course);
            thirdCourse = peopleList.findViewById(R.id.third_course);
            name = peopleList.findViewById(R.id.name);
            major = peopleList.findViewById(R.id.major);
        }
    }
}
