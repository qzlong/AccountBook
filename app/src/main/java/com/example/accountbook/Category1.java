package com.example.accountbook;
import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;

public class Category1 extends LitePalSupport {
    @Column(unique = true, nullable = false)
    private String Category1Name;

    @Column(index = true)
    private int Number_of_Sub;

    public Category1(String n){
        Category1Name = n;
        Number_of_Sub = 0;
    }

//    public int GetID(){
//        return id;
//    }

    public String getName(){
        return Category1Name;
    }

    public void SetName(String n){
        Category1Name = n;
    }

    public boolean AddCategory2(String cate2){
        ArrayList<Category2> queryCate2 = (ArrayList<Category2>) LitePal.where("Category1Name = ? and Category2Name = ?", Category1Name, cate2).find(Category2.class);
        if(queryCate2.size()!=0){
            return false;
        }
        else{
            Category2 newCate2 = new Category2(Category1Name);
            newCate2.SetName(cate2);
            newCate2.save();
            Number_of_Sub += 1;
            return true;
        }
    }
}