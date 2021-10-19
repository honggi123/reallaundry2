package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private int Enroll_code= 1;
    public CalendarView calendarView;

    private Dialog dialog;
    TextView whenDate;
    TextView Datekg;
    TextView Exercise;
    Button BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_calendar_dialog);

                whenDate = dialog.findViewById(R.id.whenDate);
                Datekg = dialog.findViewById(R.id.Datekg);
                Exercise = dialog.findViewById(R.id.Exercise);
                String fullDate = Integer.toString(year) + "_" + Integer.toString(month + 1) + "_" + Integer.toString(dayOfMonth); //선택된 날짜

                //whereEx11 = ((SelectItem)SelectItem.context_main).whereEx11;

                String kg = readWeight(fullDate);
                String whereEx = readWhereEx(fullDate);

                whenDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                Datekg.setText("몸무게           "+kg + "kg");
                Exercise.setText("   "+whereEx+" \n   운동 완료");

                if(kg== null || kg.equals("")) {
                    Datekg.setText("몸무게           ____ kg");
                }

                if(whereEx== null) {
                    Exercise.setText("완료한 운동이\n  없습니다.");
                }

                dialog.show();

                BackButton = dialog.findViewById(R.id.BackButton);
                BackButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });

            }
        });

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
        startActivity(intent);
    }

    public void enroll(View view) {
        Intent intent = new Intent(this, Enroll.class);
        startActivity(intent);
    }

    public void record(View view) {
        Intent intent = new Intent(this, record.class);
        startActivity(intent);
    }

    public void calorie(View view) {
        Intent intent = new Intent(this, calorie.class);
        startActivity(intent);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == Enroll_code) {
            if (resultCode == RESULT_OK) {

                EditText editStartDay = (EditText) findViewById(R.id.editStartDay);
                int StartDay = intent.getIntExtra("editStartDay", 0);
                editStartDay.setText(Integer.toString(StartDay));
                // age.setText(String.valueOf(age1));


            }
        }
    }



}