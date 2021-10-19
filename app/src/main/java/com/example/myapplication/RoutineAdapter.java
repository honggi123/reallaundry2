package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {

    ArrayList<RoutineItem> RoutineItems;

    Context context;
    int selposition = 0;

    public RoutineAdapter(ArrayList<RoutineItem> RoutineItems, Context context) {
        this.RoutineItems = RoutineItems;
        this.context = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.routine_item, parent, false);
        context=parent.getContext();

        Log.i("tag","oncreateviewholder");
        return new ViewHolder(view);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RoutineItem routineItem = RoutineItems.get(position);
        //holder.setItem(routineItem);

        holder.routine_whereEx.setText(routineItem.getroutine_WhereEx());
        holder.routine_second.setText(routineItem.getroutine_second());
        holder.routine_set.setText(routineItem.getroutine_set());

        Log.i("tag", "onBindViewHolder");

//       Gson gson = new Gson();
//        String json = gson.toJson(RoutineItems);
//        Intent intent = new Intent(context,TodayExercise.class);
//        intent.putExtra("json",json);


        if(RoutineItems.get(position).getsel()){
            holder.view.setBackgroundColor(R.color.Lightgray);
        }else{
            holder.view.setBackgroundColor(Color.TRANSPARENT);
        }


        holder.delete.setTag(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("삭제");
                builder.setMessage("삭제 하시겠습니까?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int deletePosition = (int) view.getTag();
                        RoutineItems.remove(deletePosition);
                        notifyDataSetChanged();

                        SharedPreferences routineShared_edit = context.getSharedPreferences("routine_shared",MODE_PRIVATE);
                        SharedPreferences.Editor routineShared_editEdt = routineShared_edit.edit();

                        int routine_list = routineShared_edit.getInt("routine_list",0);

                        Gson gson = new Gson();
                        String json = gson.toJson(RoutineItems);
                        routineShared_editEdt.putString("routine"+routine_list, json);
                        routineShared_editEdt.apply();
                        System.out.println(json);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        holder.edit.setTag(position);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                view = LayoutInflater.from(context).inflate(R.layout.activity_edit_routine, null, false);
                builder.setView(view);

                final EditText mod_whereEx = (EditText) view.findViewById(R.id.mod_whereEx);
                final EditText mod_second = (EditText)view.findViewById(R.id.mod_second);
                final EditText mod_set = (EditText)view.findViewById(R.id.mod_set);
                final Button mod_bt = (Button) view.findViewById(R.id.mod_bt);

                Log.d("tag",routineItem.getroutine_WhereEx()+"값");

                mod_whereEx.setText(routineItem.getroutine_WhereEx());
                mod_second.setText(routineItem.getroutine_second());
                mod_set.setText(routineItem.getroutine_set());

                Log.d("tag",mod_whereEx+"값");

                AlertDialog dialog = builder.create();

                mod_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String whereEx = mod_whereEx.getText().toString();
                        String second = mod_second.getText().toString();
                        String set = mod_set.getText().toString();

                        RoutineItem routine = new RoutineItem(whereEx,second,set);
                        RoutineItems.set(position,routine);
                        notifyDataSetChanged();
                        dialog.dismiss();

                        SharedPreferences routineShared_edit = context.getSharedPreferences("routine_shared",MODE_PRIVATE);
                        SharedPreferences.Editor routineShared_editEdt = routineShared_edit.edit();

                        int routine_list = routineShared_edit.getInt("routine_list",0);

                        Gson gson = new Gson();
                        String json = gson.toJson(RoutineItems);
                        routineShared_editEdt.putString("routine"+routine_list, json);
                        routineShared_editEdt.apply();
                        System.out.println(json);


                    }
                });

                dialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return RoutineItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View delete;
        public View edit;
        private TextView routine_whereEx;
        private TextView routine_second;
        private TextView routine_set;
        public View view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            routine_whereEx = itemView.findViewById(R.id.routine_whereEx);
            routine_second = itemView.findViewById(R.id.routine_second);
            routine_set = itemView.findViewById(R.id.routine_set);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0; i<= RoutineItems.size()-1;i++){
                        RoutineItems.get(i).setSel(false);
                    }
                    selposition = getAdapterPosition();
                    RoutineItems.get(getAdapterPosition()).setSel(true);
                    notifyDataSetChanged();
                }
            });

        }


    }

    public int getSelposition() {
        return selposition;
    }
}
