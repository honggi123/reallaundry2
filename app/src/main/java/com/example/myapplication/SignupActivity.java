package com.example.myapplication;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;
public class SignupActivity extends AppCompatActivity {

    Button btn_complete;
    TextView pwchktxt;
    EditText edit_pw;
    Boolean pwchk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);
        btn_complete = findViewById(R.id.btn_complete);
        pwchktxt = findViewById(R.id.txt_pwchk);
        edit_pw = findViewById(R.id.edit_pw);

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // 비밀 번호 조합 확인
        edit_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = edit_pw.getText().toString();
                //대소문자 구분 숫자 특수문자  조합 9 ~ 12 자리
                String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,}$";

                if(Pattern.matches(pwPattern,pw)){
                    pwchktxt.setVisibility(View.GONE);
                    pwchk = true;
                }else{
                    pwchktxt.setVisibility(View.VISIBLE);
                    pwchk = false;
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


}
