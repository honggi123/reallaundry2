package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TodayExercise extends AppCompatActivity {

    ArrayList<TodayExItemRoutine> todayExItemRoutines = new ArrayList<>();
    ArrayList<TodayExItemLink> todayExItemLinks = new ArrayList<>();

    private TodayExAdapter todayExAdapter_routine;
    private TodayExAdapterLink todayExAdapter_link;

    RecyclerView Ex_rv_routine;
    RecyclerView Ex_rv_link;

    Button Routine;
    Button Link;
    RelativeLayout routin_frame;
    RelativeLayout link_frame;
    TextView none_routine;
    TextView none_link;

    String announce = "등록된 루틴이 없습니다.\n운동 관리 탭에서 루틴를 등록하세요.";

    RadioButton radioButton;
    Button enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_exercise);

        Routine =findViewById(R.id.Routine);
        Link = findViewById(R.id.Link);

        link_frame = (RelativeLayout) findViewById(R.id.LinkButton);
        routin_frame = (RelativeLayout)findViewById(R.id.RoutineButton);

        none_routine = findViewById(R.id.none_routine);
        none_link = findViewById(R.id.none_link);

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.Routine:
                        routin_frame.setVisibility(View.VISIBLE);
                        Routine.setBackgroundColor(Color.rgb(58,83,221));
                        link_frame.setVisibility(View.INVISIBLE);
                        Link.setBackgroundColor(Color.rgb(153,204,255));
                        break;
                    case R.id.Link:
                        link_frame.setVisibility(View.VISIBLE);
                        Link.setBackgroundColor(Color.rgb(58,83,221));
                        routin_frame.setVisibility(View.INVISIBLE);
                        Routine.setBackgroundColor(Color.rgb(153,204,255));
                        break;
                }

            }
        };

        Link.setOnClickListener(OnClickListener);
        Routine.setOnClickListener(OnClickListener);

        Ex_rv_routine = findViewById(R.id.Ex_rv_routine);
        //loadLinkData();
        jsonReadRoutine();
        todayExAdapter_routine = new TodayExAdapter(todayExItemRoutines, TodayExercise.this);
        LinearLayoutManager link_manager_routine = new LinearLayoutManager(this);
        Ex_rv_routine.setHasFixedSize(true);
        Ex_rv_routine.setLayoutManager(link_manager_routine);
        Ex_rv_routine.setAdapter(todayExAdapter_routine);

        Ex_rv_link = findViewById(R.id.Ex_rv_link);
        //loadLinkData();
        jsonReadLink();
        todayExAdapter_link = new TodayExAdapterLink(todayExItemLinks, TodayExercise.this);
        LinearLayoutManager link_manager_link = new LinearLayoutManager(this);
        Ex_rv_link.setHasFixedSize(true);
        Ex_rv_link.setLayoutManager(link_manager_link);
        Ex_rv_link.setAdapter(todayExAdapter_link);


        enroll = findViewById(R.id.enroll);
        System.out.println(none_routine);

        if(none_routine.getText().toString().equals(announce)){
            enroll.setVisibility(View.INVISIBLE);
        }

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<TodayExItemRoutine> selectList = todayExAdapter_routine.getList() ;

                Intent intent = new Intent(TodayExercise.this, SelectItem.class);
                intent.putExtra("select",selectList);
                startActivity(intent);

            }
        });

    }


    private void jsonReadRoutine() { //둘다됨
        SharedPreferences sharedPreferences = getSharedPreferences("routine_shared", MODE_PRIVATE);
        int link_list = sharedPreferences.getInt("routine_list", 0);
        String json = sharedPreferences.getString("routine" + link_list, null);

        if(json.equals("[]") || json==null){
            none_routine.setText(announce);
        }

        else {


            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String routine_whereEx = jsonObject.getString("routine_WhereEx");
                    int routine_second = Integer.parseInt(jsonObject.getString("routine_second"));
                    int routine_set = Integer.parseInt(jsonObject.getString("routine_set"));

                    todayExItemRoutines.add(new TodayExItemRoutine(routine_whereEx, routine_second, routine_set));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    }

    private void jsonReadLink() { //둘다됨
        SharedPreferences sharedPreferences = getSharedPreferences("link_shared", MODE_PRIVATE);
        int link_list = sharedPreferences.getInt("link_list", 0);
        String json = sharedPreferences.getString("link" + link_list, null);

        if(json.equals("[]") || json==null) {
            none_link.setText("등록된 링크가 없습니다.\n운동 관리 탭에서 링크를 등록하세요.");
        }

        else {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String link_whereEx = jsonObject.getString("list_WhereEx");
                    String link_link = jsonObject.getString("list_Link");
                    int image;
                    switch (link_whereEx) {
                        case "상체": {
                            image = R.drawable.arm;
                            break;
                        }
                        case "하체": {
                            image = R.drawable.leg2;
                            break;
                        }
                        case "복부": {
                            image = R.drawable.sixpack;
                            break;
                        }
                        default:
                            image = R.drawable.ic_launcher_background;
                    }

                    todayExItemLinks.add(new TodayExItemLink(link_whereEx, link_link, image));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
      }


    }
    private void loadLinkData() { //둘 다 됨

        SharedPreferences sharedPreferences = getSharedPreferences("routine_shared", MODE_PRIVATE);
        Gson gson = new Gson();
        int link_list = sharedPreferences.getInt("routine_list", 0);
        String json = sharedPreferences.getString("routine" + link_list, null);

        Type type = new TypeToken<ArrayList<TodayExItemRoutine>>() { }.getType();
        todayExItemRoutines = gson.fromJson(json, type);

            if (todayExItemRoutines == null) {
                todayExItemRoutines = new ArrayList<>();

            }
            System.out.println(json);

    }
}
