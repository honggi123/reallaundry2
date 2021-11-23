package com.example.myapplication.customer;
//package com.example.myrecyclerviewex;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.LinkAdapter;
import com.example.myapplication.LinkItem;
import com.example.myapplication.R;
import com.example.myapplication.RoutineAdapter;
import com.example.myapplication.RoutineItem;
import com.example.myapplication.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class EnrollExercise extends AppCompatActivity {

    EditText editLink;

    RelativeLayout routin_frame;
    RelativeLayout link_frame;

    private Button Link;
    private Button Routine;
    private Button but_enroll2;
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
    User user;
    
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState
        );
        setContentView(R.layout.activity_enroll_exercise);
        user = (User) getIntent().getSerializableExtra("user");
        
        link_frame = (RelativeLayout) findViewById(R.id.LinkButton);
        routin_frame = (RelativeLayout)findViewById(R.id.RoutineButton);

        Routine =findViewById(R.id.Routine);
        Link = findViewById(R.id.Link);
        but_enroll2 = findViewById(R.id.but_enroll2);

        mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference();

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

        routineItemArrayList = new ArrayList<>();

        buildRoutineRecyclerView();

        but_routine = findViewById(R.id.but_routine);
        but_routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                routineItemArrayList.add(new RoutineItem(routineWhereEdt.getText().toString(), SecondEdt.getText().toString(),SetEdt.getText().toString()));
                routineAdapter.notifyItemInserted(routineItemArrayList.size());

                routineWhereEdt.setText("");
                SecondEdt.setText("");
                SetEdt.setText("");

            }
        });

        but_enroll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLinkData();
                writeNewlaundrys();
            }
        });

        LinkEdt = findViewById(R.id.editLink);
        armcheck = (RadioButton) findViewById(R.id.armCheck);
        legcheck = (RadioButton) findViewById(R.id.legCheck);
        musclecheck = (RadioButton) findViewById(R.id.muscleCheck);

        link_rv = findViewById(R.id.rcendlaundry_boss);
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

                LinkEdt.setText("");

            }
        });
    }

    @SuppressLint("LongLogTag")
    private void writeNewlaundrys() {
        String shopname = null;
        ArrayList<Laundry> laundryArrayList = new ArrayList<>();

        Log.e("routineItemArrayList",routineItemArrayList.size()+"");
        for (int i = 0; i <= linkItemArrayList.size()-1; i++){
                    if(linkItemArrayList.get(i).getsel()){
                        shopname = linkItemArrayList.get(i).getList_Link();
                    }
        }

        for (int i = 0; i <= routineItemArrayList.size()-1; i++){
                    if(routineItemArrayList.get(i).getsel()){
                        Log.e("routineItemArrayListgetsel",i+"");

                        Laundry laundry = new Laundry();
                        laundry.setWantdate(routineItemArrayList.get(i).getroutine_second());
                        laundry.setType(routineItemArrayList.get(i).getroutine_WhereEx());
                        laundry.setNum(Integer.parseInt(routineItemArrayList.get(i).getroutine_set()));
                        laundry.setFromid(user.getId());
                        laundry.setToshop(shopname);
                        laundry.setStatus(0);
                        laundry.setFfromid(user.getFid());
                        laundryArrayList.add(laundry);

                    }
        }

        for(int i=0;i<=laundryArrayList.size()-1;i++){
            Log.e("laundryArrayList",laundryArrayList.get(i).type);
            String ran = random(6);
            laundryArrayList.get(i).setLid(ran);
            mDatabase.child("BossLaundry").child(shopname).child("receive").child(ran).setValue(laundryArrayList.get(i))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Write was successful!
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                        }
                    });

            mDatabase.child("CustomerLaundry").child(user.getFid()).child("leave").child(ran).setValue(laundryArrayList.get(i))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Write was successful!
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                        }
                    });
        }

        Log.e("user.getFid()",user.getFid());

        for(int i=0;i<=laundryArrayList.size()-1;i++){
            Log.e("laundryArrayList",laundryArrayList.get(i).type);

        }

        Toast.makeText(this, "목록에 추가되었습니다. ", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void buildRoutineRecyclerView() {
        routineAdapter = new RoutineAdapter(routineItemArrayList,EnrollExercise.this);
        LinearLayoutManager routine_manager = new LinearLayoutManager(this);
        routine_rv.setHasFixedSize(true);
        routine_rv.setLayoutManager(routine_manager);
        routine_rv.setAdapter(routineAdapter);
    }

    public static String random(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
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

