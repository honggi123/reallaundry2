package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.style.BackgroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodayDiet extends AppCompatActivity {

    private TextView whenDate;
    private TextView morning_whenTime;
    private TextView noon_whenTime;
    private TextView night_whenTime;
    private TextView snack_whenTime;

    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy년 M월 d일 식단");
    private SimpleDateFormat mFormat2 = new SimpleDateFormat("aa hh시 mm분");

    private Button morning;
    private Button noon;
    private Button night;
    private Button snack;

    private ConstraintLayout morning_frame;
    private ConstraintLayout noon_frame;
    private ConstraintLayout night_frame;
    private ConstraintLayout snack_frame;

    private EditText morning_editMenu1;
    private EditText morning_editMenu2;
    private EditText morning_editMenu3;
    private EditText morning_editMenoDiet;

    private ImageView morning_Gallery;
    private ImageView morning_Remove;

    private String morning_Menu1;
    private String morning_Menu2;
    private String morning_Menu3;
    private String morning_MenoDiet;
    Button morning_EnrollDiet;
    private String morning_image;

    private ImageView morning_plusDiet;
    private Intent intent;
    private ActivityResultLauncher<Intent> imageDietlaucher;

    private EditText noon_editMenu1; //점심
    private EditText noon_editMenu2;
    private EditText noon_editMenu3;
    private EditText noon_editMenoDiet;

    private ImageView noon_Gallery;
    private ImageView noon_Remove;

    private String noon_Menu1;
    private String noon_Menu2;
    private String noon_Menu3;
    private String noon_MenoDiet;
    Button noon_EnrollDiet;
    private String noon_image;

    private ImageView noon_plusDiet;
    private DatePicker datePicker;
    String fullDate;

    //private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_diet);

        //whenDate = findViewById(R.id.whenDate);

        morning_whenTime = findViewById(R.id.morning_timenow);
        noon_whenTime = findViewById(R.id.noon_timenow);
        night_whenTime = findViewById(R.id.night_timenow);
        snack_whenTime = findViewById(R.id.snack_timenow);


        Date date = new Date();
        String time = mFormat.format(date);
        //whenDate.setText(time);

        String now = mFormat2.format(date);
        morning_whenTime.setText(now);
        noon_whenTime.setText(now);
        night_whenTime.setText(now);
        snack_whenTime.setText(now);

        morning_frame = (ConstraintLayout) findViewById(R.id.morning_frame);
        noon_frame = (ConstraintLayout)findViewById(R.id.noon_frame);
        night_frame = (ConstraintLayout) findViewById(R.id.night_frame);
        snack_frame = (ConstraintLayout) findViewById(R.id.snack_frame);


        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.morning:
                        morning_frame.setVisibility(View.VISIBLE);
                        morning.setBackgroundColor(Color.DKGRAY);
                        noon_frame.setVisibility(View.INVISIBLE);
                        night_frame.setVisibility(View.INVISIBLE);
                        snack_frame.setVisibility(View.INVISIBLE);
                        snack.setBackgroundColor(Color.LTGRAY);
                        noon.setBackgroundColor(Color.LTGRAY);
                        night.setBackgroundColor(Color.LTGRAY);
                        break;
                    case R.id.noon:
                        noon_frame.setVisibility(View.VISIBLE);
                        noon.setBackgroundColor(Color.DKGRAY);
                        morning_frame.setVisibility(View.INVISIBLE);
                        night_frame.setVisibility(View.INVISIBLE);
                        snack_frame.setVisibility(View.INVISIBLE);
                        snack.setBackgroundColor(Color.LTGRAY);
                        morning.setBackgroundColor(Color.LTGRAY);
                        night.setBackgroundColor(Color.LTGRAY);
                        break;

                    case R.id.Night:
                        night_frame.setVisibility(View.VISIBLE);
                        night.setBackgroundColor(Color.DKGRAY);
                        morning_frame.setVisibility(View.INVISIBLE);
                        noon_frame.setVisibility(View.INVISIBLE);
                        snack_frame.setVisibility(View.INVISIBLE);
                        snack.setBackgroundColor(Color.LTGRAY);
                        morning.setBackgroundColor(Color.LTGRAY);
                        noon.setBackgroundColor(Color.LTGRAY);
                        break;

                    case R.id.snack:
                        snack_frame.setVisibility(View.VISIBLE);
                        snack.setBackgroundColor(Color.DKGRAY);
                        morning_frame.setVisibility(View.INVISIBLE);
                        noon_frame.setVisibility(View.INVISIBLE);
                        night_frame.setVisibility(View.INVISIBLE);
                        noon.setBackgroundColor(Color.LTGRAY);
                        morning.setBackgroundColor(Color.LTGRAY);
                        night.setBackgroundColor(Color.LTGRAY);
                        break;
                }

            }
        };

        morning = findViewById(R.id.morning);
        morning.setOnClickListener(OnClickListener);
        noon =findViewById(R.id.noon);
        noon.setOnClickListener(OnClickListener);
        night = findViewById(R.id.Night);
        night.setOnClickListener(OnClickListener);
        snack =findViewById(R.id.snack);
        snack.setOnClickListener(OnClickListener);

        morning_editMenu1 = findViewById(R.id.morning_editMenu1);
        morning_editMenu2 = findViewById(R.id.morning_editMenu2);
        morning_editMenu3 = findViewById(R.id.morning_editMenu3);
        morning_editMenoDiet =findViewById(R.id.morning_editMenoDiet);

        morning_Gallery = findViewById(R.id.morning_Gallery);
        morning_Remove = findViewById(R.id.morning_Remove);
        morning_plusDiet = findViewById(R.id.morning_plusDiet);

        noon_editMenu1 = findViewById(R.id.noon_editMenu1);
        noon_editMenu2 = findViewById(R.id.noon_editMenu2);
        noon_editMenu3 = findViewById(R.id.noon_editMenu3);
        noon_editMenoDiet =findViewById(R.id.noon_editMenoDiet);

        noon_Gallery = findViewById(R.id.noon_Gallery);
        noon_Remove = findViewById(R.id.noon_Remove);
        noon_plusDiet = findViewById(R.id.noon_plusDiet);

        SharedPreferences TodayDiet = getSharedPreferences("TodayDiet",MODE_PRIVATE);
        SharedPreferences.Editor TodayDieteditor = TodayDiet.edit();

        datePicker = findViewById(R.id.datePicker);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        datePicker.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fullDate = Integer.toString(year) + "_" + Integer.toString(monthOfYear + 1) + "_" + Integer.toString(dayOfMonth);  // month는 0부터 시작되므로 +1
                String menu1 = readMemu1(fullDate); // 현재 날짜의 파일을 읽어오는 메소드
                String menu2 = readMemu2(fullDate);
                String menu3 = readMemu3(fullDate);
                String memo = readMemo(fullDate);

                String eatimage = readEatImage(fullDate);
                Bitmap Eatbitmap = StringToBitmap(eatimage);

                morning_editMenu1.setText(menu1);
                morning_editMenu2.setText(menu2);
                morning_editMenu3.setText(menu3);
                morning_editMenoDiet.setText(memo);

                if (Eatbitmap == null) {
                    Drawable drawable = getResources().getDrawable(R.drawable.plus);
                    morning_plusDiet.setImageDrawable(drawable);
                } else {
                    morning_plusDiet.setImageBitmap(Eatbitmap);
                }


            }
        });


