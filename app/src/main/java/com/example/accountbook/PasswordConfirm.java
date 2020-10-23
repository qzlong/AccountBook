package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PasswordConfirm extends AppCompatActivity implements View.OnClickListener{
    private TextView mytext;
    private TextView pattern;
    private TextView fingerprint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_confirm);
        // 指定控件并设置响应事件
        mytext = (TextView)findViewById(R.id.mytext);
        pattern = (TextView)findViewById(R.id.pattern);
        fingerprint = (TextView)findViewById(R.id.fingerprint);
        mytext.setOnClickListener(this);
        pattern.setOnClickListener(this);
        fingerprint.setOnClickListener(this);
        changeColor(1);
        replaceFragment(new TextFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mytext:
                replaceFragment(new TextFragment());
                changeColor(1);
                break;
            case R.id.pattern:
                replaceFragment(new PatternFragment());
                changeColor(2);
                break;
            case R.id.fingerprint:
                replaceFragment(new FingerprintFragment());
                changeColor(3);
                break;
            default:
        }
    }
    private void changeColor(int i){
        switch (i){
            case 1:
                mytext.setTextColor(0xFFCF8312);
                pattern.setTextColor(0xFF000000);
                fingerprint.setTextColor(0xFF000000);
                break;
            case 2:
                mytext.setTextColor(0xFF000000);
                pattern.setTextColor(0xFFCF8312);
                fingerprint.setTextColor(0xFF000000);
                break;
            case 3:
                mytext.setTextColor(0xFF000000);
                pattern.setTextColor(0xFF000000);
                fingerprint.setTextColor(0xFFCF8312);
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
}