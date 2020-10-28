package com.example.accountbook.bean;

import java.util.Calendar;

public class mCalendar {
    private int year;
    private int month;
    private int dayOfMonth;
    private int hourOfDay;
    private int minute;
//    private int total_day;

    public mCalendar(){
        this.year = 2020;
        this.month = 10;
        this.dayOfMonth = 20;
        this.hourOfDay = 12;
        this.minute = 30;
//        this.total_day = 31;
    }
    public mCalendar(int year, int month, int day, int hour, int minute){
        this.year = year;
        this.month = month;
        this.dayOfMonth = day;
        this.hourOfDay = hour;
        this.minute = minute;
    }
    public mCalendar(Detail detail) {
        this.year = detail.getYear();
        this.month = detail.getMonth();
        this.dayOfMonth = detail.getDay();
        this.hourOfDay = detail.getHour();
        this.minute = detail.getMinute();
    }
    public mCalendar(Calendar time) {
        this.year = time.get(Calendar.YEAR);
        this.month = time.get(Calendar.MONTH);
        this.dayOfMonth = time.get(Calendar.DAY_OF_MONTH);
        this.hourOfDay = time.get(Calendar.HOUR_OF_DAY);
        this.minute = time.get(Calendar.MINUTE);
    }
    public void setYear(int year) {
        if (year < 0) {
            this.year = 0;
            return;
        }
        this.year = year;
    }

    public void setMonth(int month) {
        if (month < 0 && month >= -12) {
            this.setYear(this.year - 1);
            this.month = month + 12;
        }
        this.month = month;
    }


    private int getTotal_day() {
        int day = 30;
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                day = 31;
            case 3:
            case 5:
            case 8:
            case 10:
                day = 30;
            case 1:
                day = (year % 4 == 0) ? 29 : 28;
        }
        return day;
    }

    private int getBeforeTotalDay() {
        int day = 30;
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
            case 0:
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
                day = 31;
            case 2:
                day = (year % 4 == 0) ? 29 : 28;
        }
        return day;
    }

    public void setDayOfMonth(int dayOfMonth) {
        int before_total_day = getBeforeTotalDay();
        if (dayOfMonth <= 0 && dayOfMonth >= -before_total_day) {
            this.setMonth(this.month - 1);
            this.dayOfMonth = dayOfMonth + before_total_day + 1;
        }
        this.dayOfMonth = dayOfMonth;
    }

    public void setHourOfDay(int hourOfDay) {
        if (hourOfDay < 0 && hourOfDay >= -24) {
            this.setDayOfMonth(this.dayOfMonth - 1);
            this.hourOfDay = hourOfDay + 24;
        }
        this.hourOfDay = hourOfDay;
    }

    public void setMinute(int minute) {
        if (minute < 0 && minute >= -60) {
            this.setHourOfDay(this.hourOfDay - 1);
            this.minute = minute + 60;
        }
        this.minute = minute;
    }

//
    public void setTime(mCalendar time) {
        this.setYear(time.getYear());
        this.setMonth(time.getMonth());
        this.setDayOfMonth(time.getDayOfMonth());
        this.setHourOfDay(time.getHourOfDay());
        this.setMinute(time.getMinute());
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public boolean before(mCalendar when) {
        if (this.year < when.getYear()) return true;
        else if(this.year > when.getYear()) return false;

        if (this.month < when.getMonth()) return true;
        else if (this.month > when.getMonth()) return false;

        if (this.dayOfMonth < when.getDayOfMonth()) return true;
        else if (this.dayOfMonth > when.getDayOfMonth()) return false;

        if (this.hourOfDay < when.getHourOfDay()) return true;
        else if (this.hourOfDay > when.getHourOfDay()) return false;

        return this.minute <= when.getMinute();

    }

    public void set(int year, int month, int day, int hour, int minute){
        this.setYear(year);
        this.setMonth(month);
        this.setDayOfMonth(day);
        this.setHourOfDay(hour);
        this.setMinute(minute);
    }
    public void set(int month, int day, int hour, int minute){
        this.setMonth(month);
        this.setDayOfMonth(day);
        this.setHourOfDay(hour);
        this.setMinute(minute);
    }
    public void set(int day, int hour, int minute){
        this.setDayOfMonth(day);
        this.setHourOfDay(hour);
        this.setMinute(minute);
    }
    public void set(int hour, int minute){
        this.setHourOfDay(hour);
        this.setMinute(minute);
    }
}
