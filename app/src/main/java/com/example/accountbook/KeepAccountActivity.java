package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class KeepAccountActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_show_time;
    private Button btn_show_date;
    private Button btn_show_category;
    private Button btn_show_account;
    private Button btn_show_project;
    private Button btn_show_store;
    private Button btn_show_people;
    private EditText edit_remark;
    private EditText edit_money;
    private TimePickerView date_picker,time_picker;
    private Calendar date = Calendar.getInstance();

    private ArrayList<String> category1 = new ArrayList<>();
    private ArrayList<ArrayList<String>> category2 = new ArrayList<>();
    private ArrayList<String> accout1 = new ArrayList<>();
    private ArrayList<ArrayList<String>> accout2 = new ArrayList<>();
    private ArrayList<String> project1 = new ArrayList<>();
    private ArrayList<ArrayList<String>> project2 = new ArrayList<>();
    private ArrayList<String> store1 = new ArrayList<>();
    private ArrayList<ArrayList<String>> store2 = new ArrayList<>();
    private ArrayList<String> people1 = new ArrayList<>();
    private ArrayList<ArrayList<String>> people2 = new ArrayList<>();
    private OptionsPickerView category_picker,account_picker,project_picker,store_picker,people_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_account);
        initView();
        initDate();
        initDatePicker();
        initTimePicker();
        initOptionsPicker();
        btnSetListener();
        changeBtnInitText();
    }

    private void initOptionsPicker() {
        account_picker = initOptionsPicker(account_picker,accout1,accout2,"账户",btn_show_account);
        category_picker = initOptionsPicker(category_picker,category1,category2,"账单类型",btn_show_category);
        project_picker = initOptionsPicker(project_picker,project1,project2,"项目",btn_show_project);
        people_picker = initOptionsPicker(people_picker,people1,people2,"成员",btn_show_people);
        store_picker = initOptionsPicker(store_picker,store1,store2,"商家",btn_show_store);
    }


    private void initDate() {
        initCategoryDate();
        initProjectDate();
        initAccountDate();
        initPeoPleDate();
        initStoreDate();
    }

    private OptionsPickerView initOptionsPicker(OptionsPickerView picker, final ArrayList array1, final ArrayList<ArrayList<String>> array2, String titlename, final Button button){
        OptionsPickerBuilder opbuilder = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx =array1.get(options1)+"->"+array2.get(options1).get(options2);
                button.setText(tx);
            }
        }).setTitleText(titlename)
                .setContentTextSize(20)
                .setSelectOptions(0,0)
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.LTGRAY)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(true)
                .isCenterLabel(false)
                .setOutSideColor(0x00000000)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String tx =array1.get(options1)+"->"+array2.get(options1).get(options2);
                        button.setText(tx);
                    }
                })
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(KeepAccountActivity.this,"Finish",Toast.LENGTH_LONG).show();
                    }
                });
        picker = opbuilder.build();
        picker.setPicker(array1,array2);
        return picker;
    }


    private void initProjectDate() {
        project1.add("最近使用");
        project1.add("所有");
        ArrayList<String> project2_01 = new ArrayList<>();
        project2_01.add("无");
        ArrayList<String> project2_02 = new ArrayList<>();
        project2_02.add("腐败");
        project2_02.add("装修");
        project2.add(project2_01);
        project2.add(project2_02);
    }
    private void initAccountDate() {
        accout1.add("最近使用");
        accout1.add("现金");
        accout1.add("信用卡");
        accout1.add("金融账户");
        ArrayList<String> accout2_01 = new ArrayList<>();
        accout2_01.add("无");
        ArrayList<String> accout2_02 = new ArrayList<>();
        accout2_02.add("现金");
        ArrayList<String> accout2_03 = new ArrayList<>();
        accout2_03.add("信用卡");
        ArrayList<String> accout2_04 = new ArrayList<>();
        accout2_04.add("银行卡");
        accout2.add(accout2_01);
        accout2.add(accout2_02);
        accout2.add(accout2_03);
        accout2.add(accout2_04);
    }
    private void initCategoryDate() {
        String[] strs={"最近使用","衣服饰品","食品酒水","学习进修",
                      "居家物业","行车交通","交流通讯","休闲娱乐",
                      "人情往来","医疗保健","金融保险","其他杂项"};
        for(String str:strs){
            category1.add(str);
        }
        ArrayList<String> category2_01 = new ArrayList<>();
        category2_01.add("无");
        ArrayList<String> category2_02 = new ArrayList<>();
        category2_02.add("衣服裤子");
        category2_02.add("鞋帽包包");
        ArrayList<String> category2_03 = new ArrayList<>();
        category2_03.add("早午晚餐");
        category2_03.add("烟酒茶");
        ArrayList<String> category2_04 = new ArrayList<>();
        category2_04.add("书报杂志");
        category2_04.add("培训进修");
        ArrayList<String> category2_05 = new ArrayList<>();
        category2_05.add("日常用品");
        category2_05.add("水电煤气");
        ArrayList<String> category2_06 = new ArrayList<>();
        category2_06.add("打车租车");
        category2_06.add("公共交通");
        ArrayList<String> category2_07 = new ArrayList<>();
        category2_07.add("座机费");
        category2_07.add("手机费");
        ArrayList<String> category2_08 = new ArrayList<>();
        category2_08.add("运动健身");
        category2_08.add("休闲娱乐");
        ArrayList<String> category2_09 = new ArrayList<>();
        category2_09.add("送礼请客");
        category2_09.add("孝敬家长");
        ArrayList<String> category2_10 = new ArrayList<>();
        category2_10.add("保健费");
        category2_10.add("美容费");
        ArrayList<String> category2_11 = new ArrayList<>();
        category2_11.add("银行手续");
        category2_11.add("按揭还款");
        ArrayList<String> category2_12 = new ArrayList<>();
        category2_12.add("意外丢失");
        category2_12.add("烂账损失");
        category2.add(category2_01);
        category2.add(category2_02);
        category2.add(category2_03);
        category2.add(category2_04);
        category2.add(category2_05);
        category2.add(category2_06);
        category2.add(category2_07);
        category2.add(category2_08);
        category2.add(category2_09);
        category2.add(category2_10);
        category2.add(category2_11);
        category2.add(category2_12);
    }
    private void initPeoPleDate(){
        people1.add("最近使用");
        people1.add("所有");
        ArrayList<String> people2_01 = new ArrayList<>();
        people2_01.add("无");
        ArrayList<String> people2_02 = new ArrayList<>();
        people2_02.add("本人");
        people2_02.add("男朋友");
        people2.add(people2_01);
        people2.add(people2_02);
    }
    private void initStoreDate(){
        store1.add("最近使用");
        store1.add("所有");
        store1.add("附近商家");
        ArrayList<String> store2_01 = new ArrayList<>();
        store2_01.add("无");
        ArrayList<String> store2_02 = new ArrayList<>();
        store2_02.add("其他");
        store2_02.add("饭堂");
        ArrayList<String> store2_03 = new ArrayList<>();
        store2_03.add("待实现");
        store2.add(store2_01);
        store2.add(store2_02);
        store2.add(store2_03);
    }
    private void changeBtnInitText() {
        String init_date_text = date.get(Calendar.YEAR)+"年"+(date.get(Calendar.MONTH)+1)+"月"+date.get(Calendar.DATE)+"日";
        btn_show_date.setText(init_date_text);
        String init_time_text = date.get(Calendar.HOUR_OF_DAY)+"时"+date.get(Calendar.MINUTE)+"分";
        btn_show_time.setText(init_time_text);
        String init_accout_text = accout1.get(0) + "->" +accout2.get(0);
        btn_show_account.setText(init_accout_text);
        String init_category_text = category1.get(0)+"->"+category2.get(0);
        btn_show_category.setText(init_category_text);
    }

    private void initTimePicker() {
        TimePickerBuilder time_builder = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                        btn_show_time.setText(change_time_text);
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

    private void initDatePicker() {
        TimePickerBuilder tpbuilder = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

            }
        }).setType(new boolean[]{true,true,true,false,false,false})
          .isAlphaGradient(true)
          .setItemVisibleCount(7)
          .isDialog(true)
          .setDate(date)
          .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
              @Override
              public void onTimeSelectChanged(Date date) {
                  Calendar c = Calendar.getInstance();
                  c.setTime(date);
                  String change_date_text = c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月"+c.get(Calendar.DATE)+"日";
                  btn_show_date.setText(change_date_text);
              }
          })
          .addOnCancelClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

              }
          });
        date_picker = tpbuilder.build();
        Dialog mDialog = date_picker.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            date_picker.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }

    private void initView() {
        btn_show_account = (Button) findViewById(R.id.btn_show_account);
        btn_show_category = (Button) findViewById(R.id.btn_show_category);
        btn_show_date = (Button) findViewById(R.id.btn_show_date);
        btn_show_people = (Button) findViewById(R.id.btn_show_people);
        btn_show_project = (Button) findViewById(R.id.btn_show_project);
        edit_remark = (EditText) findViewById(R.id.edit_remark);
        btn_show_store = (Button) findViewById(R.id.btn_show_store);
        btn_show_time = (Button) findViewById(R.id.btn_show_time);
        edit_money = (EditText) findViewById(R.id.edit_money);
    }

    private void btnSetListener() {
        btn_show_time.setOnClickListener(this);
        btn_show_store.setOnClickListener(this);
        edit_remark.setOnClickListener(this);
        btn_show_project.setOnClickListener(this);
        btn_show_category.setOnClickListener(this);
        btn_show_date.setOnClickListener(this);
        btn_show_account.setOnClickListener(this);
        btn_show_people.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show_date:
                date_picker.show(v);
                break;
            case R.id.btn_show_time:
                time_picker.show(v);
                break;
            case R.id.btn_show_category:
                category_picker.show(v);
                break;
            case R.id.btn_show_account:
                account_picker.show(v);
                break;
            case R.id.btn_show_project:
                project_picker.show(v);
                break;
            case R.id.btn_show_store:
                store_picker.show();
                break;
            case R.id.btn_show_people:
                people_picker.show();
                break;
            default:
                break;
        }
    }

}