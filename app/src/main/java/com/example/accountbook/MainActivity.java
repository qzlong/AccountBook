package com.example.accountbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_selaccount;
    private Button btn_graphanalysis;
    private Button btn_insititute;
    private Button btn_setting;
    private Button btn_keepaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        initView();
        setButtonListener();
        //ListView
    }


    private void initView() {
        btn_selaccount = (Button) findViewById(R.id.btn_select_account);
        btn_graphanalysis = (Button) findViewById(R.id.btn_graph_analysis);
        btn_insititute = (Button) findViewById(R.id.btn_institute);
        btn_setting = (Button) findViewById(R.id.btn_setting);
        btn_keepaccount = (Button) findViewById(R.id.btn_keep_account);

    }

    private void setButtonListener() {
        btn_keepaccount.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        btn_insititute.setOnClickListener(this);
        btn_graphanalysis.setOnClickListener(this);
        btn_selaccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_setting:
                intent = new Intent(MainActivity.this,SettingsActivity.class);
                break;
            case R.id.btn_graph_analysis:
                intent = new Intent(MainActivity.this,ChartAnalysis.class);
                break;
            case R.id.btn_institute:
                intent = new Intent(MainActivity.this,Institute.class);
                break;
            case R.id.btn_select_account:
                intent = new Intent(MainActivity.this,SelectAccount.class);
                break;
            case R.id.btn_keep_account:
                intent = new Intent(MainActivity.this,KeepAccountActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}