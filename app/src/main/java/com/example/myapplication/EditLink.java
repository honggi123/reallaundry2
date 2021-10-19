package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class EditLink extends AppCompatActivity {

    EditText editLink;
    EditText editLink2;

    String checkarm;
    String checkleg;
    String checkmuscle;
    String Link1;
    String Link2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_link);

    }


    public void EnrollLink (View view) {
        Intent intent = new Intent(this, EnrollExercise.class);

        editLink = (EditText) findViewById(R.id.editLink);
        //editLink2 = (EditText) findViewById(R.id.editLink2);

        Link1 = editLink.getText().toString();
        Link2 = editLink2.getText().toString();

        intent.putExtra("editLink", Link1);
        intent.putExtra("editLink2", Link2);

        final RadioButton arm = (RadioButton) findViewById(R.id.armCheck);
        final RadioButton leg = (RadioButton) findViewById(R.id.legCheck);
        final RadioButton muscle = (RadioButton) findViewById(R.id.muscleCheck);


        if (arm.isChecked() == true) {
            checkarm = arm.getText().toString();
            intent.putExtra("whereEx",checkarm);}
            if (leg.isChecked() == true) {
                checkleg = leg.getText().toString();
                intent.putExtra("whereEx",checkleg);
            } else if (muscle.isChecked() == true) {
                checkmuscle = muscle.getText().toString();
                intent.putExtra("whereEx",checkmuscle);

            }

            startActivity(intent);

        }

    }
