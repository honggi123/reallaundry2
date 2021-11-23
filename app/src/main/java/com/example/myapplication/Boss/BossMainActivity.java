package com.example.myapplication.Boss;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.customer.Laundry;
import com.example.myapplication.customer.LeaveClothAdapter;
import com.example.myapplication.customer.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BossMainActivity extends AppCompatActivity {

    User user;
    ImageView btn_profile;
    ImageView btn_laundrylist;
    ArrayList<Laundry> arr_leavelaundry;
    RecyclerView recyclerView;
    LaundryClothAdapter_Boss laundryClothAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_main);
        btn_profile = findViewById(R.id.btn_profile);
        btn_laundrylist = findViewById(R.id.btn_laundrylist7);

        user = (User) getIntent().getSerializableExtra("user");
        recyclerView = findViewById(R.id.rclaundry_boss);

        arr_leavelaundry = new ArrayList<>();
        // 세탁소 등록 여부
        if(!Boolean.parseBoolean(user.getHaveshop())){
            Intent intent = new Intent(BossMainActivity.this, Laundryshop_register_boss.class);
            intent.putExtra("user",user);
            Log.e("user",user.getId());
            Log.e("user",user.getHaveshop());
            startActivityForResult(intent,105);
            Toast.makeText(BossMainActivity.this, "세탁소를 먼저 등록해주세요.", Toast.LENGTH_SHORT).show();
        }else{
            GetLaundry();

            // 맡긴 세탁물 리사이클러 뷰
            laundryClothAdapter = new LaundryClothAdapter_Boss(arr_leavelaundry);
            LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(BossMainActivity.this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            // 어댑터 추가
            recyclerView.setAdapter(laundryClothAdapter);




        }

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BossMainActivity.this, BossProfile.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        btn_laundrylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BossMainActivity.this, Laundrylist_Boss.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 활동을 더하고 난 후
        if(requestCode == 105){
            if(resultCode != RESULT_OK){ // 값이 성공적으로 반환되었을때
                user = (User) data.getSerializableExtra("user");
                GetLaundry();
                return;
            }
        }
    }


    public void GetLaundry(){
        DatabaseReference mDatabase;
        arr_leavelaundry = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기
        DatabaseReference laundryref = mDatabase.child("BossLaundry").child(user.getShopname()).child("receive");
        laundryref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap hashMap = (HashMap) dataSnapshot.getValue();
                arr_leavelaundry.clear();
                if(hashMap== null){
                }else{
                    Iterator<String> keys = hashMap.keySet().iterator();


                    while (keys.hasNext()){
                        String key = keys.next();
                        DatabaseReference laundryref2 = mDatabase.child("BossLaundry").child(user.getShopname()).child("receive").child(key);
                        laundryref2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Laundry laundry = dataSnapshot.getValue(Laundry.class);
                                arr_leavelaundry.add(laundry);
                                laundryClothAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

            laundryClothAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
