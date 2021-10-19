package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {

    public static Context mContext;
    ArrayList<TodayExItemRoutine> selectTodayExArrayList;
    SelectItem selectItem = new SelectItem();
    Context context;

    Intent intent = new Intent();



    public SelectAdapter(ArrayList<TodayExItemRoutine> selectTodayExArrayList, Context context){
        this.selectTodayExArrayList = selectTodayExArrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public SelectAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.activity_select_today_ex, parent, false);
            context=parent.getContext();

            return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SelectAdapter.ViewHolder holder, int position) {
        TodayExItemRoutine selectTodayEx = selectTodayExArrayList.get(position);
        holder.routine_whereEx.setText(selectTodayEx.getroutine_WhereEx());
        holder.routine_second.setText(""+selectTodayEx.getroutine_second());
        holder.routine_set.setText(""+selectTodayEx.getroutine_set());


    }

    @Override
    public int getItemCount() {
        return selectTodayExArrayList.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView routine_whereEx;
        private TextView routine_second;
        private TextView routine_set;
        private TextView finish;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            routine_whereEx = itemView.findViewById(R.id.routine_whereEx);
            routine_second = itemView.findViewById(R.id.routine_second);
            routine_set = itemView.findViewById(R.id.routine_set);


        }



    }
}
