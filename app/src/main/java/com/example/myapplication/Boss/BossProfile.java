package com.example.myapplication.Boss;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.customer.Laundry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

public class BossProfile extends AppCompatActivity {

    User user;
    TextView id;
    TextView type;
    TextView shopname;
    TextView txt_endlaundrynum;
    int num=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_boss);
        user = (User) getIntent().getSerializableExtra("user");
        id = findViewById(R.id.profileid);
        type = findViewById(R.id.profiletype);
        shopname = findViewById(R.id.profileshopname);
        txt_endlaundrynum = findViewById(R.id.txt_endlaundrynum);
        GetendLaundrynum();
        Log.e("shopname",user.getShopname());
        id.setText(user.getId());
        if(user.getType().equals("boss")){
            type.setText("세탁소 사장님");
        }else{
            type.setText("일반 회원");
        }

        shopname.setText(user.getShopname());

    }

    public void GetendLaundrynum(){
        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기
        DatabaseReference laundryref = mDatabase.child("BossLaundry").child(user.getShopname()).child("endreceive");
        laundryref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap hashMap = (HashMap) dataSnapshot.getValue();
                Log.e("hashmap",hashMap.toString());
                Iterator<String> keys = hashMap.keySet().iterator();
                while (keys.hasNext()) {
                    keys.next();
                    Log.e("hasNext", "hasNext");
                    num++;
                }
                txt_endlaundrynum.setText(num+" 벌");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
