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

public class OptionsPicker {
    OptionsPickerView optionsPickerView ;
    public OptionsPickerView getOptionsPickerView(Context mContext, String titlename, final Button btn, final ArrayList array1, final ArrayList<ArrayList<String>> array2){
        OptionsPickerBuilder opbuilder = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx =array1.get(options1)+"->"+array2.get(options1).get(options2);
                btn.setText(tx);
            }
        }).setTitleText(titlename)
                .setContentTextSize(20)
                .setSelectOptions(0,0)
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.LTGRAY)
                .setCancelColor(Color.BLACK)
                .setOutSideCancelable(true)
                .setSubmitColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(true)
                .isCenterLabel(false)
                .setOutSideColor(0x00000000)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String tx =array1.get(options1)+"->"+array2.get(options1).get(options2);
                        btn.setText(tx);
                    }
                })
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        optionsPickerView = opbuilder.build();
        optionsPickerView.setPicker(array1,array2);
        return optionsPickerView;
    }
}
