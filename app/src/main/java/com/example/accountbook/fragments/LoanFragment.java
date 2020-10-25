package com.example.accountbook.fragments;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.accountbook.bean.Detail;
import com.example.accountbook.R;
import com.example.accountbook.bean.Model;
import com.example.accountbook.helper.TextSplitter;
import com.example.accountbook.pickers.DatePicker;
import com.example.accountbook.pickers.OptionsPicker;
import com.example.accountbook.helper.PickerDataHelper;
import com.example.accountbook.pickers.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class LoanFragment extends Fragment implements View.OnClickListener{
    private final String Type = "LOAN";

    private Button btn_show_time;
    private Button btn_show_account;
    private Button btn_show_store;
    private Button btn_show_date;
    private Button btn_show_member;
    private Button btn_show_project;
    private Button btn_show_lender;
    private Button btn_save_as_model;
    private Button btn_save;
    private EditText edit_money;
    private EditText edit_remark;

    private Context mContext;
    private TimePickerView time_picker;
    private TimePickerView date_picker;
    private OptionsPickerView lender_picker,account_picker,project_picker,store_picker,people_picker;
    private TimePicker timePicker= new TimePicker();
    private DatePicker datePicker = new DatePicker();
    private Detail detail = new Detail();
    private Window window;

    ArrayList<String> lender_1;
    ArrayList<ArrayList<String>> lender_2;
    ArrayList<String> account_1;
    ArrayList<ArrayList<String>> account_2;
    ArrayList<String> store_1;
    ArrayList<ArrayList<String>> store_2;
    ArrayList<String> member_1;
    ArrayList<ArrayList<String>> member_2;
    ArrayList<String> project_1;
    ArrayList<ArrayList<String>> project_2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.loan, container, false);
        mContext = getActivity();
        window = getActivity().getWindow();
        initPickerData();
        initView(view);
        setBtnListener();
        initPickerView(mContext);
        setDefaultValue();
        return view;
    }
    @SuppressLint("SetTextI18n")
    private void setDefaultValue() {
        btn_show_account.setText(getDefaultValue(account_1,account_2));
        btn_show_lender.setText(getDefaultValue(lender_1,lender_2));
        Calendar date = Calendar.getInstance();
        String time_text = date.get(Calendar.HOUR_OF_DAY) + "时" + date.get(Calendar.MINUTE) + "分";
        String date_text = date.get(Calendar.YEAR) + "年" + date.get(Calendar.MONTH) +"月" +date.get(Calendar.DAY_OF_MONTH) +"日";
        btn_show_time.setText(time_text);
        btn_show_date.setText(date_text);
        btn_show_member.setText("默认->无成员");
        btn_show_project.setText("默认->无项目");
        btn_show_store.setText("默认->无商家");
        Model model = (Model) getArguments().getParcelable("LOAN");
        if(model!=null){
            btn_show_lender.setText(model.getCategory1()+"->"+model.getCategory2());
            btn_show_account.setText(model.getAccount1()+"->"+model.getAccount2());
            String store = model.getTrader();
            String member = model.getMember();
            String project = model.getProject();
            String note = model.getNote();
            if(!store.equals("无商家"))
                btn_show_store.setText("所有->"+store);
            if(!member.equals("无成员"))
                btn_show_member.setText("所有->"+member);
            if(!project.equals("无项目"))
                btn_show_project.setText("所有->"+project);
            if(note!=null||note.length()!=0)
                edit_remark.setText(note);
        }
    }
    private String getDefaultValue(ArrayList array1,ArrayList<ArrayList<String>> array2){
        return array1.get(0)+"->"+array2.get(0).get(0);
    }
    private void initPickerData() {
        PickerDataHelper pickerDataHelper = new PickerDataHelper();
        lender_1 = pickerDataHelper.findCategory1(PickerDataHelper.LENDER);
        lender_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.LENDER);
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
        lender_picker = new OptionsPicker().getOptionsPickerView(mContext,"放贷人",btn_show_lender,lender_1,lender_2,window,Type);
        account_picker = new OptionsPicker().getOptionsPickerView(mContext,"账户",btn_show_account,account_1,account_2,window,Type);
        store_picker = new OptionsPicker().getOptionsPickerView(mContext,"商家",btn_show_store,store_1,store_2,window,Type);
        people_picker = new OptionsPicker().getOptionsPickerView(mContext,"成员",btn_show_member,member_1,member_2,window,Type);
        project_picker = new OptionsPicker().getOptionsPickerView(mContext,"项目",btn_show_project,project_1,project_2,window, Type);
    }
    private void initView(View view){
        btn_show_time = (Button) view.findViewById(R.id.btn_show_time);
        btn_show_account = (Button) view.findViewById(R.id.btn_show_account);
        btn_show_date = (Button) view.findViewById(R.id.btn_show_date);
        btn_show_member = (Button) view.findViewById(R.id.btn_show_member);
        btn_show_project = (Button) view.findViewById(R.id.btn_show_project);
        btn_show_store = (Button) view.findViewById(R.id.btn_show_store);
        btn_show_lender = (Button) view.findViewById(R.id.btn_show_lender);
        btn_save_as_model = (Button) view.findViewById(R.id.btn_save_as_model);
        btn_save = (Button) view.findViewById(R.id.btn_save);
        edit_money = (EditText) view.findViewById(R.id.edit_money);
        edit_remark = (EditText) view.findViewById(R.id.edit_remark);
    }
    private void setBtnListener(){
        btn_show_time.setOnClickListener(this);
        btn_show_account.setOnClickListener(this);
        btn_show_date.setOnClickListener(this);
        btn_show_member.setOnClickListener(this);
        btn_show_project.setOnClickListener(this);
        btn_show_store.setOnClickListener(this);
        btn_show_lender.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_save_as_model.setOnClickListener(this);
    }
    public String getType(){
        return this.Type;
    }
    private void saveBill(){
        ArrayList<String> temp = new ArrayList<>();
        TextSplitter textSplitter = new TextSplitter();
        Log.d("test", "saveBill: 1");
        //1.设置账单类型
        detail.setType(Type);
        Log.d("test", "saveBill: 2");
        //2.设置备注
        detail.setNote(edit_remark.getText().toString());
        Log.d("test", "saveBill: 3");
        //3.设置时间和日期
        Calendar Date = datePicker.getTime();
        Calendar Time = timePicker.getTime();
        Time.set(Calendar.YEAR,Date.get(Calendar.YEAR));
        Time.set(Calendar.MONTH,Date.get(Calendar.MONTH));
        Time.set(Calendar.DAY_OF_MONTH,Date.get(Calendar.DAY_OF_MONTH));
        Log.d("test", "saveBill: t");
        detail.setTime(Time);
        Log.d("test", "saveBill: 4");
        //4.设置分类
        temp = textSplitter.splitArrow(btn_show_lender.getText().toString());
        detail.setCategory(temp.get(0),temp.get(1));
        Log.d("test", "saveBill: 5");
        //5.设置金额
        detail.setMoney(Float.parseFloat(edit_money.getEditableText().toString().trim()));
        Log.d("test", "saveBill: 6");
        //6.设置账户
        temp = textSplitter.splitArrow(btn_show_account.getText().toString());
        detail.setAccount(temp.get(0),temp.get(1));
        Log.d("test", "saveBill: 7");
        //7.设置商家
        temp = textSplitter.splitArrow(btn_show_store.getText().toString());
        detail.setTrader(temp.get(1));
        Log.d("test", "saveBill: 8");
        //8.设置成员
        temp = textSplitter.splitArrow(btn_show_member.getText().toString());
        detail.setMember(temp.get(1));
        //9.设置项目
        temp = textSplitter.splitArrow(btn_show_project.getText().toString());
        detail.setProject(temp.get(1));
        Log.d("test", "saveBill: 9");
        //10.保存至数据库
        detail.save();
    }
    @Override
    public void onClick(View v) {
        PickerDataHelper pickerDataHelper = new PickerDataHelper();
        switch (v.getId()){
            case R.id.btn_show_time:
                time_picker.show();
                break;
            case R.id.btn_show_date:
                date_picker.show();
                break;
            case R.id.btn_show_lender:
                lender_picker.show();
                if(lender_picker.isUpdated()){
                    lender_1 = pickerDataHelper.findCategory1(PickerDataHelper.LENDER);
                    lender_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.LENDER);
                    lender_picker = new OptionsPicker().getOptionsPickerView(mContext,"放贷人",btn_show_lender,lender_1,lender_2,window,Type);
                }
                break;
            case R.id.btn_show_account:
                account_picker.show();
                if(account_picker.isUpdated()){
                    account_1 = pickerDataHelper.findCategory1(PickerDataHelper.ACCOUNT);
                    account_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.ACCOUNT);
                    account_picker = new OptionsPicker().getOptionsPickerView(mContext,"账户",btn_show_account,account_1,account_2,window,Type);
                }
                break;
            case R.id.btn_show_store:
                store_picker.show();
                if(store_picker.isUpdated()){
                    store_1 = pickerDataHelper.findCategory1(PickerDataHelper.STORE);
                    store_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.STORE);
                    store_picker = new OptionsPicker().getOptionsPickerView(mContext,"商家",btn_show_store,store_1,store_2,window,Type);
                }
                break;
            case R.id.btn_show_member:
                people_picker.show();
                if(people_picker.isUpdated()){
                    member_1 = pickerDataHelper.findCategory1(PickerDataHelper.PEOPLE);
                    member_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.PEOPLE);
                    people_picker = new OptionsPicker().getOptionsPickerView(mContext,"成员",btn_show_member,member_1,member_2,window,Type);
                }
                break;
            case R.id.btn_show_project:
                project_picker.show();
                if(project_picker.isUpdated()){
                    project_1 = pickerDataHelper.findCategory1(PickerDataHelper.PROJECT);
                    project_2 = pickerDataHelper.findAllCategory2(PickerDataHelper.PROJECT);
                    project_picker = new OptionsPicker().getOptionsPickerView(mContext,"项目",btn_show_project,project_1,project_2,window,Type);
                }
                break;
            case R.id.btn_save_as_model:
                Model model = new Model(detail);
                model.save();
                Toast.makeText(mContext,"已添加至模板",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_save:
                saveBill();
                Toast.makeText(mContext,"账单添加成功",Toast.LENGTH_SHORT).show();
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}