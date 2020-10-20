package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private DrawerLayout mDrawerLayout;
    private Button btn_graphanalysis;
    private Button btn_insititute;
    private Button btn_setting;
    private Button btn_keepaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setButtonListener();
        SharedPreferences sharedPreferences = this.getSharedPreferences("first_launch",MODE_PRIVATE);
        boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch",true);
        //if(isFirstLaunch) {
            LitePal.initialize(this);
            SQLiteDatabase db = LitePal.getDatabase();
            FirstLaunch firstLaunch = new FirstLaunch();
            firstLaunch.initPickerData();
            SharedPreferences.Editor editor = getSharedPreferences("first_launch",MODE_PRIVATE).edit();
            editor.putBoolean("isFirstLaunch",false);
            editor.apply();
        //}
    }
    private void initView() {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
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
    }
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_setting:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.btn_graph_analysis:
                intent = new Intent(MainActivity.this,SettingsActivity.class);
                break;
            case R.id.btn_institute:
                intent = new Intent(MainActivity.this,Institute.class);
                break;
            case R.id.btn_keep_account:
                intent = new Intent(MainActivity.this,KeepAccountActivity.class);
                break;
            default:
                break;
        }
        if(intent != null){
            startActivity(intent);
        }
    }
}