//        //저장해둔 값 불러오기 아침
//        morning_Menu1 = TodayDiet.getString("morning_Menu1","");
//        morning_Menu2 = TodayDiet.getString("morning_Menu2","");
//        morning_Menu3 = TodayDiet.getString("morning_Menu3","");
//        morning_MenoDiet = TodayDiet.getString("morning_MenoDiet","");
//        morning_image = TodayDiet.getString("morning_Dietimage",null);
//
//        Bitmap MorDietbitmap = StringToBitmap(morning_image);
//
//        if(MorDietbitmap==null){
//            Drawable drawable = getResources().getDrawable(R.drawable.plus);
//            morning_plusDiet.setImageDrawable(drawable);
//        }else {
//            morning_plusDiet.setImageBitmap(MorDietbitmap);
//        }
//
//        morning_editMenu1.setText(morning_Menu1);
//        morning_editMenu2.setText(morning_Menu2);
//        morning_editMenu3.setText(morning_Menu3);
//        morning_editMenoDiet.setText(morning_MenoDiet);


        morning_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.putExtra("CallType", 0);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                imageDietlaucher.launch(intent);
            }
        });


        imageDietlaucher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == RESULT_OK) {
                intent = result.getData();
                Uri uri = intent.getData();
                morning_plusDiet.setImageURI(uri);

            }
        });

        morning_Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //morning_plusDiet.setImageBitmap(null);
                Drawable drawable = getResources().getDrawable(R.drawable.plus);
                morning_plusDiet.setImageDrawable(drawable);
               // TodayDieteditor.remove("morning_Dietimage");

            }
        });

        morning_EnrollDiet = findViewById(R.id.morning_EnrollDiet);
        morning_EnrollDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(TodayDiet.this,EnrollSuccess.class);
