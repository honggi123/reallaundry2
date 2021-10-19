package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class TodayEyeBody extends AppCompatActivity {

    ArrayList todayExItemRoutines = new ArrayList();
    long mNow;
    Date mDate;

    public static Context context_main;

    private ImageView InRemove;
    private ImageView EyeRemove;
    private ImageView EyeGallery;
    private ImageView InGallery;

    EditText edittTodayKg;
    EditText editMemoEyebody;

    private String todaykg;
    private String MemoEyebody;

    Button EnrollEyeBody;

    ImageView plusEyebody;
    ImageView plusInbody;
    Intent intent;

    String fullDate;
    String InfullDate="2020";
    String Inweight;

    //FileOutputStream outFsKg;

    private ActivityResultLauncher<Intent> imageEyelaucher;
    private ActivityResultLauncher<Intent> imageInlaucher;


    private DatePicker whenDate;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy년 M월 d일 눈바디");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye_body);

        context_main = this;
        edittTodayKg = findViewById(R.id.edittTodayKg);
        editMemoEyebody = findViewById(R.id.editMemoEyebody);
        plusEyebody = findViewById(R.id.plusEyebody);
        plusInbody = findViewById(R.id.plusInbody);

        InRemove = findViewById(R.id.InRemove);
        EyeRemove = findViewById(R.id.EyeRemove);
        EyeGallery = findViewById(R.id.EyeGallery);
        InGallery = findViewById(R.id.InGallery);

        whenDate = findViewById(R.id.datePicker);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

//        SharedPreferences TodayEyeBody = getSharedPreferences("TodayEyeBody", MODE_PRIVATE);
//        SharedPreferences.Editor TodayEyeBodyeditor = TodayEyeBody.edit();

        whenDate.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fullDate = Integer.toString(year) + "_" + Integer.toString(monthOfYear + 1) + "_" + Integer.toString(dayOfMonth);  // month는 0부터 시작되므로 +1

                String kg = readWeight(fullDate); // 현재 날짜 읽어옴
                String memo = readMemo(fullDate);
                String eyeimage = readEyeImage(fullDate);
                String inimage = readInImage(fullDate);

                Bitmap Eyebitmap = StringToBitmap(eyeimage);
                Bitmap Inbitmap = StringToBitmap(inimage);
                edittTodayKg.setText(kg);
                editMemoEyebody.setText(memo);

                if (Eyebitmap == null) {
                    Drawable drawable = getResources().getDrawable(R.drawable.plus);
                    plusEyebody.setImageDrawable(drawable);
                } else {
                    plusEyebody.setImageBitmap(Eyebitmap);
                }

                if (Inbitmap == null) {
                    Drawable drawable = getResources().getDrawable(R.drawable.plus);
                    plusInbody.setImageDrawable(drawable);
                } else {
                    plusInbody.setImageBitmap(Inbitmap);
                }

            }
            });

//        //저장해둔 값 불러오기

//        MemoEyebody = TodayEyeBody.getString("MemoEyebody", "");
//        plusEye = TodayEyeBody.getString("plusEye", "");
//        plusIn = TodayEyeBody.getString("plusIn", "");

       // Bitmap Eyebitmap = StringToBitmap(plusEye);
       // Bitmap Inbitmap = StringToBitmap(plusIn);

        //edittTodayKg.setText(todaykg);
       // editMemoEyebody.setText(MemoEyebody);

//        if (Eyebitmap == null) {
//            Drawable drawable = getResources().getDrawable(R.drawable.plus);
//            plusEyebody.setImageDrawable(drawable);
//        } else {
//            plusEyebody.setImageBitmap(Eyebitmap);
//        }

