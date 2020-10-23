package com.example.accountbook.pickers;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;

public class NoLinkPicker {
    OptionsPickerView optionsPickerView ;
//    private  ArrayList<String> defaultList = new ArrayList<>();
    public OptionsPickerView getOptionsPickerView(Context mContext, String titlename, final Button btn, final ArrayList array1, final ArrayList array2){
        OptionsPickerBuilder opbuilder = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx =array1.get(options1)+"->"+array2.get(options2);
                btn.setText(tx);
            }
        }).setTitleText(titlename)
                .setContentTextSize(20)
                .setSelectOptions(0,0)
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.LTGRAY)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setOutSideCancelable(true)
                .isCenterLabel(true)
                .setLabels(":转出",":转入",null)
                .isRestoreItem(true)
                .isCenterLabel(false)
                .setOutSideColor(0x00000000)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String tx =array1.get(options1)+"->"+array2.get(options2);
                        btn.setText(tx);
                    }
                })
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
//        defaultList.add("所有");
        optionsPickerView = opbuilder.build();
        optionsPickerView.setNPicker(array1,array2,null);
        return optionsPickerView;
    }
}
