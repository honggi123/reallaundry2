package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class EditRoutine extends Dialog {

    //private OnDialogListener listener;
    private Context context;
    private Button mod_bt;
    private EditText mod_whereEx,mod_second,mod_set;
    private String whereEx,second,set;

    public EditRoutine(@NonNull Context context,final int position,RoutineItem routineItem) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_edit_routine);

        whereEx = routineItem.getroutine_WhereEx();
        second = routineItem.getroutine_second();
        set = routineItem.getroutine_set();

        mod_second = findViewById(R.id.mod_second);
        mod_set = findViewById(R.id.mod_set);
        mod_whereEx = findViewById(R.id.mod_whereEx);

        mod_bt = findViewById(R.id.mod_bt);

//        mod_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(listener!=null){
//                    String whereEx = mod_whereEx.getText().toString();
//                    String second = mod_second.getText().toString();
//                    String set = mod_set.getText().toString();
//
//                    listener.onFinish(position,routineItem);
//                    dismiss();
//                }
//            }
//        });
    }

//    public void setDialogListener(OnDialogListener listener){
//        this.listener = listener;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_routine);
    }
}