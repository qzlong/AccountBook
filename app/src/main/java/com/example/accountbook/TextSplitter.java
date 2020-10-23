package com.example.accountbook;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextSplitter {
    private ArrayList<String> res = new ArrayList<>();

    public ArrayList<String> splitArrow(String s){
        res.clear();
        if(s.length()==0){
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
}
