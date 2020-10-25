package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.accountbook.password.FingerprintFragment;
import com.example.accountbook.password.PatternFragment;
import com.example.accountbook.password.TextFragment;

public class PasswordConfirm extends AppCompatActivity implements View.OnClickListener{
    private TextView txt_select_text_code;
    private TextView txt_select_graph_code;
    private TextView txt_select_fingerprint_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_confirm);
        // 指定控件并设置响应事件
        txt_select_text_code = (TextView)findViewById(R.id.txt_select_text_code);
        txt_select_graph_code = (TextView)findViewById(R.id.txt_select_graph_code);
        txt_select_fingerprint_code = (TextView)findViewById(R.id.txt_select_fingerprint_code);
        txt_select_text_code.setOnClickListener(this);
        txt_select_graph_code.setOnClickListener(this);
        txt_select_fingerprint_code.setOnClickListener(this);
        changeColor(1);
        replaceFragment(new TextFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_select_text_code:
                replaceFragment(new TextFragment());
                changeColor(1);
                break;
            case R.id.txt_select_graph_code:
                replaceFragment(new PatternFragment());
                changeColor(2);
                break;
            case R.id.txt_select_fingerprint_code:
                replaceFragment(new FingerprintFragment());
                changeColor(3);
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