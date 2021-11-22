package com.example.myapplication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
public class SignupActivity extends AppCompatActivity {

    Button btn_complete;
    TextView pwchktxt;
    EditText edit_pw;
    Boolean pwchk;
    ArrayList<User> arruser;
    EditText edit_idregister;
    CheckBox id_boss;
    TextView pwnotsame;
    EditText edit_pwchk;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);
        btn_complete = findViewById(R.id.btn_complete);
        pwchktxt = findViewById(R.id.txt_pwchk);
        edit_pw = findViewById(R.id.edit_pw);
        pwnotsame = findViewById(R.id.pwnotsame);
        edit_pwchk = findViewById(R.id.pwchk);
        edit_idregister = findViewById(R.id.edit_idregister);
        id_boss = findViewById(R.id.checkBox2);
        loadRoutineData();


        // 비밀번호 확인
        edit_pwchk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edit_pwchk.getText().toString().equals(edit_pw.getText().toString())){
                    pwnotsame.setVisibility(View.VISIBLE);
                }else {
                    pwnotsame.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");

        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘

        //파이어베이스 접근 설정
        // user = firebaseAuth.getCurrentUser();
        firebaseAuth =  FirebaseAuth.getInstance();

        //firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        //파이어베이스 user 로 접글

        //가입버튼 클릭리스너   -->  firebase에 데이터를 저장한다.
        btn_complete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //가입 정보 가져오기
                final String email = edit_idregister.getText().toString().trim();
                String pwd = edit_pw.getText().toString().trim();
                String pwdcheck = edit_pwchk.getText().toString().trim();

                if(pwd.equals(pwdcheck)) {
                    Log.e("Singup", "등록 버튼 :" + email + " , " + pwd);
                    final ProgressDialog mDialog = new ProgressDialog(SignupActivity.this);
                    mDialog.setMessage("가입중입니다...");
                    mDialog.show();

                    //파이어베이스에 신규계정 등록하기
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //가입 성공시
                            if (task.isSuccessful()){
                                mDialog.dismiss();

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String email = user.getEmail();
                                String uid = user.getUid();

                                //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                HashMap<Object,String> hashMap = new HashMap<>();

                                if(id_boss.isChecked()){
                                    hashMap.put("boss","true");
                                    hashMap.put("setshop","false");
                                }else{
                                    hashMap.put("boss","false");
                                }

                                hashMap.put("uid",uid);
                                hashMap.put("email",email);


                                FirebaseDatabase database = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/");
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);

                                //가입이 이루어져을시 가입 화면을 빠져나감.
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(SignupActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignupActivity.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                return;  //해당 메소드 진행을 멈추고 빠져나감.

                            }
                        }
                    });

                    //비밀번호 오류시
                }else{

                    Toast.makeText(SignupActivity.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
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
                Log.e("pw","pwck");
                if(pwPattern.equals(pw)){
                    pwchktxt.setVisibility(View.GONE);
                    pwchk = true;
                    Log.e("pw","pwck1");
                }else{
                    pwchktxt.setVisibility(View.VISIBLE);
                    pwchk = false;
                    Log.e("pw","pwck2");
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void saveuserData() {

        SharedPreferences sharedPreferences = getSharedPreferences("arruser", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();


        Gson gson = new Gson();
        User user = new User();

        user.setId(edit_idregister.getText().toString());
        user.setPw(edit_pw.getText().toString());

        user.setType(edit_pw.getText().toString());
        if(id_boss.isChecked()){
            user.setType("boss");
        }else{
            user.setType("normal");
        }

        arruser.add(user);
        String json = gson.toJson(arruser);

        editor.putString("arruser", json);
        editor.apply();
        System.out.println(json);

    }

    private void loadRoutineData() {
        SharedPreferences sharedPreferences = getSharedPreferences("arruser", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("arruser", null);

        Type type = new TypeToken<ArrayList<User>>() {}.getType();

        arruser = gson.fromJson(json, type);
        if (arruser == null) {
            arruser = new ArrayList<>();
        }

        System.out.println(json);
    }


}
