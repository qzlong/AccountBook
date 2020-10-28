package com.example.accountbook.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextSplitter {
    private ArrayList<String> res = new ArrayList<>();

    public ArrayList<String> splitArrow(String s) {
        res.clear();
        if (s.length() == 0) {
            res.add(null);
            res.add(null);
            return res;
        }
        String pattern = "(.+)->(.+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        m.find();
        res.add(m.group(1));
        res.add(m.group(2));
        return res;
    }

    public ArrayList<Integer> splitDate(String s) {
        ArrayList<Integer> res = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        if (s.length() == 0) {
            res.add(date.get(Calendar.YEAR));
            res.add(date.get(Calendar.MONTH));
            res.add(date.get(Calendar.DAY_OF_MONTH));
            return res;
        }
        String pattern = "(.+)年(.+)月(.+)日";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        if (m.find()) {
            res.add(Integer.valueOf(m.group(1)));
            res.add(Integer.valueOf(m.group(2)));
            res.add(Integer.valueOf(m.group(3)));
        }
        return res;
    }

    public ArrayList<Integer> splitTime(String s) {
        ArrayList<Integer> res = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        if (s.length() == 0) {
            res.add(date.get(Calendar.HOUR_OF_DAY));
            res.add(date.get(Calendar.MINUTE));
            return res;
        }
        String pattern = "(.+)时(.+)分";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        if (m.find()){
            res.add(Integer.valueOf(m.group(1)));
            res.add(Integer.valueOf(m.group(2)));
        }
        return res;
    }
}
