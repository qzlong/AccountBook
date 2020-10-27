package com.example.accountbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.accountbook.bean.Detail;
import com.example.accountbook.bean.Model;
import com.example.accountbook.setting.Option;
import com.example.accountbook.adapter.OptionAdapter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,SharedPreferences.OnSharedPreferenceChangeListener{
    //主页面
    private DrawerLayout mDrawerLayout;
    private Button btn_graphanalysis;
    private Button btn_insititute;
    private Button btn_setting;
    private Button btn_keepaccount;
    private List<Option> optionList = new ArrayList<>();
    private SharedPreferences setting_pref;
    //设置页面

    //summary
    TextView remain_all;
    TextView income_all;
    TextView expend_all;
    //summary week
    TextView week_time;
    TextView reamain_week;
    TextView income_week;
    TextView expend_week;
    //summary month
    TextView month_time;
    TextView reamain_month;
    TextView income_month;
    TextView expend_month;
    //summary season
    TextView season_time;
    TextView reamain_season;
    TextView income_season;
    TextView expend_season;
    //summary year
    TextView year_time;
    TextView reamain_year;
    TextView income_year;
    TextView expend_year;
    //date
    private final Calendar current_date = Calendar.getInstance();
    private final int current_month = current_date.get(Calendar.MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setting_pref = getSharedPreferences("setting",MODE_PRIVATE);
        boolean isSetCode = isSetCode();
        if(isSetCode)
            startActivity(new Intent(MainActivity.this,PasswordConfirm.class));

        initView();
        initOptions();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        OptionAdapter adapter = new OptionAdapter(optionList,this,MainActivity.this);
        recyclerView.setAdapter(adapter);
        setButtonListener();
        SharedPreferences sharedPreferences = this.getSharedPreferences("first_launch",MODE_PRIVATE);

        //第一次运行时加载默认分类数据
        boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch",true);
        if(isFirstLaunch) {
            LitePal.initialize(this);
            SQLiteDatabase db = LitePal.getDatabase();
            FirstLaunch firstLaunch = new FirstLaunch();
            firstLaunch.initPickerData();
            SharedPreferences.Editor editor = getSharedPreferences("first_launch",MODE_PRIVATE).edit();
            editor.putBoolean("isFirstLaunch",false);
            editor.apply();
        }

        bindView();
    }

    private boolean isSetCode() {
        boolean isSetTextCode = setting_pref.getBoolean("isSetTextCode",false);
        boolean isSetPatternCode = setting_pref.getBoolean("isSetPatternCode",false);
        boolean isSetFingerprintCode = setting_pref.getBoolean("isEnableFingerprintCode",false);
        return isSetFingerprintCode|isSetPatternCode|isSetTextCode;
    }

    private void initOptions() {
//        optionList.clear();
//        Option option1 = new Option(" ","    定时任务");
//        optionList.add(option1);
//        Option option2 = new Option("   自动记账","    定时自动添加账单");
//        optionList.add(option2);
//        Option option3 = new Option("   记账提醒","    设置记账提醒");
//        optionList.add(option3);
//        Option option4 = new Option("   自动备份","    自动备份数据");
//        optionList.add(option4);
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
        boolean isSetEmailAddress = setting_pref.getBoolean("isSetEmailAddress",false);
        Option option10;
        if(!isSetEmailAddress)
            option10 = new Option("   绑定邮箱","    绑定邮箱用于导出用户数据");
        else
            option10 = new Option("   "+setting_pref.getString("emailAddress",""),"    解除邮箱绑定");
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
        remain_all = findViewById(R.id.statis_summary_remain);
        income_all = findViewById(R.id.statis_summary_income);
        expend_all = findViewById(R.id.statis_summary_expend);
        week_time = findViewById(R.id.week_time);
        month_time = findViewById(R.id.month_time);
        season_time = findViewById(R.id.quarter_time);
        year_time = findViewById(R.id.year_time);
        reamain_week = findViewById(R.id.week_balance_value);
        reamain_month = findViewById(R.id.month_balance_value);
        reamain_season = findViewById(R.id.quarter_balance_value);
        reamain_year = findViewById(R.id.year_balance_value);
        income_week = findViewById(R.id.week_income_value);
        income_month = findViewById(R.id.month_income_value);
        income_season = findViewById(R.id.quarter_income_value);
        income_year = findViewById(R.id.year_income_value);
        expend_week = findViewById(R.id.week_expenditure_value);
        expend_month = findViewById(R.id.month_expenditure_value);
        expend_season = findViewById(R.id.quarter_expenditure_value);
        expend_year = findViewById(R.id.year_expenditure_value);
    }

    @SuppressLint("SetTextI18n")
    private void bindView() {
        final List<Detail> bill_list = LitePal.findAll(Detail.class);
        getSummary(expend_all, income_all, remain_all, bill_list);

        Calendar month_start = (Calendar) current_date.clone();
        Calendar season_start  = (Calendar) current_date.clone();
        Calendar week_start = (Calendar) current_date.clone();
        Calendar year_start = (Calendar) current_date.clone();
        month_start.set(Calendar.DAY_OF_MONTH, 0);
        week_start.add(Calendar.DAY_OF_MONTH, -7);
        year_start.set(Calendar.MONTH, 0);
        switch (current_month) {
            case 0:
            case 1:
            case 2:
                season_start.set(Calendar.MONTH, 0);
                season_time.setText("第1季度");
                break;
            case 3:
            case 4:
            case 5:
                season_start.set(Calendar.MONTH, 3);
                season_time.setText("第2季度");
                break;
            case 6:
            case 7:
            case 8:
                season_start.set(Calendar.MONTH, 6);
                season_time.setText("第3季度");
                break;
            case 9:
            case 10:
            case 11:
                season_start.set(Calendar.MONTH, 9);
                season_time.setText("第4季度");
                break;
        }

        String weekTimeText = (week_start.get(Calendar.MONTH)+1) + "月" + week_start.get(Calendar.DAY_OF_MONTH) + "--" +
                (current_date.get(Calendar.MONTH)+1) + "月" + current_date.get(Calendar.DAY_OF_MONTH) + "日";
        week_time.setText(weekTimeText);
        getSummary(expend_week, income_week, reamain_week, getDataByTime(week_start, current_date, bill_list));

        month_time.setText(current_date.get(Calendar.MONTH) + "月");
        getSummary(expend_month, income_month, reamain_month, getDataByTime(month_start, current_date, bill_list));

        getSummary(expend_season, income_season, reamain_season, getDataByTime(season_start, current_date, bill_list));

        year_time.setText(current_date.get(Calendar.YEAR) + "");
        getSummary(expend_year, income_year, reamain_year, getDataByTime(year_start, current_date, bill_list));
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
                intent = new Intent(MainActivity.this,StatisticsActivity.class);
                startActivityForResult(intent, 1);
                return;
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        initOptions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                bindView();
            }
        }
    }

    private void getSummary(TextView expendView, TextView incomeView, TextView remainView, List<Detail> list) {
        float expend = getSumExpend(list);
        expendView.setText(expend + "");
        float income = getSumIncome(list);
        incomeView.setText(income + "");
        float remain = income - expend;
        remainView.setText(remain + "");
    }

    /**
     * @param list
     * @return 获取指定账单列表收入之和
     */
    private float getSumIncome(List<Detail> list) {
        float sum = 0;
        for (Detail bill : list) {
            if (bill.getType().equals("INCOME")) {
                sum += bill.getMoney();
            }
        }
        return sum;
    }
    /**
     * @param list
     * @return 获取指定账单列表支出之和
     */
    private float getSumExpend(List<Detail> list) {
        float sum = 0;
        for (Detail bill : list) {
            if (bill.getType().equals("PAY")) {
                sum += bill.getMoney();
            }
        }
        return sum;
    }

    private List<Detail> getDataByTime(Calendar startTime, Calendar endTime, List<Detail> list) {
        List<Detail> data = new ArrayList<>();
        for (Detail bill : list) {
            Calendar date = bill.getTime();
            if (date.after(startTime)) {
                if (date.before(endTime)) {
                    data.add(bill);
                }
            }
        }
        return data;
    }
}