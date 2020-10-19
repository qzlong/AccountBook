package com.example.accountbook;
import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class PickerDataHelper extends LitePalSupport {
    @Column(unique = true, nullable = false)
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


    public void addCategory(String name, String cate1, String cate2){
        this.Name = name;
        this.Category1 = cate1;
        this.Category2 = cate2;
        this.save();
    }

    public ArrayList<String> findCategory1(String name){
        ArrayList<String> ret = new ArrayList<String>();
        List<PickerDataHelper> fetch_cate1 = LitePal.where("Name like ?", name).find(PickerDataHelper.class);
        for(PickerDataHelper cate1: fetch_cate1){
            ret.add(cate1.getCategory1());
        }
        Set<String> dup = new HashSet<String>(ret);
        ret.clear();
        ret.addAll(dup);
        return ret;
    }

    private ArrayList<String> findCategory2(String name, String cate1){
        ArrayList<String> ret = new ArrayList<String>();
        List<PickerDataHelper> fetch_cate2 = LitePal.where("Name like ? and Category1 like ?", name, cate1).find(PickerDataHelper.class);
        for(PickerDataHelper cate2: fetch_cate2){
            ret.add(cate2.getCategory2());
        }
        Set<String> dup = new HashSet<String>(ret);
        ret.clear();
        ret.addAll(dup);
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