package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Enroll extends AppCompatActivity {

    private ActivityResultLauncher<Intent> resultLauncher;

    EditText editStartDay;
    EditText editHeight;
    EditText editWeight;
    EditText editAimWeight;

    String startdate;
    String height;
    String weight;
    String aimweight;

    Button EnrollButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enroll);

        editStartDay = findViewById(R.id.editStartDay);
        editHeight = findViewById(R.id.editHeight);
        editWeight = findViewById(R.id.editWeight);
        editAimWeight = findViewById(R.id.editAimWeight);

        SharedPreferences userData = getSharedPreferences("userData",MODE_PRIVATE);
        SharedPreferences.Editor editor = userData.edit();

        //저장해둔 값 불러오기
        startdate = userData.getString("startdate","");
        height = userData.getString("height","");
        weight = userData.getString("weight","");
        aimweight = userData.getString("aimweight","");

        editStartDay.setText(startdate);
        editHeight.setText(height);
        editWeight.setText(weight);
        editAimWeight.setText(aimweight);

        EnrollButton = findViewById(R.id.EnrollButton);
        EnrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             startdate = editStartDay.getText().toString();
             height = editHeight.getText().toString();
             weight = editWeight.getText().toString();
             aimweight = editAimWeight.getText().toString();

              editor.putString("startdate",startdate);
              editor.putString("height",height);
              editor.putString("weight",weight);
              editor.putString("aimweight",aimweight);
              editor.commit();

          Toast.makeText(Enroll.this,"등록이 완료되었습니다.",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("editStartDay",startdate);

    }

    public void backMain(View view) {
        Intent intent = new Intent(Enroll.this, MainActivity.class);
        startActivity(intent);

    }

        }





