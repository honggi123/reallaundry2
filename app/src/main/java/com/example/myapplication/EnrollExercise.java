package com.example.myapplication;
//package com.example.myrecyclerviewex;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EnrollExercise extends AppCompatActivity {

    EditText editLink;

    RelativeLayout routin_frame;
    RelativeLayout link_frame;

    private Button Link;
    private Button Routine;

    private EditText routineWhereEdt, SecondEdt,SetEdt;
    private Button but_routine;
    private RecyclerView routine_rv;

    private RoutineAdapter routineAdapter;
    private ArrayList<RoutineItem> routineItemArrayList;

    private EditText LinkEdt;
    private RadioButton armcheck,legcheck,musclecheck;
    private Button but_enroll;
    private RecyclerView link_rv;

    private LinkAdapter linkAdapter;
    private ArrayList<LinkItem> linkItemArrayList;

    private ImageView youtube;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState
        );
        setContentView(R.layout.activity_enroll_exercise);

        link_frame = (RelativeLayout) findViewById(R.id.LinkButton);
        routin_frame = (RelativeLayout)findViewById(R.id.RoutineButton);

        Routine =findViewById(R.id.Routine);
        Link = findViewById(R.id.Link);



        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.Routine:
                        routin_frame.setVisibility(View.VISIBLE);
                        Routine.setBackgroundColor(Color.rgb(58,83,221));
                        Log.d("tag", "here");
                        link_frame.setVisibility(View.INVISIBLE);
                        Link.setBackgroundColor(Color.rgb(153,204,255));
                        break;
                    case R.id.Link:
                        link_frame.setVisibility(View.VISIBLE);
                        Link.setBackgroundColor(Color.rgb(58,83,221));
                        Log.d("tag", "here");
                        routin_frame.setVisibility(View.INVISIBLE);
                        Routine.setBackgroundColor(Color.rgb(153,204,255));
                        break;
                }

            }
        };

        Link.setOnClickListener(OnClickListener);
        Routine.setOnClickListener(OnClickListener);

        routine_rv = findViewById(R.id.routine_rv);
        routineWhereEdt = findViewById(R.id.edit_routineWhere);
        SecondEdt = findViewById(R.id.editSecond);
        SetEdt = findViewById(R.id.editSet);

        loadRoutineData();
        buildRoutineRecyclerView();

        but_routine = findViewById(R.id.but_routine);
        but_routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                routineItemArrayList.add(new RoutineItem(routineWhereEdt.getText().toString(), SecondEdt.getText().toString(),SetEdt.getText().toString()));
                routineAdapter.notifyItemInserted(routineItemArrayList.size());
                saveRoutineData();

                routineWhereEdt.setText("");
                SecondEdt.setText("");
                SetEdt.setText("");

            }
        });

        LinkEdt = findViewById(R.id.editLink);
        armcheck = (RadioButton) findViewById(R.id.armCheck);
        legcheck = (RadioButton) findViewById(R.id.legCheck);
        musclecheck = (RadioButton) findViewById(R.id.muscleCheck);

        link_rv = findViewById(R.id.link_rv);
        but_enroll = findViewById(R.id.but_enroll);

        loadLinkData();
        buildLinkRecyclerView();

        but_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String WhereEx = null;

                if (armcheck.isChecked() == true) {
                    WhereEx = armcheck.getText().toString();
                    armcheck.setChecked(false);

                }
                else if (legcheck.isChecked() == true) {
                    WhereEx = legcheck.getText().toString();
                    legcheck.setChecked(false);
                }
                else if (musclecheck.isChecked() == true) {
                    WhereEx = musclecheck.getText().toString();
                    armcheck.setChecked(false);
                }
                int image;
                switch (WhereEx) {
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

                linkItemArrayList.add(new LinkItem(WhereEx,LinkEdt.getText().toString(),image));
                linkAdapter.notifyItemInserted(linkItemArrayList.size());
                saveLinkData();

                LinkEdt.setText("");

            }
        });
    }

    private void buildRoutineRecyclerView() {
        routineAdapter = new RoutineAdapter(routineItemArrayList,EnrollExercise.this);
        LinearLayoutManager routine_manager = new LinearLayoutManager(this);
        routine_rv.setHasFixedSize(true);
        routine_rv.setLayoutManager(routine_manager);
        routine_rv.setAdapter(routineAdapter);
    }


    private void loadRoutineData() {
        SharedPreferences sharedPreferences = getSharedPreferences("routine_shared", MODE_PRIVATE);

        Gson gson = new Gson();
        int routine_list = sharedPreferences.getInt("routine_list",0);
        String json = sharedPreferences.getString("routine"+routine_list, null);

        Type type = new TypeToken<ArrayList<RoutineItem>>() {}.getType();

        routineItemArrayList = gson.fromJson(json, type);
        if (routineItemArrayList == null) {

            routineItemArrayList = new ArrayList<>();
        }
        System.out.println(json);
    }

        private void saveRoutineData() {

            SharedPreferences sharedPreferences = getSharedPreferences("routine_shared", MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            int routine_list = sharedPreferences.getInt("routine_list",0);
            Gson gson = new Gson();
            String json = gson.toJson(routineItemArrayList);

            editor.putString("routine"+routine_list, json);
            editor.apply();
            System.out.println(json);

            Toast.makeText(this, "목록에 추가되었습니다. ", Toast.LENGTH_SHORT).show();
        }


    private void buildLinkRecyclerView() {
        linkAdapter = new LinkAdapter(linkItemArrayList, EnrollExercise.this);
        LinearLayoutManager link_manager = new LinearLayoutManager(this);
        link_rv.setHasFixedSize(true);
        link_rv.setLayoutManager(link_manager);
        link_rv.setAdapter(linkAdapter);
    }

    private void loadLinkData() {

        SharedPreferences sharedPreferences = getSharedPreferences("link_shared", MODE_PRIVATE);
        Gson gson = new Gson();
        int link_list = sharedPreferences.getInt("link_list",0);
        String json = sharedPreferences.getString("link"+link_list, null);

        Type type = new TypeToken<ArrayList<LinkItem>>() {}.getType();

        linkItemArrayList = gson.fromJson(json, type);

        if (linkItemArrayList == null) {
            linkItemArrayList = new ArrayList<>();
        }
        System.out.println(json);
    }

    private void saveLinkData() {

        SharedPreferences sharedPreferences = getSharedPreferences("link_shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int link_list = sharedPreferences.getInt("link_list",0);

        Gson gson = new Gson();
        String json = gson.toJson(linkItemArrayList);
        editor.putString("link"+link_list, json);
        editor.apply();
        System.out.println(json);
        Toast.makeText(this, "목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public void backMain(View view) {
        Intent intent = new Intent(EnrollExercise.this, MainActivity.class);
        startActivity(intent);
    }

}

