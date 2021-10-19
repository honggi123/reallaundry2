package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class TodayExAdapterLink extends RecyclerView.Adapter<TodayExAdapterLink.ViewHolder> {


    ArrayList<TodayExItemLink> todayExAdapterLinks;
    Context context;

    public TodayExAdapterLink(ArrayList<TodayExItemLink> todayExAdapterLinks, Context context) {

        this.todayExAdapterLinks = todayExAdapterLinks;
        this.context = context;
    }


    @NotNull
    @Override
    public TodayExAdapterLink.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_today_ex_item_link, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NotNull TodayExAdapterLink.ViewHolder holder, int position) {
        TodayExItemLink todayExItemLink = todayExAdapterLinks.get(position);
        System.out.println(todayExItemLink.getList_WhereEx()+"링크");

        holder.list_WhereEx.setText(todayExItemLink.getList_WhereEx());
        holder.list_Link.setText(todayExItemLink.getList_Link());
        holder.list_image.setImageResource(todayExItemLink.getList_image());

        holder.list_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = holder.list_Link.getText().toString();
                String vidioId = text.substring(text.length()-11, text.length());
                System.out.println(vidioId);


            }
        });

//        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
//
//            @Override
//            public String transformUrl(Matcher match, String url) {
//                return "";
//
//            }
//        };
//        Pattern pattern = Pattern.compile(todayExItemLink.getList_Link());
//        Linkify.addLinks(holder.list_Link,pattern,todayExItemLink.getList_Link(),null,transformFilter);
    }

    @Override
    public int getItemCount() {
        return todayExAdapterLinks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView list_WhereEx, list_Link;
        private ImageView list_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_WhereEx = itemView.findViewById(R.id.list_whereEx);
            list_Link = itemView.findViewById(R.id.list_Link);
            list_image = itemView.findViewById(R.id.imageView);

        }

    }
}




