package com.example.myapplication.Boss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Laundryshop_register_boss extends AppCompatActivity {

    Button register;
    User user;
    EditText shopname;
    EditText shopnum;


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_register);

        user = (User) getIntent().getSerializableExtra("user");

        shopname = findViewById(R.id.editshopname);
        shopnum = findViewById(R.id.editshopnum);

        mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference();

        register = findViewById(R.id.EnrollButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser();
            }
        });


    }


    private void writeNewUser() {

        LaundryShop laundryShop = new LaundryShop();
        laundryShop.setName(shopname.getText().toString());
        laundryShop.setNum(shopnum.getText().toString());
        laundryShop.setUserid(user.getId());
        
        mDatabase.child("LaundryShop").child(laundryShop.name).setValue(laundryShop)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(Laundryshop_register_boss.this, "등록 완료했습니다.", Toast.LENGTH_SHORT).show();

                        mDatabase.child("Users").child(user.getFid()).child("setshop").setValue("true");
                        mDatabase.child("Users").child(user.getFid()).child("shopname").setValue(laundryShop.getName());
                        user.setHaveshop("true");
                        user.setShopname(laundryShop.getName());

                        Intent intentR = new Intent();
                        intentR.putExtra("user",user);
                        setResult(RESULT_OK,intentR); //결과를 저장

                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(Laundryshop_register_boss.this, "등록 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
