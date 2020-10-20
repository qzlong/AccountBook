package com.example.accountbook;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DetailDBHelper extends LitePalSupport {
    @Column(nullable = false)
    private String Account ;

    @Column(nullable = false)
    private float Money;

    @Column(nullable = false)
    private int Year;

    @Column(nullable = false)
    private int Month;

    @Column(nullable = false)
    private int Day;

    @Column(nullable = false)
    private int Hour;

    @Column(nullable = false)
    private int Minute;

    @Column(nullable = true)
    private String Note ;

    @Column(nullable = false)
    private String Cate1;

    @Column(nullable = false)
    private String Cate2 ;

    @Column(nullable = true)
    private String Member ;

    @Column(nullable = true)
    private String Trader;  //商家

    @Column(nullable = false)
    private String Type;

    private Calendar Time;  //仅作为中转使用

    public boolean setMoney(float m){
        if(m < 0)
            return false;
        else{
            Money = m;
            this.save();
            return true;
        }
    }

    public boolean setNote(String n){
        if(n.length() > 1000)
            return false;
        else{
            this.Note = n;
            this.save();
            return true;
        }
    }

    public boolean setTrader(String t){
        if(t.length() > 100)
            return false;
        else{
            this.Trader = t;
            this.save();
            return true;
        }
    }

    public boolean setMember(String m){
        if(m.length() > 100)
            return false;
        else{
            this.Member = m;
            this.save();
            return true;
        }
    }

    public void setType(String  t){
        this.Type = t;
        this.save();
    }

    public void setCategory(String c1, String c2){
        Cate1 = c1;
        Cate2 = c2;
        this.save();
    }

    public void setTime(Calendar tradetime){
        this.Time = tradetime;
        this.Year = tradetime.get(Calendar.YEAR);
        this.Month = tradetime.get(Calendar.MONTH);
        this.Day = tradetime.get(Calendar.DAY_OF_MONTH);
        this.Hour = tradetime.get(Calendar.HOUR_OF_DAY);
        this.Minute = tradetime.get(Calendar.MINUTE);
        this.save();
    }

    public String getAccount(){
        return this.Account;
    }

    public String  getType(){
        return this.Type;
    }

    public float getMoney(){
        return this.Money;
    }

    public String getNote(){
        return this.Note;
    }

    public Calendar getTime(){
        return this.Time;
    }

    public String GetCate1(){
        return this.Cate1;
    }

    public String GetCate2(){
        return this.Cate2;
    }

    public String GetMember(){
        return this.Member;
    }

    public String GetTrader(){
        return this.Trader;
    }

    public void addDetail(String account_type, String account, float money, Calendar time, String note, String cate1, String cate2, String member, String trader){
        this.Account = account;
        this.Money = money;
        this.Time = time;
        this.Note = note;
        this.Cate1 = cate1;
        this.Cate2 = cate2;
        this.Member = member;
        this.Trader = trader;
        this.Type = account_type;
        this.Year = Time.get(Calendar.YEAR);
        this.Month = Time.get(Calendar.MONTH);
        this.Day = Time.get(Calendar.DAY_OF_MONTH);
        this.Hour = Time.get(Calendar.HOUR_OF_DAY);
        this.Minute = Time.get(Calendar.MINUTE);
        this.save();
    }

    public ArrayList<String> getAllAccount(){
        ArrayList<String> ret = new ArrayList<String>();
        List<Detail> detail_list = LitePal.findAll(Detail.class);
        for(Detail det: detail_list){
            ret.add(det.GetAccount());
        }
        Set<String> dup = new HashSet<String>(ret);
        ret.clear();
        ret.addAll(dup);
        return ret;
    }

    public ArrayList<Detail> getAllDetail(String account){
        ArrayList<Detail> ret = new ArrayList<Detail>();
        List<Detail> detail_list = LitePal.where("Account like ?", account).order("Money").find(Detail.class);
        ret.addAll(detail_list);
        return ret;
    }

    public ArrayList<Detail> getDetailByDate(String account, int year1, int month1, int day1, int year2, int month2, int day2){
        List<Detail> all_detail = (ArrayList<Detail>) LitePal.where("Account like ?", account).order("Money").find(Detail.class);
        int time1 = (year1-1970) * 10000 + month1 * 100 + day1;
        int time2 = (year2-1970) * 10000 + month2 * 100 + day2;
        ArrayList<Detail> ret = new ArrayList<Detail>();
        for(Detail det: all_detail){
            Calendar time = det.GetTime();
            int detail_time = (time.get(Calendar.YEAR) - 1970) * 10000 + time.get(Calendar.MONTH) * 100 + time.get(Calendar.DAY_OF_MONTH);
            if(detail_time <= time2 && detail_time >= time1)
                ret.add(det);
        }
        return ret;
    }

    public ArrayList<Detail> getDetailByMoney(String account, float money1, float money2){
        List<Detail> detail_list = (ArrayList<Detail>) LitePal.where("Account like ? and Money > ? and Money < ?", account, Float.toString(money1), Float.toString(money2)).order("Money").find(Detail.class);
        ArrayList<Detail> ret = new ArrayList<>();
        ret.addAll(detail_list);
        return ret;
    }
}