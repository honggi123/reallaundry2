package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import java.util.logging.Handler;


public class SelectItem extends AppCompatActivity {


    public ArrayList whereEx11 = new ArrayList();
    private SoundPool soundPool;

    RecyclerView select_rv;
    Intent intent;
    SelectAdapter selectAdapter;

    TextView showWhere;
    TextView showSecond;
    TextView showSet;

    public static Context mContext;
    public String check = "nonCheck";

    private ProgressBar progressBar;

    int second;
    int set;
    int time;

    String whereEx;
    String where11;

    public static Context context_main;

    ArrayList<TodayExItemRoutine> selectTodayExArrayList;

    Button start;
    Button stop;

    private Handler mHandler = new Handler();
    String running = "init";

    int running1 = timer_start;

    ArrayList timer = new ArrayList();
    ArrayList seter = new ArrayList();
    ArrayList whereExer = new ArrayList();
    int totaltime = 0;

    private static final int timer_start = 10;
    private static final int timer_stop = 20;
    private static final int timer_restart = 30;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);

        context_main = this;

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        int set_sound = soundPool.load(this,R.raw.setalarm,0);
        int finish_sound = soundPool.load(this,R.raw.finishalarm,0);


        mContext = this;
        intent = getIntent();
        selectTodayExArrayList = (ArrayList<TodayExItemRoutine>) intent.getSerializableExtra("select");
        select_rv = findViewById(R.id.select_rv);
        selectAdapter = new SelectAdapter(selectTodayExArrayList, SelectItem.this);
        LinearLayoutManager link_manager = new LinearLayoutManager(this);
        select_rv.setHasFixedSize(true);
        select_rv.setLayoutManager(link_manager);
        select_rv.setAdapter(selectAdapter);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        progressBar = findViewById(R.id.progressBar);

        showWhere = findViewById(R.id.showWhere);
        showSet = findViewById(R.id.showSet);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start(running1);
                running = "init";
                running1= timer_start;
                progress progress = new progress(mHandler);
                progress.setDaemon(true);
                progress.start();

                //mHandler.sendEmptyMessage(10);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = "stop";
                running1 = timer_stop;
                System.out.println(running);
                mHandler.removeCallbacksAndMessages(null);
                timer.clear();
                whereExer.clear();
                //progressBar.setProgress(0);
//                showSet.setText("세트");
//                showWhere.setText("운동부위");

               //mHandler.sendEmptyMessage(0);

//                start.setText("재시작");
//                start.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        running = "init";
//                        running1 = timer_start;
//                        System.out.println(running);
//                        mHandler.sendEmptyMessage(0);
//                        progress progress = new progress(mHandler);

