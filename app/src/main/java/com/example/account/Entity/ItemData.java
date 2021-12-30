package com.example.account.Entity;

import java.io.Serializable;

public class ItemData implements Serializable {
    public int icon;
    public String title;
    public String money;
    public String date;
    public int inout;



    public ItemData(String title, String money,String date,int icon, int inout){
        this.title=title;
        this.money=money;
        this.date=date;
        this.icon=icon;
        this.inout=inout;
    }

    public String getTitle(){

        return title;
    }

    public String getMoney(){
        return money;
    }

    public int getIcon(){
        return icon;
    }

    public String getDate(){
        return date;
    }

    public String getInout(){
        String inout_no="+";
        if(inout==0){
            inout_no="-";
        }
        return inout_no;
    }

    public void setTitle(String title1){
        this.title=title1;
    }

    public void setIcon(int icon1){
        this.icon=icon1;
    }

    public void setMoney(String money1){
        this.money=money1;
    }

    public void setDate(String date1){
        this.date=date1;
    }

    public void setInout(int inout1){
        this.inout=inout1;
    }

}
