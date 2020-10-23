package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.accountbook.adapter.OptionAdapter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private DrawerLayout mDrawerLayout;
    private Button btn_graphanalysis;
    private Button btn_insititute;
    private Button btn_setting;
    private Button btn_keepaccount;
    private List<Option> optionList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initOptions();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        OptionAdapter adapter = new OptionAdapter(optionList);
        recyclerView.setAdapter(adapter);
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

    private void initOptions() {

        optionList.clear();
        Option option1 = new Option(" ","    定时任务");
        optionList.add(option1);
        Option option2 = new Option("   自动记账","    定时自动添加账单");
        optionList.add(option2);
        Option option3 = new Option("   记账提醒","    设置记账提醒");
        optionList.add(option3);
        Option option4 = new Option("   自动备份","    自动备份数据");
        optionList.add(option4);
        Option option5 = new Option(" ","    密码安全");
        optionList.add(option5);
        Option option6 = new Option("   指纹密码","    打开/关闭使用指纹密码");
        optionList.add(option6);
        Option option7 = new Option("   文本密码","    设置/更改文本密码");
        optionList.add(option7);
        Option option8 = new Option("   图形密码","    设置/更改文本密码");
        optionList.add(option8);
        Option option9 = new Option(" ","    数据服务");
        optionList.add(option9);
        Option option10 = new Option("   绑定邮箱","    绑定邮箱用于导出用户数据");
        optionList.add(option10);
        Option option11 = new Option("   数据导出","    将账单信息以CSV文件格式导出到绑定邮箱");
        optionList.add(option11);
        Option option12 = new Option("   数据导入","    以CSV文件格式将账单信息导入APP");
        optionList.add(option12);
        Option option13 = new Option(" ","    其他设置");
        optionList.add(option13);
        Option option14 = new Option("   清空账单","    删除所有账单信息");
        optionList.add(option14);
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
                intent = new Intent(MainActivity.this,ChartAnalysisActivity.class);
                break;
            case R.id.btn_institute:
                intent = new Intent(MainActivity.this, StatisticsActivity.class);
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