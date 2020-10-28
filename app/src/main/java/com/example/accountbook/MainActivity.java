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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.accountbook.bean.Detail;
import com.example.accountbook.bean.mCalendar;
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
    TextView title_all;
    TextView income_all;
    TextView expend_all;
    //summary today
    TextView today_describe;
    TextView income_today;
    TextView expend_today;
    //summary week
    TextView week_time;
    TextView income_week;
    TextView expend_week;
    //summary month
    TextView month_time;
    TextView income_month;
    TextView expend_month;
    //summary year
    TextView year_time;
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
        title_all = findViewById(R.id.statis_summary_remain_title);
        income_all = findViewById(R.id.statis_summary_income);
        expend_all = findViewById(R.id.statis_summary_expend);
        week_time = findViewById(R.id.week_time);
        month_time = findViewById(R.id.month_time);
        today_describe = findViewById(R.id.day_describe);
        year_time = findViewById(R.id.year_time);
        income_week = findViewById(R.id.week_income_value);
        income_month = findViewById(R.id.month_income_value);
        income_today = findViewById(R.id.day_income_value);
        income_year = findViewById(R.id.year_income_value);
        expend_week = findViewById(R.id.week_expenditure_value);
        expend_month = findViewById(R.id.month_expenditure_value);
        expend_today = findViewById(R.id.day_expenditure_value);
        expend_year = findViewById(R.id.year_expenditure_value);
    }

    @SuppressLint("SetTextI18n")
    private void bindView() {
        final List<Detail> bill_list = LitePal.findAll(Detail.class);


        mCalendar month_start = new mCalendar(current_date);
        mCalendar today_start  = new mCalendar(current_date);
        mCalendar week_start = new mCalendar(current_date);
        mCalendar year_start = new mCalendar(current_date);
        mCalendar current_date = new mCalendar(this.current_date);

        today_start.set(0, 0);
        month_start.set(1, 0, 0);
        week_start.set(week_start.getDayOfMonth() - 6, 0, 0);
        year_start.set(0, 1, 0, 0);
        current_date.set(23, 59);

        String weekTimeText = (week_start.getMonth()+1) + "月" + week_start.getDayOfMonth() + "日" + "--" +
                (current_date.getMonth()+1) + "月" + current_date.getDayOfMonth() + "日";
        week_time.setText(weekTimeText);
        getSummary(expend_week, income_week, getDataByTime(week_start, current_date, bill_list));
        getSummary(expend_all, income_all, getDataByTime(week_start, current_date, bill_list));

        month_time.setText(current_date.getMonth() + "月");
        getSummary(expend_month, income_month, getDataByTime(month_start, current_date, bill_list));

        if (bill_list.size() == 0) {
            today_describe.setText("今天还未记账");
        }
        else if (bill_list.get(bill_list.size()-1).getTime().get(Calendar.DAY_OF_MONTH) != current_date.getDayOfMonth()) {
            today_describe.setText("今天还未记账");
        } else {
            Detail today = bill_list.get(bill_list.size()-1);
            String type;
            switch (today.getType()){
                case "PAY" :
                    today_describe.setText("最近  " + "支出： " + today.getMoney());
                    break;
                case "INCOME" :
                    today_describe.setText("最近  " + "收入： " + today.getMoney());
                    break;
                case "LOAN" :
                    today_describe.setText("最近  " + "借贷： " + today.getMoney());
                    break;
                case "TRANSFER" :
                    today_describe.setText("最近  " + "转账： " + today.getMoney());
                    break;
                default:
                    today_describe.setText("");
            }
        }
        getSummary(expend_today, income_today, getDataByTime(today_start, current_date, bill_list));

        year_time.setText(current_date.getYear() + "");
        getSummary(expend_year, income_year, getDataByTime(year_start, current_date, bill_list));
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
                startActivityForResult(intent, 2);
                return;
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
                bindView();
        }
        else if (requestCode == 2) {
            bindView();
        }
    }

    private void getSummary(TextView expendView, TextView incomeView, List<Detail> list) {
        float expend = getSumExpend(list);
        expendView.setText(expend + "");
        float income = getSumIncome(list);
        incomeView.setText(income + "");
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

    private List<Detail> getDataByTime(mCalendar startTime, mCalendar endTime, List<Detail> list) {
        List<Detail> data = new ArrayList<>();
        for (Detail bill : list) {
            mCalendar date = new mCalendar(bill);
            if (startTime.before(date)) {
                if (date.before(endTime)) {
                    data.add(bill);
                }
            }
        }
        return data;
    }
}