package com.example.accountbook.pickers;

import android.app.Dialog;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.accountbook.R;
import com.example.accountbook.helper.PickerDataHelper;

public class AddCategoryDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private EditText edit_first;
    private EditText edit_second;
    private LinearLayout layout_first;
    private TextView txt_title;
    private TextView txt_sure;
    private TextView txt_cancel;
    private String name;
    private String type;
    private String title;
    private boolean first_show;
    private final String defaultCategory1 = "所有";
    private PickerDataHelper pickerDataHelper = new PickerDataHelper();
    public AddCategoryDialog(@NonNull Context context,String type,String title) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.type = type;
        this.title = title;
        initView();
        initDialogContent();
    }

    private void initDialogContent() {
        switch (title){
            case "账户":
                name = PickerDataHelper.ACCOUNT;
                first_show = true;
                break;
            case "商家":
                name = PickerDataHelper.STORE;
                first_show = false;
                break;
            case "成员":
                name = PickerDataHelper.PEOPLE;
                first_show = false;
                break;
            case "项目":
                name = PickerDataHelper.PROJECT;
                first_show = false;
                break;
            case "放贷人":
                name = PickerDataHelper.LENDER;
                first_show = false;
                break;
            case "分类":
                if(type.equals("INCOME")){
                    name = PickerDataHelper.INCOME_CATEGORY;
                }else{
                    name = PickerDataHelper.PAY_CATEGORY;
                }
                first_show = true;
                break;
        }
        if(!first_show)
            layout_first.setVisibility(View.GONE);
    }

    //初始化
    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.addcategorydialog, null);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        edit_first = (EditText) view.findViewById(R.id.edit_first);
        edit_second = (EditText) view.findViewById(R.id.edit_second);
        layout_first = (LinearLayout) view.findViewById(R.id.layout_first);
        txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_sure = (TextView) view.findViewById(R.id.txt_sure);
        txt_cancel.setOnClickListener(this);
        txt_sure.setOnClickListener(this);
        super.setContentView(view);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_cancel:
                dismiss();
                break;
            case R.id.txt_sure:
                String cate1,cate2;
                if(first_show)
                    cate1 = edit_first.getText().toString();
                else
                    cate1 = defaultCategory1;
                cate2 = edit_second.getText().toString();
                if(cate1.length()==0)
                    Toast.makeText(mContext,"一级分类不能为空",Toast.LENGTH_SHORT).show();
                else if(cate2.length()==0)
                    Toast.makeText(mContext,"二级分类不能为空",Toast.LENGTH_SHORT).show();
                else {
                    Log.d("addCategory","add "+name+" "+cate1+" "+cate2);
                    pickerDataHelper.addCategory(name, cate1, cate2);
                    Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
                break;
            default:
                break;
        }
    }
}