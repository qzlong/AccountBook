package com.example.accountbook;
import android.util.Log;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;


import java.util.ArrayList;
import java.util.List;


public class PickerDataHelper extends LitePalSupport {
    @Column(unique = false, nullable = false)
    private String Name;

    @Column(index = true)
    private String Category1;

    @Column(index = true)
    private String Category2;

    private String getName(){
        return Name;
    }

    private String getCategory1(){
        return Category1;
    }

    private String getCategory2(){
        return Category2;
    }
    public final static String PAY_CATEGORY = "pay_category";
    public final static String INCOME_CATEGORY = "income_category";
    public final static String PEOPLE = "people";
    public final static String PROJECT="project";
    public final static String STORE = "store";
    public final static String ACCOUNT = "account";
    public final static String LENDER = "lender";

    public void addCategory(String name, String cate1, String cate2){
        this.Name = name;
        this.Category1 = cate1;
        this.Category2 = cate2;
        this.save();
    }

    public ArrayList<String> findCategory1(String name){
        ArrayList<String> ret = new ArrayList<String>();
        ArrayList<String> fetch_cate1 = new ArrayList<String>();
        List<PickerDataHelper> fetch_detail = LitePal.where("Name like ?", name).order("id asc").find(PickerDataHelper.class);
//        Log.d("test", "fetch_cate1.size:"+fetch_cate1.size());
        for(PickerDataHelper detail: fetch_detail){
            fetch_cate1.add(detail.getCategory1());
        }
        for(String cate1: fetch_cate1){
            if(!ret.contains(cate1)){
                ret.add(cate1);
            }
        }
        return ret;
    }

    private ArrayList<String> findCategory2(String name, String cate1){
        ArrayList<String> ret = new ArrayList<String>();
        ArrayList<String> fetch_cate2 = new ArrayList<String>();
        List<PickerDataHelper> fetch_detail = LitePal.where("Name like ? and Category1 like ?", name, cate1).order("id asc").find(PickerDataHelper.class);
        for(PickerDataHelper detail: fetch_detail){
            fetch_cate2.add(detail.getCategory2());
        }
        for(String cate2: fetch_cate2){
            if(!ret.contains(cate2)){
                ret.add(cate2);
            }
        }
        return ret;
    }

    public ArrayList<ArrayList<String>> findAllCategory2(String name){
        ArrayList<String> cate1_list = findCategory1(name);
        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
        for(String cate1: cate1_list){
            ret.add(findCategory2(name, cate1));
        }
        return ret;
    }
}