package com.example.accountbook;
import android.app.Application;
import com.example.accountbook.Account;
import org.litepal.LitePal;


import java.util.ArrayList;
import java.util.List;

public class PickerDataHelper extends Application {
    public ArrayList<ArrayList<String>> getAllCategories(){
        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
        ArrayList<Category1> cate1List = (ArrayList<Category1>) LitePal.findAll(Category1.class);
        for(Category1 cate1: cate1List){
            List<Category2> cate2List = LitePal.where("Category1Name like ?", cate1.getName()).find(Category2.class);
            ArrayList<String> subret = new ArrayList<String>();
            subret.add(cate1.getName());
            for(Category2 cate2: cate2List){
                subret.add(cate2.getName());
            }
            ret.add(subret);
        }
        return ret;
    }

    public boolean AddCate1(String NewCate1){
        List<Category1> queryCate1 = LitePal.where("Category1Name like ?", NewCate1).find(Category1.class);
        if(queryCate1.size()!=0)
            return false;
        else{
            Category1 newCategory1  = new Category1(NewCate1);
            newCategory1.save();
            return true;
        }
    }

    public boolean AddCate2(String Cate1,Category2 newCate2){
        List<Category1> queryCate1 = LitePal.where("Category1Name like ?", Cate1).find(Category1.class);
        if(queryCate1.size()!=0){
            Category1 SupCate = new Category1(Cate1);
            return SupCate.AddCategory2(newCate2.getName());
        }
        else return false;
    }


    public void init(){
        LitePal.initialize(this);
    }

    public void createAccount(String AccountName){
        Account bill = new Account(AccountName);
        bill.save();
    }
}
