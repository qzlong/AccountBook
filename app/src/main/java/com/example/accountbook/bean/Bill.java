package com.example.accountbook.bean;

import java.io.Serializable;
import java.util.Calendar;

public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;
    private String Account;
    private String billCategory;
    private String money;
    private String date;
    private float moneyf;
    private Calendar time;
    private String note;
    private String cate1;
    private String cate2;
    private String Type;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public Bill(String billCategory, String money, String date) {
        this.billCategory = billCategory;
        this.money = money;
        this.date = date;
    }
    public Bill(Float moneyf, String Type, Calendar time) {
        this.note = Type;
        this.moneyf = moneyf;
        this.Type = Type;
        this.time = time;
        this.cate1 = "无";
        this.cate2 = "无";
    }
    public Bill(String note, Float moneyf, String Type, Calendar time, String cate1, String cate2) {
        this.note = note;
        this.moneyf = moneyf;
        this.Type = Type;
        this.time = time;
        this.cate1 = cate1;
        this.cate2 = cate2;
    }
    public String getBillCategory() {
        return this.billCategory;
    }

    public String getMoney() {
        return this.money;
    }

    public String getDate() {
        return this.date ;
    }

    public String getNote() { return this.note; }

    public float getMoneyf() { return this.moneyf; }

    public String getType() { return this.Type; }

    public Calendar getTime() { return this.time; }

    public String getCate1() { return this.cate1; }

    public String getCate2() { return this.cate2; }



}
