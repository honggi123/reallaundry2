package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;

public class LinkItem{

    private int list_image;
    private String list_WhereEx;
    private String list_Link;
    private boolean sel;

    public LinkItem(String list_WhereEx, String list_Link,int list_image){
        this.list_WhereEx = list_WhereEx;
        this.list_Link = list_Link ;
        this.list_image = list_image;
    }


    public boolean getsel() {
        return sel;
    }

    public void setSel(boolean sel) {
        this.sel = sel;
    }

    public int getList_image() {
        return list_image;
    }

    public void setList_image(int list_image) {

        this.list_image = list_image;
    }

    public String getList_WhereEx() {

        return list_WhereEx;
    }

    public void setList_WhereEx(String list_WhereEx) {

        this.list_WhereEx = list_WhereEx;
    }

    public String getList_Link() {

        return list_Link;
    }

    public void setList_Link(String list_Link) {

        this.list_Link = list_Link;
    }

}