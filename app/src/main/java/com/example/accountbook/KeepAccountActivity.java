package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KeepAccountActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_go_back;
    private TextView text_template;//moban
    private TextView text_expenditure;//zhichu
    private TextView text_income;//
    private TextView text_transfer;//zhuanzhnang
    private TextView text_loan;//jiedai
    private ViewPager myViewPager;
    private List<Fragment> list;
    private TabFragmentPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_account);
        InitView();
    }
    private void InitView() {
        //指定控件id
        btn_go_back = (Button) findViewById(R.id.goback);
        text_template = (TextView)findViewById(R.id.text_template);
        text_expenditure = (TextView)findViewById(R.id.text_expenditure);
        text_income = (TextView)findViewById(R.id.text_income);
        text_transfer = (TextView)findViewById(R.id.text_transfer);
        text_loan = (TextView)findViewById(R.id.text_loan);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        //设置响应事件
        btn_go_back.setOnClickListener(this);
        text_template.setOnClickListener(this);
        text_expenditure.setOnClickListener(this);
        text_income.setOnClickListener(this);
        text_transfer.setOnClickListener(this);
        text_loan.setOnClickListener(this);
        myViewPager.addOnPageChangeListener(new MyPagerChangeListener()) ;
        //把Fragment添加到List集合里面
        list = new ArrayList<>();
        list.add(new TemplateFragment());
        list.add(new ExpenditureFragment());
        list.add(new IncomeFragment());
        list.add(new TransferFragment());
        list.add(new LoanFragment());
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(1);  //初始化显示第一个页面
        changeColor(1);//被选中就为红色
    }
    //第一次设置点击监听事件，为菜单栏设置监听事件，监听的对象是页面的滑动
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:
                finish();
                break;
            case R.id.text_template:
                myViewPager.setCurrentItem(0);
                changeColor(0);
                break;
            case R.id.text_expenditure:
                myViewPager.setCurrentItem(1);
                changeColor(1);
                break;
            case R.id.text_income:
                myViewPager.setCurrentItem(2);
                changeColor(2);
                break;
            case R.id.text_transfer:
                myViewPager.setCurrentItem(3);
                changeColor(3);
                break;
            case R.id.text_loan:
                myViewPager.setCurrentItem(4);
                changeColor(4);
                break;
            default:
        }
    }
    //第二次设置点击监听事件，为ViewPager设置监听事件，用于实现菜单栏的样式变化
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    changeColor(0);
                    break;
                case 1:
                    changeColor(1);
                    break;
                case 2:
                    changeColor(2);
                    break;
                case 3:
                    changeColor(3);
                    break;
                case 4:
                    changeColor(4);
                    break;
                default:
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
    }
    public void changeColor(int i){
        switch (i) {
            case 0:
                text_template.setTextColor(0xFFCF8312);
                text_expenditure.setTextColor(Color.BLACK);
                text_income.setTextColor(Color.BLACK);
                text_transfer.setTextColor(Color.BLACK);
                text_loan.setTextColor(Color.BLACK);
                break;
            case 1:
                text_template.setTextColor(Color.BLACK);
                text_expenditure.setTextColor(0xFFFFA500);
                text_income.setTextColor(Color.BLACK);
                text_transfer.setTextColor(Color.BLACK);
                text_loan.setTextColor(Color.BLACK);
                break;
            case 2:
                text_template.setTextColor(Color.BLACK);
                text_expenditure.setTextColor(Color.BLACK);
                text_income.setTextColor(0xFFFFA500);
                text_transfer.setTextColor(Color.BLACK);
                text_loan.setTextColor(Color.BLACK);
                break;
            case 3:
                text_template.setTextColor(Color.BLACK);
                text_expenditure.setTextColor(Color.BLACK);
                text_income.setTextColor(Color.BLACK);
                text_transfer.setTextColor(0xFFFFA500);
                text_loan.setTextColor(Color.BLACK);
                break;
            case 4:
                text_template.setTextColor(Color.BLACK);
                text_expenditure.setTextColor(Color.BLACK);
                text_income.setTextColor(Color.BLACK);
                text_transfer.setTextColor(Color.BLACK);
                text_loan.setTextColor(0xFFFFA500);
                break;
            default:
        }
    }
}