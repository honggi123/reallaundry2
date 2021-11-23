package com.example.myapplication.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

public class myprofile_normal extends AppCompatActivity{

        private ActivityResultLauncher<Intent> resultLauncher;

        TextView profileing_normal;
        TextView profileid_normal;
        TextView profiletype_normal;
        TextView profileendnum_normal;
        String startdate;
        User user;
        int num = 0;
        int ingnum=0;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.enroll);

            user = (User) getIntent().getSerializableExtra("user");

            profileing_normal = findViewById(R.id.profileinglaundry_normal);
            profileid_normal = findViewById(R.id.profileid_normal);
            profiletype_normal = findViewById(R.id.profiletype_normal);
            profileendnum_normal = findViewById(R.id.profileendnum_normal);




            profileid_normal.setText(user.getId());
            profiletype_normal.setText("손님");
            GetingLaundrynum();
            GetendLaundrynum();
        }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString("editStartDay",startdate);

        }



    public void GetendLaundrynum(){
        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기
        DatabaseReference laundryref = mDatabase.child("CustomerLaundry").child(user.getFid()).child("endleave");
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
                profileendnum_normal.setText(num+" 벌");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void GetingLaundrynum(){
        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기
        DatabaseReference laundryref = mDatabase.child("CustomerLaundry").child(user.getFid()).child("leave");
        laundryref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap hashMap = (HashMap) dataSnapshot.getValue();
                if(hashMap != null){
                    Log.e("hashmap",hashMap.toString());
                    Iterator<String> keys = hashMap.keySet().iterator();
                    while (keys.hasNext()) {
                        keys.next();
                        Log.e("hasNext", "hasNext");
                        ingnum++;
                    }
                    profileing_normal.setText(ingnum+" 벌");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
