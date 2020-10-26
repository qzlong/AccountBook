package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accountbook.password.FingerprintFragment;
import com.example.accountbook.password.PatternFragment;
import com.example.accountbook.password.TextFragment;

public class PasswordConfirm extends AppCompatActivity implements View.OnClickListener{
    private TextView txt_select_text_code;
    private TextView txt_select_graph_code;
    private TextView txt_select_fingerprint_code;
    SharedPreferences sharedPreferences;
    boolean isSetTextCode;
    boolean isSetPatternCode;
    boolean isSetFingerprintCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_confirm);
        // 指定控件并设置响应事件
        txt_select_text_code = findViewById(R.id.txt_select_text_code);
        txt_select_graph_code = findViewById(R.id.txt_select_graph_code);
        txt_select_fingerprint_code = findViewById(R.id.txt_select_fingerprint_code);
        txt_select_text_code.setOnClickListener(this);
        txt_select_graph_code.setOnClickListener(this);
        txt_select_fingerprint_code.setOnClickListener(this);
        //获取设置信息
        sharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        isSetTextCode = sharedPreferences.getBoolean("isSetTextCode",false);
        isSetFingerprintCode = sharedPreferences.getBoolean("isSetFingerprintCode",false);
        isSetPatternCode = sharedPreferences.getBoolean("isSetPatternCode",false);
        if(isSetFingerprintCode) {
            changeColor(3);
            replaceFragment(new FingerprintFragment());
        }else if(isSetTextCode){
            changeColor(1);
            replaceFragment(new TextFragment());
        }else{
            changeColor(2);
            replaceFragment(new PatternFragment());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_select_text_code:
                if(isSetTextCode) {
                    replaceFragment(new TextFragment());
                    changeColor(1);
                }else{
                    Toast.makeText(PasswordConfirm.this,"您未设置文字密码",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.txt_select_graph_code:
                if(isSetPatternCode) {
                    replaceFragment(new PatternFragment());
                    changeColor(2);
                }else{
                    Toast.makeText(PasswordConfirm.this,"您未设置图形密码",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.txt_select_fingerprint_code:
                if (isSetFingerprintCode) {
                    replaceFragment(new FingerprintFragment());
                    changeColor(3);
                }else {
                    Toast.makeText(PasswordConfirm.this,"您未授权使用指纹密码",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    private void changeColor(int i){
        switch (i){
            case 1:
                txt_select_text_code.setTextColor(0xFFCF8312);
                txt_select_graph_code.setTextColor(0xFF000000);
                txt_select_fingerprint_code.setTextColor(0xFF000000);
                break;
            case 2:
                txt_select_text_code.setTextColor(0xFF000000);
                txt_select_graph_code.setTextColor(0xFFCF8312);
                txt_select_fingerprint_code.setTextColor(0xFF000000);
                break;
            case 3:
                txt_select_text_code.setTextColor(0xFF000000);
                txt_select_graph_code.setTextColor(0xFF000000);
                txt_select_fingerprint_code.setTextColor(0xFFCF8312);
                break;
            default:
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.passwordFragment,fragment);
        transaction.commit();
    }
    //重写keyEvent，防止用户使用Back返回MainActivity
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            //do nothing
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}