//                    }
//                });

            }
        });



        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                      int value = msg.getData().getInt("value");
                      int time = msg.getData().getInt("time");
                      int set = msg.getData().getInt("set");
                      String now = msg.getData().getString("now");
                      String where = msg.getData().getString("where");

                      progressBar.setProgress(value);
                      progressBar.setMax(time);

                      showWhere.setText(where);
                      showSet.setText(String.valueOf(set)+"세트");
                      System.out.println(now);
                      if(showSet.equals(1)){
                          soundPool.play(set_sound,3f,3f,0,0,1f);
                      }
                      if(now!=null && now.equals("now")==true){
                          Toast.makeText(SelectItem.this, where+" 운동 완료!", Toast.LENGTH_SHORT).show();
                          soundPool.play(finish_sound,3f,3f,0,0,1f);
                          whereEx11.add(where);
                          now = "notnow";
                          where = "where";
                          check ="check";
                      }

                      System.out.println(time+"time"+value+"value");
                      if (time <= value) {
                          Toast.makeText(SelectItem.this, "운동을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                          System.out.println("time over");
                          soundPool.play(finish_sound,3f,3f,0,0,1f);
                          running = "stop";
                          //start.setText("시작");

                          if(whereExer.size()>0) {
                              where = (String) whereExer.get(whereExer.size() - 1);
                              System.out.println(whereEx + "whereEx");
                              whereEx11.add(where);
                          }

                          Date date = new Date();
                          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_d");
                          String nowdate = dateFormat.format(date);

                          System.out.println(nowdate);

                          try {
                              FileOutputStream outFswhereEx = openFileOutput(nowdate+"whereEx",Context.MODE_PRIVATE);

                              String str = String.format("%s\n",whereEx11.toString());
                              outFswhereEx.write(str.getBytes());
                              outFswhereEx.close();

                          } catch (FileNotFoundException e) {
                              e.printStackTrace();
                          } catch (IOException e) {
                              e.printStackTrace();
                          }


                          mHandler.removeCallbacksAndMessages(null);
                          timer.clear();
                          whereExer.clear();
                          progressBar.setProgress(0);
                          showSet.setText("세트");
                          showWhere.setText("운동부위");

                      }

                   }

        };

    }


    class progress extends Thread {

        private Handler mHandler;

        public progress(Handler mHandler) {
            this.mHandler = mHandler;

        }

        public void run() {

            int value = 0;
            int time = 0;
            int total = 0;
            int second = 0;
            int showset = 1;
            String now = "notnow";
            String where = "where";

            finishTime();
            setwhereEx();
            getSet();

            for (int i = 0; i < timer.size(); i++) {
                time += (int) timer.get(i);
            }

            System.out.println(running1+"running상태");

                for (int i = 0; i < timer.size(); i++) {
                        second = (int) seter.get(i);
                    while (true) {
                        while (running.equals("init")) {
                            value++;
                            Message message = mHandler.obtainMessage();
                            Bundle bundle = new Bundle();

                            now = "notnow";
                            where = (String) whereExer.get(i);

                            if (i == 0) {
                                totaltime = (int) timer.get(i);
                                total = totaltime;

                            }

                            set = total / second;
                            System.out.println(total + "토탈" + value + "value" + i + "i값");

                            while (total >= value) {

                                if (second + 1 == value) {
                                    showset++;
                                    System.out.println(showset + "showset");
                                    second += (int) seter.get(i);
                                    break;
                                }

                                if (total == value) {
                                    showset = 0;
                                    if (i + 1 == timer.size()) {
                                        break;
                                    }
                                    now = "now";
                                    i++;
                                    total = total + (int) timer.get(i);
                                }
                                break;

                            }


                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            bundle.putInt("set", showset);
                            bundle.putString("now", now);
                            bundle.putString("where", where);
                            bundle.putInt("total", total);
                            bundle.putInt("value", value);

                            bundle.putInt("time", time);

                            message.setData(bundle);
                            mHandler.sendMessage(message);

                        } break;

                    } break;


                }
            }


        }


    public String setwhereEx() {
        selectTodayExArrayList = (ArrayList<TodayExItemRoutine>) intent.getSerializableExtra("select");

        for (int i = 0; i < selectTodayExArrayList.size(); i++) {
            whereEx = selectTodayExArrayList.get(i).getroutine_WhereEx();
            whereExer.add(whereEx);
        }
        return whereEx;
    }

    public int finishTime() {

        selectTodayExArrayList = (ArrayList<TodayExItemRoutine>) intent.getSerializableExtra("select");
        for (int i = 0; i < selectTodayExArrayList.size(); i++) {

            second = selectTodayExArrayList.get(i).getroutine_second();
            set = selectTodayExArrayList.get(i).getroutine_set();
            time = second * set;
            timer.add(time);
        }
        return time;
    }

    public int getSet() {

        selectTodayExArrayList = (ArrayList<TodayExItemRoutine>) intent.getSerializableExtra("select");
        for (int i = 0; i < selectTodayExArrayList.size(); i++) {
            second = selectTodayExArrayList.get(i).getroutine_second();
            set = selectTodayExArrayList.get(i).getroutine_set();
            seter.add(second);
        }
        return second;
    }


    public void start(int status) {
        switch (status) {
            //시작
            case timer_start:

                //Handler로 메세지를 전달해서 타이머를 시작
                mHandler.sendEmptyMessage(0);
                running1 = timer_stop;
                break;

            case timer_stop:
                start.setText("재시작");
                running1 = timer_restart;
                break;
            case timer_restart:
                start.setText("시작");
                running1 = timer_start;
                mHandler.sendEmptyMessage(0);
        }
    }
}


