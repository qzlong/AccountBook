package com.example.accountbook;

import java.io.Serializable;

public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;
    private String billCategory;
    private String money;
    private String date;

    public Bill() {

    }

    public Bill(String billCategory, String money, String date) {
        this.billCategory = billCategory;
        this.money = money;
        this.date = date;
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

}
