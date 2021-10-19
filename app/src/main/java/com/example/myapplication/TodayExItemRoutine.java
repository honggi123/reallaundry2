package com.example.myapplication;

import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

public class TodayExItemRoutine implements Serializable {

    private String routine_WhereEx;
    private int routine_second;
    private int routine_set;
    private boolean isSelected;

    public TodayExItemRoutine(String routine_WhereEx, int routine_second, int routine_set){
        this.routine_WhereEx = routine_WhereEx;
        this.routine_second = routine_second ;
        this.routine_set = routine_set ;
    }

    public String getCheck(){
        return "완전완료";
    }

    public String getroutine_WhereEx() {
        return routine_WhereEx;
    }

    public void setroutine_WhereEx(String routine_WhereEx) {
        this.routine_WhereEx = routine_WhereEx;
    }

    public int getroutine_second() {
        return routine_second;
    }

    public void setroutine_second(int routine_second) {
        this.routine_second = routine_second;
    }

    public int getroutine_set() {
        return routine_set;
    }

    public void setroutine_set(int routine_set) {
        this.routine_set = routine_set ;
    }


    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

}