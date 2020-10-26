package com.example.accountbook.setting;

import android.app.Dialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.accountbook.R;

public class AddTextCodeDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private EditText edit_first;
    private EditText edit_second;
    private TextView txt_title;
    private TextView txt_sure;
    private TextView txt_cancel;
    private TextView txt_warn;
    private String password_return;
    private boolean isSetSuccessful = false;

    public AddTextCodeDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        initView();
    }

    //初始化
    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.add_text_code_dialog, null);
        txt_title = view.findViewById(R.id.txt_title);

        edit_first = view.findViewById(R.id.edit_first);
        edit_second = view.findViewById(R.id.edit_second);
        txt_warn = view.findViewById(R.id.txt_warn);
        txt_cancel = view.findViewById(R.id.txt_cancel);
        txt_sure = view.findViewById(R.id.txt_sure);
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
                String password = edit_first.getText().toString();
                String password_confirm = edit_second.getText().toString();
                if(password.length()==0)
                    txt_warn.setText("密码不能为空");
                else if(password.length()<6)
                    txt_warn.setText("密码至少有6位");
                else if(!password.equals(password_confirm))
                    txt_warn.setText("两次密码不一致");
                else{
                    password_return = password;
                    isSetSuccessful = true;
                    dismiss();
                }
                txt_warn.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
    public boolean isSetSuccessful(){
        return this.isSetSuccessful;
    }
    public String getPassword(){
        return this.password_return;
    }
}