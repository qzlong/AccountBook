package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.accountbook.bean.Detail;
import com.example.accountbook.helper.PickerDataHelper;
import com.example.accountbook.helper.TextSplitter;
import com.example.accountbook.pickers.DatePicker;
import com.example.accountbook.pickers.OptionsPicker;
import com.example.accountbook.pickers.TimePicker;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BillDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private final String Type = "PAY";

    private Button btn_show_time;
    private Button btn_show_account;
    private Button btn_show_store;
    private Button btn_show_date;
    private Button btn_show_member;
    private Button btn_show_project;
    private Button btn_show_category;
    private Button btn_save_as_model;
    private Button btn_save;
    private Button btn_go_back;
    private EditText edit_money;
    private EditText edit_remark;

    private Context mContext;
    private TimePickerView time_picker;
    private TimePickerView date_picker;
    private OptionsPickerView category_picker,account_picker,project_picker,store_picker,people_picker;
    private Window window;
    private TimePicker timePicker= new TimePicker();
    private DatePicker datePicker = new DatePicker();
    ArrayList<String> category_1;
    ArrayList<ArrayList<String>> category_2;
    ArrayList<String> account_1;
    ArrayList<ArrayList<String>> account_2;
    ArrayList<String> store_1;
    ArrayList<ArrayList<String>> store_2;
    ArrayList<String> member_1;
    ArrayList<ArrayList<String>> member_2;
    ArrayList<String> project_1;
    ArrayList<ArrayList<String>> project_2;
    private long bill_id;       //当前账单的id
    private Detail detail;      //修改后的账单
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        mContext = this;
        window = getWindow();
        intent = getIntent();
        bill_id = intent.getLongExtra("id", 0);
        detail = LitePal.find(Detail.class, bill_id);
        initPickerData();
        InitView();
        setBtnListener();
        initPickerView(mContext);
        setDefaultValue();
    }
    private void InitView() {
        btn_go_back = (Button) findViewById(R.id.goback);
        btn_show_time = (Button) findViewById(R.id.btn_show_time);
        btn_show_account = (Button) findViewById(R.id.btn_show_account);
        btn_show_date = (Button) findViewById(R.id.btn_show_date);
        btn_show_member = (Button) findViewById(R.id.btn_show_member);
        btn_show_project = (Button) findViewById(R.id.btn_show_project);
        btn_show_store = (Button) findViewById(R.id.btn_show_store);
        btn_show_category = (Button) findViewById(R.id.btn_show_category);
        btn_save = (Button) findViewById(R.id.btn_save);
        edit_money = (EditText) findViewById(R.id.edit_money);
        edit_remark = (EditText) findViewById(R.id.edit_remark);
    }

    @SuppressLint("SetTextI18n")
    private void setDefaultValue() {
        edit_money.setText(detail.getMoney() + "");
        btn_show_category.setText(detail.getCategory1() + "->" + detail.getCategory2());
        btn_show_account.setText(detail.getAccount1() + "->" + detail.getAccount2());
        Calendar date = detail.getTime();
        String time_text = date.get(Calendar.HOUR_OF_DAY) + "时" + date.get(Calendar.MINUTE) + "分";
        String date_text = date.get(Calendar.YEAR) + "年" + date.get(Calendar.MONTH) +"月" +date.get(Calendar.DAY_OF_MONTH) +"日";
        btn_show_time.setText(time_text);
        btn_show_date.setText(date_text);
        edit_remark.setText(detail.getNote());
        if(!detail.getTrader().equals("无商家"))
            btn_show_store.setText("所有->"+detail.getTrader());
        if(!detail.getMember().equals("无成员"))
            btn_show_member.setText("所有->"+detail.getMember());
        if(!detail.getProject().equals("无项目"))
            btn_show_project.setText("所有->"+detail.getProject());
        if(detail.getNote()!=null||detail.getNote().length()!=0)
            edit_remark.setText(detail.getNote());
    }
    private String getDefaultValue(ArrayList array1,ArrayList<ArrayList<String>> array2){
        return array1.get(1)+"->"+array2.get(1).get(0);
    }
    private void initPickerData() {
        PickerDataHelper pickerDataHelper = new PickerDataHelper();
        category_1 = pickerDataHelper.findCategory1(PickerDataHelper.PAY_CATEGORY);
        category_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.PAY_CATEGORY);
        account_1 = pickerDataHelper.findCategory1(PickerDataHelper.ACCOUNT);
        account_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.ACCOUNT);
        store_1 = pickerDataHelper.findCategory1(PickerDataHelper.STORE);
        store_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.STORE);
        member_1 = pickerDataHelper.findCategory1(PickerDataHelper.PEOPLE);
        member_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.PEOPLE);
        project_1 = pickerDataHelper.findCategory1(PickerDataHelper.PROJECT);
        project_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.PROJECT);
    }

    private void initPickerView(Context mContext) {
        time_picker = timePicker.getTimePicker(mContext,btn_show_time);
        date_picker = datePicker.getDatePicker(mContext,btn_show_date);
        category_picker = new OptionsPicker().getOptionsPickerView(mContext,"分类",btn_show_category,category_1,category_2,window,Type);
        account_picker = new OptionsPicker().getOptionsPickerView(mContext,"账户",btn_show_account,account_1,account_2,window,Type);
        store_picker = new OptionsPicker().getOptionsPickerView(mContext,"商家",btn_show_store,store_1,store_2,window,Type);
        people_picker = new OptionsPicker().getOptionsPickerView(mContext,"成员",btn_show_member,member_1,member_2,window,Type);
        project_picker = new OptionsPicker().getOptionsPickerView(mContext,"项目",btn_show_project,project_1,project_2,window,Type);
    }

    private void setBtnListener(){
        btn_show_time.setOnClickListener(this);
        btn_show_account.setOnClickListener(this);
        btn_show_date.setOnClickListener(this);
        btn_show_member.setOnClickListener(this);
        btn_show_project.setOnClickListener(this);
        btn_show_store.setOnClickListener(this);
        btn_show_category.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    public String getType(){
        return this.Type;
    }
//    private String getDefaultValue(ArrayList array1,ArrayList<ArrayList<String>> array2){
//        return array1.get(1)+"->"+array2.get(1).get(0);
//    }
    private boolean saveBill(){
        ArrayList<String> temp = new ArrayList<>();
        TextSplitter textSplitter = new TextSplitter();
        //Log.d("test", "saveBill: 1");
        //1.设置账单类型
        detail.setType(Type);
        //Log.d("test", "saveBill: 2");
        //2.设置备注
        detail.setNote(edit_remark.getText().toString());
        //Log.d("test", "saveBill: 3");
        //3.设置时间和日期
        Calendar Date = datePicker.getTime();
        Calendar Time = timePicker.getTime();
        Time.set(Calendar.YEAR,Date.get(Calendar.YEAR));
        Time.set(Calendar.MONTH,Date.get(Calendar.MONTH));
        Time.set(Calendar.DAY_OF_MONTH,Date.get(Calendar.DAY_OF_MONTH));
//        Log.d("test", "saveBill: t");
        detail.setTime(Time);
//        Log.d("test", "saveBill: 4");
        //4.设置分类
        temp = textSplitter.splitArrow(btn_show_category.getText().toString());
        detail.setCategory(temp.get(0),temp.get(1));
//        Log.d("test", "saveBill: 5");

        //5.设置账户
        temp = textSplitter.splitArrow(btn_show_account.getText().toString());
        detail.setAccount(temp.get(0),temp.get(1));
//        Log.d("test", "saveBill: 6");
        //6.设置商家
        temp = textSplitter.splitArrow(btn_show_store.getText().toString());
        detail.setTrader(temp.get(1));
//        Log.d("test", "saveBill: 7");
        //7.设置成员
        temp = textSplitter.splitArrow(btn_show_member.getText().toString());
        detail.setMember(temp.get(1));
        //8.设置项目
        temp = textSplitter.splitArrow(btn_show_project.getText().toString());
        detail.setProject(temp.get(1));
//        Log.d("test", "saveBill: 8");
        //9.设置金额
        try {
            float money = Float.parseFloat(edit_money.getEditableText().toString().trim());
            if(Math.abs(money-0)<=10e-6){
                Toast.makeText(mContext,"金额不能为0",Toast.LENGTH_SHORT).show();
                return false;
            }
            if(money<0){
                Toast.makeText(mContext,"金额不能小于0",Toast.LENGTH_SHORT).show();
                return false;
            }
            detail.setMoney(money);
        }catch (Exception e){
            Toast.makeText(mContext,"请输入合法数字",Toast.LENGTH_SHORT).show();
            return false;
        }
        //10.保存至数据库
        detail.save();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goback:
                setResult(1, intent);
                finish();
                break;
            case R.id.btn_show_time:
                time_picker.show();
                break;
            case R.id.btn_show_date:
                date_picker.show();
                break;
            case R.id.btn_show_category:
                category_picker.show();
                break;
            case R.id.btn_show_account:
                account_picker.show();
                break;
            case R.id.btn_show_store:
                store_picker.show();
                break;
            case R.id.btn_show_member:
                people_picker.show();
                break;
            case R.id.btn_show_project:
                project_picker.show();
                break;
            case R.id.btn_save:
                saveBill();
                Toast.makeText(mContext,"账单修改成功",Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }


}