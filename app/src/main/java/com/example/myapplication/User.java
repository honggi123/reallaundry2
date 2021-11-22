package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Serializable {

    String Fid;
    String id;
    String pw;
    String type;
    String haveshop;

    String shopname;

    public String getFid() {
        return Fid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public void setFid(String fid) {
        Fid = fid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHaveshop() {
        return haveshop;
    }

    public void setHaveshop(String haveshop) {
        this.haveshop = haveshop;
    }

    public String getPw() {
        return pw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }


}