//                startActivity(intent);
                try{
                FileOutputStream outFsTime = openFileOutput(fullDate+"time", Context.MODE_PRIVATE);
                FileOutputStream outFsMenu1 = openFileOutput(fullDate+"menu1", Context.MODE_PRIVATE);
                FileOutputStream outFsMenu2 = openFileOutput(fullDate+"menu2", Context.MODE_PRIVATE);
                FileOutputStream outFsMenu3 = openFileOutput(fullDate+"menu3", Context.MODE_PRIVATE);
                FileOutputStream outFsEat = openFileOutput(fullDate+"eat", Context.MODE_PRIVATE);
                FileOutputStream outFsMemo = openFileOutput(fullDate+"memo1", Context.MODE_PRIVATE);

                morning_Menu1 = morning_editMenu1.getText().toString();
                morning_Menu2 = morning_editMenu2.getText().toString();
                morning_Menu3 = morning_editMenu3.getText().toString();
                morning_MenoDiet = morning_editMenoDiet.getText().toString();

                BitmapDrawable Diet = (BitmapDrawable)morning_plusDiet.getDrawable();
                Bitmap DietBit = Diet.getBitmap();
                morning_plusDiet.setImageBitmap(DietBit);
                String Eatimage = BitmapToString(DietBit);

                outFsMenu1.write(morning_Menu1.getBytes());
                outFsMenu2.write(morning_Menu2.getBytes());
                outFsMenu3.write(morning_Menu3.getBytes());
                outFsMemo.write(morning_MenoDiet.getBytes());
                outFsEat.write(Eatimage.getBytes());

                    outFsMenu1.close();
                    outFsMenu2.close();
                    outFsMenu2.close();
                outFsMemo.close();
                outFsEat.close();

                } catch (IOException e) {

                }

//
//                BitmapDrawable Diet = (BitmapDrawable)morning_plusDiet.getDrawable();
//                Bitmap DietBit = Diet.getBitmap();
//
//                if(DietBit==null){
//                    System.out.println("여기");
//                    Drawable drawable = getResources().getDrawable(R.drawable.plus);
//                    morning_plusDiet.setImageDrawable(drawable);
//                }else {
//                    morning_plusDiet.setImageBitmap(DietBit);
//                    String morning_Dietimage = BitmapToString(DietBit);
//                    TodayDieteditor.putString("morning_Dietimage",morning_Dietimage);
//                }
//
//                morning_Menu1 = morning_editMenu1.getText().toString();
//                morning_Menu2 = morning_editMenu2.getText().toString();
//                morning_Menu3 = morning_editMenu3.getText().toString();
//                morning_MenoDiet = morning_editMenoDiet.getText().toString();
//
//                TodayDieteditor.putString("morning_Menu1",morning_Menu1);
//                TodayDieteditor.putString("morning_Menu2",morning_Menu2);
//                TodayDieteditor.putString("morning_Menu3",morning_Menu3);
//                TodayDieteditor.putString("morning_MenoDiet",morning_MenoDiet);
//
//                TodayDieteditor.commit();

                Toast.makeText(TodayDiet.this,"등록이 완료되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        //점심-저장해둔 값 불러오기
        noon_Menu1 = TodayDiet.getString("noon_Menu1","");
        noon_Menu2 = TodayDiet.getString("noon_Menu2","");
        noon_Menu3 = TodayDiet.getString("noon_Menu3","");
        noon_MenoDiet = TodayDiet.getString("noon_MenoDiet","");
        noon_image = TodayDiet.getString("noon_Dietimage",null);

        Bitmap noon_MorDietbitmap = StringToBitmap(noon_image);

        if(noon_MorDietbitmap==null){
            Drawable drawable = getResources().getDrawable(R.drawable.plus);
            noon_plusDiet.setImageDrawable(drawable);
        }else {
            noon_plusDiet.setImageBitmap(noon_MorDietbitmap);
        }

        noon_editMenu1.setText(noon_Menu1);
        noon_editMenu2.setText(noon_Menu2);
        noon_editMenu3.setText(noon_Menu3);
        noon_editMenoDiet.setText(noon_MenoDiet);

        noon_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.putExtra("CallType", 0);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                imageDietlaucher.launch(intent);
            }
        });

        imageDietlaucher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == RESULT_OK) {
                intent = result.getData();
                Uri uri = intent.getData();
                noon_plusDiet.setImageURI(uri);

            }
        });

        noon_Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //morning_plusDiet.setImageBitmap(null);
                Drawable drawable = getResources().getDrawable(R.drawable.plus);
                noon_plusDiet.setImageDrawable(drawable);
                TodayDieteditor.remove("noon_Dietimage");

            }
        });

        noon_EnrollDiet = findViewById(R.id.noon_EnrollDiet);
        noon_EnrollDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(TodayDiet.this,EnrollSuccess.class);
