package com.example.accountbook.setting;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TemplateItem {
    private String Type; //收入、支出、转账、借贷
    private String Category; //分类
    private String Value;//价格
    public TemplateItem(String Type, String Category, String Value){
        this.Type = Type;
        this.Category = Category;
        this.Value = Value;
    }
    public String getType(){
        return Type;
    }
    public String getCategory(){
        return Category;
    }
    public String getValue(){
        return Value;
    }
}
