package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.customer.MainActivity;

public class record extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recorddata);
    }



    public void TodayEyeBody(View view) {
        Intent intent = new Intent(this,TodayEyeBody.class); //바꾸기
        startActivity(intent);
    }

    public void TodayDiet(View view) {
        Intent intent = new Intent(this,TodayDiet.class); //바꾸기
        startActivity(intent);
    }


    public void TodayExercise(View view) {
        Intent intent = new Intent(this,TodayExercise.class); //바꾸기
        startActivity(intent);
    }

    public void backMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
