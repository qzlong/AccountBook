package com.example.accountbook.bean;

import com.contrarywind.interfaces.IPickerViewData;

public class CategoryBean implements IPickerViewData {
    private long id;
    private String category;




    @Override
    public String getPickerViewText() {
        return null;
    }
}