//        if (Inbitmap == null) {
//            Drawable drawable = getResources().getDrawable(R.drawable.plus);
//            plusInbody.setImageDrawable(drawable);
//        } else {
//            plusInbody.setImageBitmap(Inbitmap);
//        }

        EyeGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.putExtra("CallType", 0);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                imageEyelaucher.launch(intent);
            }
        });

        InGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.putExtra("CallType", 1);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                imageInlaucher.launch(intent);
            }
        });

        imageEyelaucher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == RESULT_OK) {
                intent = result.getData();
                Uri uri = intent.getData();
                plusEyebody.setImageURI(uri);

            }
        });

        imageInlaucher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == RESULT_OK) {
                intent = result.getData();
                Uri uri = intent.getData();
                plusInbody.setImageURI(uri);

            }
        });


        EyeRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                plusEyebody.setImageBitmap(null);
                Drawable drawable = getResources().getDrawable(R.drawable.plus);
                plusEyebody.setImageDrawable(drawable);
               // TodayEyeBodyeditor.remove("plusEye");

            }
        });

        InRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //plusInbody.setImageBitmap(null);
                Drawable drawable = getResources().getDrawable(R.drawable.plus);
                plusInbody.setImageDrawable(drawable);
                //TodayEyeBodyeditor.remove("plusIn");
            }
        });

        EnrollEyeBody = findViewById(R.id.EnrollEyeBody);
        EnrollEyeBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    FileOutputStream outFsKg = openFileOutput(fullDate+"kg", Context.MODE_PRIVATE);
                    FileOutputStream outFsMemo = openFileOutput(fullDate+"memo", Context.MODE_PRIVATE);
                    FileOutputStream outFsEye = openFileOutput(fullDate+"eye", Context.MODE_PRIVATE);
                    FileOutputStream outFsIn = openFileOutput(fullDate+"in", Context.MODE_PRIVATE);

                    todaykg = edittTodayKg.getText().toString();
                    MemoEyebody = editMemoEyebody.getText().toString();

                    BitmapDrawable Eyebody = (BitmapDrawable) plusEyebody.getDrawable(); //eyebody사진 string으로 바꾸기
                    Bitmap eye = Eyebody.getBitmap();
                    plusEyebody.setImageBitmap(eye);
                    String Eyeimage = BitmapToString(eye);

                    BitmapDrawable Inbody = (BitmapDrawable) plusInbody.getDrawable(); //inbody사진 string으로 바꾸기
                    Bitmap In = Inbody.getBitmap();
                    plusInbody.setImageBitmap(In);
                    String Inimage = BitmapToString(In);

                    outFsKg.write(todaykg.getBytes());
                    outFsMemo.write(MemoEyebody.getBytes());
                    outFsEye.write(Eyeimage.getBytes());
                    outFsIn.write(Inimage.getBytes());

                    outFsKg.close();
                    outFsMemo.close();
                    outFsEye.close();
                    outFsIn.close();
                    Toast.makeText(getApplicationContext(),fullDate+" 이 저장됨", Toast.LENGTH_SHORT).show();

                    InfullDate = fullDate;
                    Inweight = todaykg;

                } catch (IOException e) {

                }

//                BitmapDrawable Eyebody = (BitmapDrawable) plusEyebody.getDrawable();
//                Bitmap eye = Eyebody.getBitmap();
//                plusEyebody.setImageBitmap(eye);
//                String Eyeimage = BitmapToString(eye);
                //TodayEyeBodyeditor.putString("plusEye", Eyeimage);
//                BitmapDrawable Inbody = (BitmapDrawable) plusInbody.getDrawable();
//                Bitmap In = Inbody.getBitmap();
//                plusInbody.setImageBitmap(In);
//                String Inimage = BitmapToString(In);
                //TodayEyeBodyeditor.putString("plusIn", Inimage);
//                TodayEyeBodyeditor.putString("todaykg"+fullDate, todaykg);
                //TodayEyeBodyeditor.putString("MemoEyebody", MemoEyebody);
                //TodayEyeBodyeditor.commit();

                Toast.makeText(TodayEyeBody.this, "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
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

    String readMemo(String fName) {
        String memo = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fName+"memo");
            byte[] txt = new byte[50];
            inputStream.read(txt);
            inputStream.close();
            memo = (new String(txt)).trim();
        } catch (IOException e) {

        }
        return memo;
    }

    String readEyeImage(String fName) {
        String eyeimage = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fName+"eye");
            byte[] txt = new byte[100000000];
            inputStream.read(txt);
            inputStream.close();
            eyeimage = (new String(txt)).trim();
        } catch (IOException e) {

        }
        return eyeimage;
    }

    String readInImage(String fName) {
        String inimage = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fName+"in");
            byte[] txt = new byte[100000000];
            inputStream.read(txt);
            inputStream.close();
            inimage = (new String(txt)).trim();
        } catch (IOException e) {

        }
        return inimage;
    }

    public Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodedByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte, 0, encodedByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String temp = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return temp;
    }


    public Bitmap ByteToBitmap(byte[] byteArray) {
       Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

       return bitmap;
    }

    public byte[] BitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return byteArray;
    }


}