//                startActivity(intent);

                BitmapDrawable noonDiet = (BitmapDrawable)noon_plusDiet.getDrawable();
                Bitmap noonDietBit = noonDiet.getBitmap();

                if(noonDietBit==null){
                    System.out.println("여기");
                    Drawable drawable = getResources().getDrawable(R.drawable.plus);
                    noon_plusDiet.setImageDrawable(drawable);
                }else {
                    noon_plusDiet.setImageBitmap(noonDietBit);
                    String noonDietimage = BitmapToString(noonDietBit);
                    TodayDieteditor.putString("noon_Dietimage",noonDietimage);
                }

                noon_Menu1 = noon_editMenu1.getText().toString();
                noon_Menu2 = noon_editMenu2.getText().toString();
                noon_Menu3 = noon_editMenu3.getText().toString();
                noon_MenoDiet = noon_editMenoDiet.getText().toString();

                TodayDieteditor.putString("noon_Menu1",noon_Menu1);
                TodayDieteditor.putString("noon_Menu2",noon_Menu2);
                TodayDieteditor.putString("noon_Menu3",noon_Menu3);
                TodayDieteditor.putString("noon_MenoDiet",noon_MenoDiet);

                TodayDieteditor.commit();

                Toast.makeText(TodayDiet.this,"등록이 완료되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Bitmap StringToBitmap(String encodedString){
        try{
            byte[] encodedByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte,0,encodedByte.length);
            return bitmap;
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    public String BitmapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,70,byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String temp = Base64.encodeToString(byteArray,Base64.DEFAULT);
        return temp;
    }

//            public void black(View view) {
//                Intent intent = new Intent(this, EnrollSuccess.class); //사진불러오기
//                startActivity(intent);
//            }
//
//            public void EnrollDiet(View view) {
//                Intent intent = new Intent(this, EnrollSuccess.class); //사진불러오기
//                startActivity(intent);
//            }

    public void calorie(View view) {
        Intent intent = new Intent(this, calorie.class); //사진불러오기
        startActivity(intent);
    }


    String readMemu1(String fName) {
        String Memu1 = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fName+"menu1");
            byte[] txt = new byte[20];
            inputStream.read(txt);
            inputStream.close();
            Memu1 = (new String(txt)).trim();
        } catch (IOException e) {

        }
        return Memu1;
    }

    String readMemu2(String fName) {
        String Memu2 = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fName+"menu2");
            byte[] txt = new byte[20];
            inputStream.read(txt);
            inputStream.close();
            Memu2 = (new String(txt)).trim();
        } catch (IOException e) {

        }
        return Memu2;
    }

    String readMemu3(String fName) {
        String Memu3 = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fName+"menu3");
            byte[] txt = new byte[20];
            inputStream.read(txt);
            inputStream.close();
            Memu3 = (new String(txt)).trim();
        } catch (IOException e) {

        }
        return Memu3;
    }


    String readMemo(String fName) {
        String memo = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fName+"memo1");
            byte[] txt = new byte[50];
            inputStream.read(txt);
            inputStream.close();
            memo = (new String(txt)).trim();
        } catch (IOException e) {

        }
        return memo;
    }

    String readEatImage(String fName) {
        String eatimage = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fName+"eat");
            byte[] txt = new byte[100000000];
            inputStream.read(txt);
            inputStream.close();
            eatimage = (new String(txt)).trim();
        } catch (IOException e) {

        }
        return eatimage;
    }

        };
