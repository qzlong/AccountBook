package com.example.accountbook;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.Calendar;

public class Detail extends LitePalSupport {
    @Column(nullable = false)
    private String Account = new String();

    @Column(nullable = false)
    private float Money;

    @Column()
    private int Year;

    @Column()
    private int Month;

    @Column()
    private int Day;

    @Column()
    private int Hour;

    @Column()
    private int Minute;

    @Column(nullable = true)
    private String Note = new String();

    @Column(nullable = true)
    private String Cate1 = new String();

    @Column(nullable = true)
    private String Cate2 = new String();

    @Column(nullable = true)
    private String Member = new String();

    @Column(nullable = true)
    private String Trader = new String();

    @Column(nullable = true)
    private String Consumer = new String();

    @Column(nullable = false)
    private String  Type;

    public Detail(){
        Money = 0;
    }

    public Detail(float m){
        Money = m;
    }

    public boolean SetMoney(float m){
        if(m < 0)
            return false;
        else{
            Money = m;
            this.save();
            return true;
        }
    }

    public boolean SetNote(String n){
        if(n.length() > 1000)
            return false;
        else{
            this.Note = n;
            this.save();
            return true;
        }
    }

    public boolean SetTrader(String t){
        if(t.length() > 100)
            return false;
        else{
            this.Trader = t;
            this.save();
            return true;
        }
    }

    public boolean SetMember(String m){
        if(m.length() > 100)
            return false;
        else{
            this.Member = m;
            this.save();
            return true;
        }
    }

    public boolean SetConsumer(String c){
        if(c.length() > 100)
            return false;
        else{
            this.Consumer = c;
            this.save();
            return true;
        }
    }

    public void SetType(String Type){
        this.Type = Type;
        this.save();
    }

    public void SetCategory(String c1, String c2){
        Cate1 = c1;
        Cate2 = c2;
        this.save();
    }

    public void SetTime(Calendar tradetime){
        this.Year = tradetime.get(Calendar.YEAR);
        this.Month = tradetime.get(Calendar.MONTH);
        this.Day = tradetime.get(Calendar.DAY_OF_MONTH);
        this.Hour = tradetime.get(Calendar.HOUR_OF_DAY);
        this.Minute = tradetime.get(Calendar.MINUTE);
        this.save();
    }

//    public int GetID(){
//        return id;
//    }

    public String GetAccount(){
        return this.Account;
    }

    public String  GetType(){
        return this.Type;
    }

    public float GetMoney(){
        return this.Money;
    }

    public String GetNote(){
        return this.Note;
    }

    public Calendar GetTime(){
        Calendar TradeTime = Calendar.getInstance();
        TradeTime.set(Calendar.YEAR, Year);
        TradeTime.set(Calendar.MONTH, Month);
        TradeTime.set(Calendar.DAY_OF_MONTH, Day);
        TradeTime.set(Calendar.HOUR_OF_DAY, Hour);
        TradeTime.set(Calendar.MINUTE, Minute);
        return TradeTime;
    }

//    public String GetCategory1(){
//        return Category1;
//    }
//
//    public String GetCategory2(){
//        return Category2;
//    }

    public String GetMember(){
        return Member;
    }

    public String GetConsumer(){
        return Consumer;
    }

    public String GetTrader(){
        return Trader;
    }
}