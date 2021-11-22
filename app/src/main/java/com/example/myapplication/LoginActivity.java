package com.example.myapplication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Boss.BossMainActivity;
import com.example.myapplication.customer.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    TextView btn_signup;
    Button btn_login;
    ArrayList<User> arr_user;
    EditText edit_id;
    EditText edit_pw;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btn_signup = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.btn_login);
        edit_id = findViewById(R.id.edit_id);
        edit_pw = findViewById(R.id.edit_password);

        loadUserData();

        firebaseAuth =  FirebaseAuth.getInstance();
        //버튼 등록하기

        //로그인 버튼이 눌리면
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("로그인중입니다...");
                mDialog.show();

                String email = edit_id.getText().toString().trim();
                String pwd = edit_pw.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email,pwd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    
                                    FirebaseUser fuser = firebaseAuth.getCurrentUser();
                                    User user = new User();
                                    user.setId(fuser.getEmail());

                                    Log.e("email",fuser.getEmail().toString());

                                    DatabaseReference mDatabase ;
                                    TextView email_info;

                                    FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser(); // 로그인한 유저의 정보 가져오기
                                    String uid = user2 != null ? user2.getUid() : null; // 로그인한 유저의 고유 uid 가져오기
                                    Log.e("uid",uid);
                                    user.setFid(uid);

                                    mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기
                                    DatabaseReference bossornot = mDatabase.child("Users").child(uid).child("boss");    // 이메일
                                    bossornot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Log.e("bossornot",dataSnapshot.getValue(String.class));
                                            if(Boolean.parseBoolean(dataSnapshot.getValue(String.class))){
                                                // 세탁소 사장
                                                DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기
                                                DatabaseReference setshop = mDatabase.child("Users").child(uid).child("setshop");
                                                setshop.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        // 세탁소 등록 했는지 여부
                                                        Intent intent = new Intent(LoginActivity.this, BossMainActivity.class);

                                                       user.setType("boss");
                                                        if(Boolean.parseBoolean(dataSnapshot.getValue(String.class))){
                                                            user.setHaveshop("true");
                                                            mDatabase.child("Users").child(uid).child("shopname").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    user.setShopname(dataSnapshot.getValue(String.class));
                                                                    Log.e("shopname",user.getShopname());
                                                                    intent.putExtra("user",user);
                                                                    mDialog.dismiss();
                                                                    startActivity(intent);
                                                                    finish();
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });
                                                        }else {
                                                            user.setHaveshop("false");
                                                            intent.putExtra("user",user);
                                                            mDialog.dismiss();
                                                            startActivity(intent);
                                                            finish();
                                                        }


                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }else{
                                                // 세탁소 사장이 아니면
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                user.setType("normal");
                                                intent.putExtra("user",user);
                                                mDialog.dismiss();
                                                startActivity(intent);
                                                finish();
                                            }
                                            
                                            Toast.makeText(LoginActivity.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                //  user.setType(fuser.get);


                                }else{
                                    mDialog.dismiss();
                                    Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호를 확인하세요",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        // 가입 버튼 눌리면
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
       /* btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;i<=arr_user.size()-1;i++){
                    Log.e("user",arr_user.get(i).id);
                    if(arr_user.get(i).getId().equals(edit_id.getText().toString())){
                        Log.e("user",arr_user.get(i).getPw());
                        if(edit_pw.getText().toString().equals(arr_user.get(i).getPw())){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user",arr_user.get(i));

                            Toast.makeText(LoginActivity.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                            startActivity(intent);
                        }
                    }

                }
            }
        });

        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUserData();
    }

    private void loadUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("arruser", MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString("arruser", null);

        Type type = new TypeToken<ArrayList<User>>() {}.getType();

        arr_user = gson.fromJson(json, type);
        if (arr_user == null) {
            arr_user = new ArrayList<>();
        }

        System.out.println(json);
    }





}