package com.example.accountbook.setting;

import com.example.accountbook.Detail;

import org.litepal.LitePal;

import java.util.List;

public class DBExportToCSV {
    private List<Detail> details;
    public void dbToCsv(){
        details = LitePal.findAll(Detail.class);
        for(Detail detail:details){
            System.out.println(detail.getType());
        }
    }
}
