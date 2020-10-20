package com.example.accountbook.pickers;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;

public class TimePicker {
    private TimePickerView time_picker;
    private Calendar date;

    private void initTimePicker(Context context) {
        TimePickerBuilder time_builder = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

            }
        }).setType(new boolean[]{false,false,false,true,true,false})
                .isAlphaGradient(true)
                .setItemVisibleCount(7)
                .isDialog(true)
                .setDate(date)
                .isCyclic(true)
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        String change_time_text = c.get(Calendar.HOUR_OF_DAY)+"时"+c.get(Calendar.MINUTE)+"分";
                        //btn_show_time.setText(change_time_text);
                    }
                })
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        time_picker = time_builder.build();
        Dialog tDialog = time_picker.getDialog();
        if (tDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            time_picker.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = tDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }

}
