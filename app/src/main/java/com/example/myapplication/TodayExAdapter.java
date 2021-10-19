package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TodayExAdapter extends RecyclerView.Adapter<TodayExAdapter.ViewHolder> {


    ArrayList<TodayExItemRoutine> todayExItemRoutines;
    Context context;
    ArrayList<TodayExItemRoutine> recyclerViewArrayList = new ArrayList<>();


    public ArrayList<TodayExItemRoutine> getList() {
            return recyclerViewArrayList;
    }

    public TodayExAdapter(ArrayList<TodayExItemRoutine> RoutineItems, Context context) {
        this.todayExItemRoutines = RoutineItems;
        this.context = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_today_ex_item_routine, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        TodayExItemRoutine todayExItemRoutine = todayExItemRoutines.get(position);

        holder.routine_whereEx.setText(todayExItemRoutine.getroutine_WhereEx());
        holder.routine_second.setText("" + todayExItemRoutine.getroutine_second());
        holder.routine_set.setText("" + todayExItemRoutine.getroutine_set());


        holder.checkbox.setChecked(todayExItemRoutine.getSelected());
        holder.checkbox.setTag(position);
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer)holder.checkbox.getTag();

                if(holder.checkbox.isChecked()){
                    holder.checkbox.setChecked(true);
                    recyclerViewArrayList .add(todayExItemRoutines.get(pos));

                }
                else {
                    holder.checkbox.setChecked(false);
                    recyclerViewArrayList .remove(todayExItemRoutines.get(pos));

                }

            }

        });

    }


    @Override
    public int getItemCount() {
        return todayExItemRoutines.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView routine_whereEx;
        private TextView routine_second;
        private TextView routine_set;
        private CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            routine_whereEx = itemView.findViewById(R.id.routine_whereEx);
            routine_second = itemView.findViewById(R.id.routine_second);
            routine_set = itemView.findViewById(R.id.routine_set);
            checkbox = itemView.findViewById(R.id.checkbox);

        }
    }
}
