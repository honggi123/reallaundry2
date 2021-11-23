package com.example.myapplication.customer;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Boss.BossMainActivity;
import com.example.myapplication.Boss.BossProfile;
import com.example.myapplication.Boss.Laundrylist_Boss;
import com.example.myapplication.LinkItem;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.RoutineAdapter;
import com.example.myapplication.RoutineItem;
import com.example.myapplication.User;
import com.example.myapplication.calorie;
import com.example.myapplication.record;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {


    private int Enroll_code= 1;
    public CalendarView calendarView;

    private Dialog dialog;
    TextView whenDate;
    TextView Datekg;
    TextView Exercise;
    ImageView btn_laundrylist;
    RecyclerView recyclerView;
    ArrayList<RoutineItem> arr;
    ArrayList<RoutineItem> arrsel;
    ArrayList<LinkItem> arrlinksel;
    ArrayList<Laundry> arr_leavelaundry;
    LeaveClothAdapter leaveClothAdapter;
    LinkItem linkItem;
    User user;
    LinearLayoutManager linearLayoutManager;
    ImageView btn_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (User) getIntent().getSerializableExtra("user");

        loadLinkData();
        recyclerView = findViewById(R.id.rclaundry_cutomer);
        leaveClothAdapter = new LeaveClothAdapter(arr_leavelaundry);
        btn_profile = findViewById(R.id.btn_cprofile);

        Toast.makeText(MainActivity.this,"손님 입니다.",Toast.LENGTH_SHORT).show();

        btn_laundrylist = findViewById(R.id.btn_laundrylist);

        linearLayoutManager =  new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        // 맡긴 세탁물 리사이클러 뷰뷰
        arr_leavelaundry = new ArrayList<>();

        Toast.makeText(MainActivity.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();


        btn_laundrylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnrollExercise.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, myprofile_normal.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        /*
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_calendar_dialog);

                whenDate = dialog.findViewById(R.id.whenDate);
                Exercise = dialog.findViewById(R.id.Exercise);
                String fullDate = Integer.toString(year) + "_" + Integer.toString(month + 1) + "_" + Integer.toString(dayOfMonth); //선택된 날짜

                //whereEx11 = ((SelectItem)SelectItem.context_main).whereEx11;

                String kg = readWeight(fullDate);
                String whereEx = readWhereEx(fullDate);

                whenDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                //Datekg.setText("몸무게           "+kg + "kg");


                //  리사이클러뷰 xml id
                 my_recyclerView = dialog.findViewById(R.id.rc_calenderlaundry);
                // 라사이클러뷰에 넣기
                // 어댑터 객체 생성


                if(kg== null || kg.equals("")) {
                    //Datekg.setText("몸무게           ____ kg");
                }

                if(arr.size()== 0) {
                    Exercise.setText("세탁물을 등록해보세요.");
                }else{

                    Exercise.setText(linkItem.getList_Link());
                    my_adapter = new RoutineAdapter(arrsel,MainActivity.this);

                    LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(MainActivity.this);
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    my_recyclerView.setLayoutManager(linearLayoutManager);
                    // 어댑터 추가
                    my_recyclerView.setAdapter(my_adapter);
                }

                dialog.show();
            }
        });
*/
        loadLinkData();
        loadRoutineData();
        GetLaundry();

        leaveClothAdapter.setArr_laundry(arr_leavelaundry);
        // 어댑터 추가
        recyclerView.setAdapter(leaveClothAdapter);
    }

    String readWeight(String fName) {
        String weight = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fName+"kg");
            byte[] txt = new byte[20];
            inputStream.read(txt);
            inputStream.close();
            weight = (new String(txt)).trim();
        } catch (IOException e) {

        }
        return weight;
    }

    String readWhereEx(String fName) {
        String WhereEx = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fName+"whereEx");
            byte[] txt = new byte[20];
            inputStream.read(txt);
            inputStream.close();
            WhereEx = (new String(txt)).trim();
        } catch (IOException e) {

        }
        return WhereEx;
    }


    public void ExerciseRecord(View view) {
        Intent intent = new Intent(this, EnrollExercise.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void enroll(View view) {
        Intent intent = new Intent(this, myprofile_normal.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void record(View view) {
        Intent intent = new Intent(this, record.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void calorie(View view) {
        Intent intent = new Intent(this, calorie.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == Enroll_code) {
            if (resultCode == RESULT_OK) {
                //EditText editStartDay = (EditText) findViewById(R.id.editshopname);
              //  int StartDay = intent.getIntExtra("editStartDay", 0);
                //editStartDay.setText(Integer.toString(StartDay));
                // age.setText(String.valueOf(age1));
            }
        }
    }


    private void loadRoutineData() {
        SharedPreferences sharedPreferences = getSharedPreferences("routine_shared", MODE_PRIVATE);

        Gson gson = new Gson();
        int routine_list = sharedPreferences.getInt("routine_list",0);
        String json = sharedPreferences.getString("routine"+routine_list, null);

        Type type = new TypeToken<ArrayList<RoutineItem>>() {}.getType();

        arr = gson.fromJson(json, type);
        if (arr == null) {
            arr = new ArrayList<>();
        }else{
            arrsel = new ArrayList<>();
            for(int i= 0;i<= arr.size()-1;i++){
                if(arr.get(i).getsel()){
                    arrsel.add(arr.get(i));
                }
            }
        }

        System.out.println(json);
    }

    private void loadLinkData() {

        SharedPreferences sharedPreferences = getSharedPreferences("link_shared", MODE_PRIVATE);
        Gson gson = new Gson();
        int link_list = sharedPreferences.getInt("link_list",0);
        String json = sharedPreferences.getString("link"+link_list, null);

        Type type = new TypeToken<ArrayList<LinkItem>>() {}.getType();

        arrlinksel = gson.fromJson(json, type);
        if (arrlinksel == null) {
            arrlinksel = new ArrayList<>();
        }else{
            for(int i= 0;i<= arrlinksel.size()-1;i++){
                if(arrlinksel.get(i).getsel()){
                    linkItem = arrlinksel.get(i);
                }
            }
        }

        System.out.println(json);
    }

    public void GetLaundry(){
        DatabaseReference mDatabase;
        arr_leavelaundry = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기
        DatabaseReference laundryref = mDatabase.child("CustomerLaundry").child(user.getFid()).child("leave");
        laundryref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               HashMap hashMap = (HashMap) dataSnapshot.getValue();
                Log.e("onDataChange","onDataChange");
                arr_leavelaundry.clear();
               if(hashMap == null){
               }else{
                   Log.e("hashmap",hashMap.toString());
                   Iterator<String> keys = hashMap.keySet().iterator();
                   while (keys.hasNext()){
                       String key = keys.next();
                       DatabaseReference laundryref2 = mDatabase.child("CustomerLaundry").child(user.getFid()).child("leave").child(key);
                       laundryref2.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                               Laundry laundry = dataSnapshot.getValue(Laundry.class);
                               Log.e("laundry",laundry.type);
                               arr_leavelaundry.add(laundry);
                               Log.e("arr_leavelaundrysize",arr_leavelaundry.size()+"");
                               leaveClothAdapter.notifyDataSetChanged();
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });
                   }
               }
                leaveClothAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}