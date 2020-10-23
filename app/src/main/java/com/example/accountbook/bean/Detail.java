package com.example.accountbook.bean;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Calendar;
//当账单类型为收入和支出时，变量名和UI对应
//当账单类型为转账时，Account1表示转出账户，Account2表示转入账户
//当账单类型为借贷时，Category1表示放贷人一级分类，Category2表示放贷人二级分类
public class Detail extends LitePalSupport implements Serializable {
    private long id;

    @Column(nullable = false)
    private String Account1;

    @Column(nullable = false)
    private String Account2;

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
    private String Note ;  //备注

    @Column(nullable = true)
    private String Cate1 ; //分类1

    @Column(nullable = true)
    private String Cate2 ; //分类2

    @Column(nullable = true)
    private String Member ; //成员

    @Column(nullable = true)
    private String Trader ; //商家

    @Column(nullable = true)
    private String Project;

    @Column(nullable = false)
    private String  Type;  //账单类型

    public boolean setMoney(float m){
//        if(m < 0)
//            return false;
//        else{
//            Money = m;
//            return true;
//        }
        this.Money = m;
        return true;
    }

    public boolean setNote(String n){
//        if(n.length() > 1000)
//            return false;
//        else{
//            this.Note = n;
//            return true;
//        }
        this.Note = n;
        return true;
    }

    public boolean setTrader(String t){
//        if(t.length() > 100)
//            return false;
//        else{
//            this.Trader = t;
//            return true;
//        }
        this.Trader = t;
        return true;
    }

    public boolean setMember(String m){
//        if(m.length() > 100)
//            return false;
//        else{
//        this.Member = m;
//            return true;
//        }
        this.Member = m;
        return true;
    }
    public void setProject(String m){
        this.Project = m;
    }

    public void setType(String Type){
        this.Type = Type;
    }

    public void setCategory(String c1, String c2){
        this.Cate1 = c1;
        this.Cate2 = c2;
    }
    public void setAccount(String a1,String a2){
        this.Account1 = a1;
        this.Account2 = a2;
    }
    public void setTime(Calendar tradeTime){
        this.Year = tradeTime.get(Calendar.YEAR);
        this.Month = tradeTime.get(Calendar.MONTH);
        this.Day = tradeTime.get(Calendar.DAY_OF_MONTH);
        this.Hour = tradeTime.get(Calendar.HOUR_OF_DAY);
        this.Minute = tradeTime.get(Calendar.MINUTE);
    }

    public long getId() {return this.id; }

    public String getAccount1(){
        return this.Account1;
    }

    public String getAccount2(){
        return this.Account2;
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
        Calendar TradeTime = Calendar.getInstance();
        TradeTime.set(Calendar.YEAR, Year);
        TradeTime.set(Calendar.MONTH, Month);
        TradeTime.set(Calendar.DAY_OF_MONTH, Day);
        TradeTime.set(Calendar.HOUR_OF_DAY, Hour);
        TradeTime.set(Calendar.MINUTE, Minute);
        return TradeTime;
    }

    public String getCategory1(){
        return this.Cate1;
    }

    public String getCategory2(){
        return this.Cate2;
    }

    public String getMember(){
        return this.Member;
    }

    public String getProject(){
        return this.Project;
    }

    public String getTrader(){
        return this.Trader;
    }
}