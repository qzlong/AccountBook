package com.example.accountbook.setting;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.accountbook.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomEditDialog extends Dialog implements View.OnClickListener{
    Context mContext;
    private TextView btn_sure;
    private TextView btn_cancel;
    private TextView title;
    private EditText edit_mail;
    private EditText edit_code;
    private Button btn_send_code;
    private String code = null;
    private String email_address = null;

    public CustomEditDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        initView();
    }

    //初始化
    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.customeditdialog, null);
        title = (TextView) view.findViewById(R.id.title);
        edit_mail = (EditText) view.findViewById(R.id.edit_email_address);
        btn_send_code = (Button) view.findViewById(R.id.btn_send_code);
        edit_code = (EditText) view.findViewById(R.id.edit_code);
        btn_sure = (TextView) view.findViewById(R.id.dialog_confirm_sure);
        btn_cancel = (TextView) view.findViewById(R.id.dialog_confirm_cancel);
        btn_send_code.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        super.setContentView(view);
    }


    public CustomEditDialog setTile(String s) {
        title.setText(s);
        return this;
    }

    //获取当前输入框对象
    public View getEmail() {
        return edit_mail;
    }

    public View getCode(){
        return edit_code;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_confirm_sure:
                if(code == null){
                    Toast.makeText(mContext,"请先发送验证码",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String user_code = edit_code.getText().toString();
                    if(user_code.equals(code)){
                        //TODO
                        Toast.makeText(mContext,"绑定成功",Toast.LENGTH_SHORT).show();
                        dismiss();
                    }else{
                        Log.d("test","code="+code);
                        Log.d("test","user_code="+user_code);
                        Toast.makeText(mContext,"验证码错误",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.dialog_confirm_cancel:
                dismiss();
                break;
            case R.id.btn_send_code:
                email_address = edit_mail.getText().toString();
                if(isEmail(email_address)){
                    btn_send_code.setClickable(false);
                    //btn_send_code.setBackgroundColor(Color.GRAY);
                    btn_send_code.setBackgroundResource(R.drawable.dialog_btn_gray);
                    btn_send_code.setTextColor(Color.WHITE);
                    CodeHelper codeHelper = new CodeHelper();
                    code = codeHelper.generateCode();
                    EmailSupporterThread emailSupporterThread = new EmailSupporterThread(mContext,email_address,code);
                    emailSupporterThread.start();
                }else{
                    Toast.makeText(mContext,"邮件地址不合法",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    //判断Email合法性
    public static boolean isEmail(String email) {
        if (email == null)
            return false;
        String rule = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(email);
        if (matcher.matches())
            return true;
        else
            return false;
    }
}