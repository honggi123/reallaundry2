package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class RoutineItem {

    int list_image;
    private String routine_WhereEx;
    private String routine_second;
    private String routine_set;

    Button delete;

    public RoutineItem(String routine_WhereEx,String routine_second,String routine_set){
        this.routine_WhereEx = routine_WhereEx;
        this.routine_second = routine_second ;
        this.routine_set = routine_set ;
    }

    public String getroutine_WhereEx() {
        return routine_WhereEx;
    }

    public void setroutine_WhereEx(String routine_WhereEx) {
        this.routine_WhereEx = routine_WhereEx;
    }

    public String getroutine_second() {
        return routine_second;
    }

    public void setroutine_second(String routine_second) {
        this.routine_second = routine_second;
    }

    public String getroutine_set() {
        return routine_set;
    }

    public void setroutine_set(String routine_set) {
        this.routine_set = routine_set ;
    }

    public String getdelete() {
        return routine_set;
    }

    public void setdelete(Button delete) {
        this.delete = delete ;
    }


}