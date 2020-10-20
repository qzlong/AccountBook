package com.example.accountbook;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.accountbook.pickers.DatePicker;
import com.example.accountbook.pickers.OptionsPicker;
import com.example.accountbook.pickers.TimePicker;

import java.util.ArrayList;

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
        initPickerData();
        initView(view);
        setBtnListener();
        initPickerView(mContext);
        return view;
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
        time_picker = new TimePicker().getTimePicker(mContext,btn_show_time);
        date_picker = new DatePicker().getDatePicker(mContext,btn_show_date);
        lender_picker = new OptionsPicker().getOptionsPickerView(mContext,"放贷人",btn_show_lender,lender_1,lender_2);
        account_picker = new OptionsPicker().getOptionsPickerView(mContext,"账户",btn_show_account,account_1,account_2);
        store_picker = new OptionsPicker().getOptionsPickerView(mContext,"商家",btn_show_store,store_1,store_2);
        people_picker = new OptionsPicker().getOptionsPickerView(mContext,"成员",btn_show_member,member_1,member_2);
        project_picker = new OptionsPicker().getOptionsPickerView(mContext,"项目",btn_show_project,project_1,project_2);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show_time:
                time_picker.show();
                break;
            case R.id.btn_show_date:
                date_picker.show();
                break;
            case R.id.btn_show_lender:
                lender_picker.show();
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
            case R.id.btn_save_as_model:
                break;
            case R.id.btn_save:
                break;
            default:
                break;
        }
    }
}