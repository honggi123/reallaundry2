package com.example.myapplication.Boss;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.customer.Laundry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Laundrylist_Boss extends AppCompatActivity {


    RecyclerView recyclerView;
    EndLaundryAdapter laundryClothAdapter;
    ArrayList<Laundry> arr_endlaundry;
    User user;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_laundrylist);
        recyclerView =  findViewById(R.id.rcendlaundry_boss);
        user = (User) getIntent().getSerializableExtra("user");



        // 맡긴 세탁물 리사이클러 뷰
        Log.e("onCreate","0");

        Log.e("onCreate","1");

        // 맡긴 세탁물 리사이클러 뷰

        arr_endlaundry = new ArrayList<>();
        laundryClothAdapter = new EndLaundryAdapter(arr_endlaundry);
         linearLayoutManager =  new LinearLayoutManager(Laundrylist_Boss.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        // 어댑터 추가
        recyclerView.setAdapter(laundryClothAdapter);
        GetLaundry();
        //GetLaundry();

        // 어댑터 추가

        Log.e("onCreate","2");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.e("onStart","onstart");

    }

    public void GetLaundry(){
        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기
        DatabaseReference laundryref = mDatabase.child("BossLaundry").child(user.getShopname()).child("endreceive");
        laundryref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap hashMap = (HashMap) dataSnapshot.getValue();
                Log.e("hashmap",hashMap.toString());
                Iterator<String> keys = hashMap.keySet().iterator();
                while (true) {
                    if(!keys.hasNext()){
                        Log.e("hasNextx", "hasNextx");

                        break;
                    }

                    String key = keys.next();
                    DatabaseReference laundryref2 = mDatabase.child("BossLaundry").child(user.getShopname()).child("endreceive").child(key);
                    laundryref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.e("key", key);
                            Laundry laundry = dataSnapshot.getValue(Laundry.class);
                            laundryClothAdapter.add(laundry);
                            Log.e("arr_endlaundry", arr_endlaundry.size() + "");

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                Log.e("arr_endlaundry",arr_endlaundry.size()+"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
