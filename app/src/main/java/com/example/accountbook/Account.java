package com.example.accountbook;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Calendar;

public class Account extends LitePalSupport {
    @Column(nullable = false)
    String Account = new String();
    @Column(nullable = false)
    int NumberOfDetails;

    //ArrayList<Detail> AccountList = new ArrayList<Detail>();

    public Account(String n){
        Account = n;
        NumberOfDetails = 0;
    }

    public int getNumber(){
        return NumberOfDetails;
    }

    public void setName(String a){
        Account = a;
        this.save();
    }

    public Calendar LatelyUpdate(){
        Calendar UpdateTime = Calendar.getInstance();
        ////////////////////////////获取最后的更改时间
        return UpdateTime;
    }

    public void AddDetail(float money, Calendar time, String note, String cate1, String cate2, String member, String trader, String consumer){
        Detail newDetail = new Detail(money);
        newDetail.SetTime(time);
        newDetail.SetNote(note);
        newDetail.SetCategory(cate1, cate2);
        newDetail.SetMember(member);
        newDetail.SetTrader(trader);
        newDetail.SetConsumer(consumer);
        newDetail.save();
        NumberOfDetails += 1;
    }

    public ArrayList<Detail> GetAllDetail(){
        ArrayList<Detail> ret = (ArrayList<Detail>) LitePal.where("Account like ?", Account).order("Money").find(Detail.class);
        return ret;
    }

    public ArrayList<Detail> GetDetailByDate(int year1, int month1, int day1, int year2, int month2, int day2){
        ArrayList<Detail> AllDetail = (ArrayList<Detail>) LitePal.where("Account like ?", Account).order("Money").find(Detail.class);
        int time1 = (year1-1970) * 10000 + month1 * 100 + day1;
        int time2 = (year2-1970) * 10000 + month2 * 100 + day2;
        ArrayList<Detail> ret = new ArrayList<Detail>();
        for(Detail detail: AllDetail){
            Calendar time = detail.GetTime();
            int detail_time = (time.get(Calendar.YEAR) - 1970) * 10000 + time.get(Calendar.MONTH) * 100 + time.get(Calendar.DAY_OF_MONTH);
            if(detail_time <= time2 && detail_time >= time1)
                ret.add(detail);
        }
        return ret;
    }

    public ArrayList<Detail> GetDetailByCate1(String cate1){
        ArrayList<Detail> ret = (ArrayList<Detail>) LitePal.where("Account like ? and Category1Name like ?", Account, cate1).order("Money").find(Detail.class);
        return ret;
    }

    public ArrayList<Detail> GetDetailByCate2(String cate2){
        ArrayList<Detail> ret = (ArrayList<Detail>) LitePal.where("Account like ? and Category2Name like ?", Account, cate2).order("Money").find(Detail.class);
        return ret;
    }

    public ArrayList<Detail> GetDetailByMoney(float money1, float money2){
        ArrayList<Detail> ret = (ArrayList<Detail>) LitePal.where("Account like ? and Money > ? and Money < ?", Account, Float.toString(money1), Float.toString(money2)).order("Money").find(Detail.class);
        return ret;
    }


    public void DeleteAll(){
        LitePal.deleteAll(Detail.class, "Account like ?", Account);
    }
}