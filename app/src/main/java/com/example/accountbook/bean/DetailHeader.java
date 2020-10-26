package com.example.accountbook.bean;

import java.util.ArrayList;
import java.util.List;

public class DetailHeader {
    private String type;
    private String title;
    private String subtitle;
    private float remain;
    private float income;
    private float expend;
    private List<Detail> dataList = new ArrayList<>();
    public DetailHeader(){
        this.type = "时间";
        this.title = "";
        this.subtitle = "";
    }
    public DetailHeader(String title, String subtitle, float remain, List<Detail> dataList) {
        this.title = title;
        this.subtitle = subtitle;
        this.remain = remain;
        setDataList(dataList);
    }
    public void setType(String type) { this.type = type; }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public void setRemain(float remain) {
        this.remain = remain;
    }
    public void setExpend(float expend) {
        this.expend = expend;
    }
    public void setIncome(float income) {
        this.income = income;
    }
    public void setDataList(List<Detail> data_list) {
        if (this.dataList.size() != 0) {
            this.dataList.clear();
        }
        this.dataList.addAll(data_list);
        this.income = getSumIncome(data_list);
        this.expend = getSumExpend(data_list);
        this.remain = this.income - this.expend;
    }

    public String getType() { return this.type; }
    public String getTitle() {
        return this.title;
    }
    public String getSubtitle() {
        return this.subtitle;
    }
    public float getRemain() {
        return this.remain;
    }
    public float getIncome() {
        return this.income;
    }
    public float getExpend() {
        return this.expend;
    }
    public List<Detail> getDataList() {
        return this.dataList;
    }
    /**
     * @param list
     * @return 获取指定账单列表收入之和
     */
    private float getSumIncome(List<Detail> list) {
        float sum = 0;
        for (Detail bill : list) {
            if (bill.getType().equals("INCOME")) {
                sum += bill.getMoney();
            }
        }
        return sum;
    }
    /**
     * @param list
     * @return 获取指定账单列表支出之和
     */
    private float getSumExpend(List<Detail> list) {
        float sum = 0;
        for (Detail bill : list) {
            if (bill.getType().equals("PAY")) {
                sum += bill.getMoney();
            }
        }
        return sum;
    }
}
