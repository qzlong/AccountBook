package com.example.accountbook.helper;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.example.accountbook.bean.Detail;

import org.litepal.LitePal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;

public class CsvIOHelper{

    public boolean writeDetail(String filepath){
        try {
            CsvWriter backup_writer = new CsvWriter(filepath, ',', Charset.forName("GBK"));
            String[] headers = {"Account1", "Account2", "Money","Year","Month","Day","Hour","Minute","Note","Cate1","Cate2","Member","Trader","Project","Type"};
            backup_writer.writeRecord(headers);

            List<Detail> data_groups = LitePal.findAll(Detail.class);

            for(Detail line: data_groups){
                Calendar detail_time = line.getTime();
                String[] content = {
                                        line.getAccount1(),
                                        line.getAccount2(),
                                        String.valueOf(line.getMoney()),
                                        String.valueOf(detail_time.get(Calendar.YEAR)),
                                        String.valueOf(detail_time.get(Calendar.MONTH)),
                                        String.valueOf(detail_time.get(Calendar.DAY_OF_MONTH)),
                                        String.valueOf(detail_time.get(Calendar.HOUR_OF_DAY)),
                                        String.valueOf(detail_time.get(Calendar.MINUTE)),
                                        line.getNote(),
                                        line.getCategory1(),
                                        line.getCategory2(),
                                        line.getMember(),
                                        line.getTrader(),
                                        line.getProject(),
                                        line.getType()
                };
                backup_writer.writeRecord(content);
            }

            backup_writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void readDetail(String filePath){

        try {
            // 创建CSV读对象
            CsvReader backup_reader = new CsvReader(filePath, ',', Charset.forName("GBK"));

            // 读表头
            backup_reader.readHeaders();
            while (backup_reader.readRecord()){
                Detail write_detail = new Detail();
                write_detail.setAccount(backup_reader.get("Account1"), backup_reader.get("Account2"));
                write_detail.setMoney(Float.parseFloat(backup_reader.get("Money")));
                Calendar detail_time = Calendar.getInstance();
                detail_time.set(Calendar.YEAR, Integer.parseInt(backup_reader.get("Year")));
                detail_time.set(Calendar.MONTH, Integer.parseInt(backup_reader.get("Month")));
                detail_time.set(Calendar.DAY_OF_MONTH, Integer.parseInt(backup_reader.get("Day")));
                detail_time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(backup_reader.get("Hour")));
                detail_time.set(Calendar.MINUTE, Integer.parseInt(backup_reader.get("Minute")));
                write_detail.setTime(detail_time);
                write_detail.setNote(backup_reader.get("Note"));
                write_detail.setCategory(backup_reader.get("Cate1"), backup_reader.get("Cate2"));
                write_detail.setMember(backup_reader.get("Member"));
                write_detail.setTrader(backup_reader.get("Trader"));
                write_detail.setProject(backup_reader.get("Project"));
                write_detail.setType(backup_reader.get("Type"));
                write_detail.save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}