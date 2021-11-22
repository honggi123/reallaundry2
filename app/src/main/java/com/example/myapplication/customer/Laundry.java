package com.example.myapplication.customer;

import androidx.recyclerview.widget.RecyclerView;

public class Laundry {

    String Lid;
    String type;
    int num;
    String wantdate;
    String fromid;
    String Ffromid;
    String toshop;
    int status;

    public String getLid() {
        return Lid;
    }

    public void setLid(String lid) {
        Lid = lid;
    }


    public String getFfromid() {
        return Ffromid;
    }

    public void setFfromid(String ffromid) {
        Ffromid = ffromid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public String getFromid() {
        return fromid;
    }

    public String getToshop() {
        return toshop;
    }

    public String getType() {
        return type;
    }

    public String getWantdate() {
        return wantdate;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public void setToshop(String toshop) {
        this.toshop = toshop;
    }

    public void setWantdate(String wantdate) {
        this.wantdate = wantdate;
    }
}
