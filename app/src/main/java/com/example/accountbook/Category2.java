package com.example.accountbook;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Category2 extends LitePalSupport {
    @Column(unique = true, nullable = false)
    private String Category2Name;

    @Column(index = true)
    private String Category1Name;

    public Category2(String SupCate){
        Category1Name = SupCate;
    }

//    public int GetID(){
//        return id;
//    }

    public String getName(){
        return Category2Name;
    }

    public void SetName(String n){
        Category2Name = n;
    }

    public String GetSup(){
        return Category1Name;
    }

    public void SetSup(String s){
        Category1Name = s;
    }
}