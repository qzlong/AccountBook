package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.adapter.RVAdapter;
import com.example.accountbook.bean.Detail;
import com.example.accountbook.decoration.DividerDecoration;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChartDetailActivity extends AppCompatActivity{
    //RecyclerView
    private RecyclerView mRecyclerView;
    private RVAdapter mAdapter;
    //summary
    TextView remain_all;
    TextView income_all;
    TextView expend_all;
    //button
    Button goback;
    //title
    TextView title;
    //data
    private Calendar startTime;
    private Calendar endTime;
    private String category;                                 //具体的分类
    private String type;                                    //要显示的类别
    private List<Detail> bill_list = new ArrayList<>();     //要显示的账单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_detail);
        initData();
        initView();
    }
    private void initData() {
        Intent intent = getIntent();
        startTime = (Calendar) intent.getSerializableExtra("startTime");
        startTime.get(Calendar.MILLISECOND);
        startTime.set(Calendar.MILLISECOND, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.add(Calendar.DAY_OF_MONTH, -1);
        endTime = (Calendar) intent.getSerializableExtra("endTime");
        endTime.get(Calendar.MILLISECOND);
        endTime.set(Calendar.MILLISECOND, 0);
        endTime.set(Calendar.MINUTE, 0);
        endTime.set(Calendar.HOUR_OF_DAY, 0);
        endTime.add(Calendar.DAY_OF_MONTH, 1);
        category = intent.getStringExtra("category");
        type = intent.getStringExtra("type");
        getData();
    }
    @SuppressLint("SetTextI18n")
    private void initView() {
        //Button
        goback = (Button) findViewById(R.id.goback);
        //title
        title = (TextView) findViewById(R.id.cd_title);
        title.setText(category);
        getData();
        //summary
        remain_all = (TextView) findViewById(R.id.statis_summary_remain);
        income_all = (TextView) findViewById(R.id.statis_summary_income);
        expend_all = (TextView) findViewById(R.id.statis_summary_expend);
        getSummary();
        //RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.chartDetail_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ChartDetailActivity.this));
        mAdapter = new RVAdapter(bill_list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerDecoration(ChartDetailActivity.this, LinearLayoutManager.VERTICAL));
        initClickListener();

    }

    /**
     * 设置要显示的数据
     */
    public void getData() {
        bill_list = LitePal.findAll(Detail.class);
        if (bill_list.size() == 0) {
            return;
        }
        Collections.sort(bill_list, new DetailComparetor());
        bill_list = getDataByCateFromBillList(type);

        bill_list = getDataByTimeFromBillList(startTime, endTime);
    }


    /**
     * @param list
     * @return 获取指定账单列表收入之和
     */
    private float getSumIncome(List<Detail> list) {
        float sum = 0;
        for (Detail bill : bill_list) {
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
        for (Detail bill : bill_list) {
            if (bill.getType().equals("PAY")) {
                sum += bill.getMoney();
            }
        }
        return sum;
    }

    /**
     * 设置点击Listener
     * 1. 列表项点击 跳转到详情页
     * 2. 删除按钮
     * 3. 返回
     */
    private void initClickListener() {
        //返回
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public int dip2px(Context context, float dipValue) {
        float m=context.getResources().getDisplayMetrics().density ;
        return (int)(dipValue * m + 0.5f) ;
    }


    /**
     * 获取全部账单的收支之和和总结余，并显示
     */
    private void getSummary() {
        float expend = getSumExpend(bill_list);
        this.expend_all.setText(expend + "");
        float income = getSumIncome(bill_list);
        this.income_all.setText(income + "");
        float remain = income - expend;
        this.remain_all.setText(remain + "");
        if (remain < 0) {
            this.remain_all.setTextColor(Color.parseColor("#32CD32"));
        } else {
            this.remain_all.setTextColor(Color.parseColor("#B22222"));
        }
    }

    private List<Detail> getDataByCateFromBillList(String type) {
        List<Detail> data = new ArrayList<>();
        switch (type){
            case "全部分类":
            case "一级支出":
            case "一级收入":
                for (Detail bill : bill_list) {
                    if (bill.getCategory1().equals(category)) {
                        data.add(bill);
                    }
                }
                break;
            case "二级支出":
            case "二级收入":
                for (Detail bill : bill_list) {
                    if (bill.getCategory2().equals(category)) {
                        data.add(bill);
                    }
                }
                break;
            case "成员":
                for (Detail bill : bill_list) {
                    if (bill.getMember().equals(category)) {
                        data.add(bill);
                    }
                }
                break;
            case "账户":
                for (Detail bill : bill_list) {
                    if (bill.getAccount2().equals(category)) {
                        data.add(bill);
                    }
                }
                break;
            case "商家":
                for (Detail bill : bill_list) {
                    if (bill.getTrader().equals(category)) {
                        data.add(bill);
                    }
                }
                break;
            case "项目":
                for (Detail bill : bill_list) {
                    if (bill.getProject().equals(category)) {
                        data.add(bill);
                    }
                }
                break;
            default:;
        }
        return data;
    }

    private List<Detail> getDataByTimeFromBillList(Calendar startTime, Calendar endTime) {
        List<Detail> data = new ArrayList<>();
        for (Detail bill : bill_list) {
            Calendar date = bill.getTime();
            date.get(Calendar.MILLISECOND);
            date.set(Calendar.MILLISECOND, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.HOUR_OF_DAY, 0);
            if (date.after(startTime)) {
                if (date.before(endTime)) {
                    data.add(bill);
                }
            }
        }
        return data;
    }

    private class DetailComparetor implements Comparator<Detail> {
        @Override
        public int compare(Detail bill1, Detail bill2) {
            if (bill1.getTime().before(bill2.getTime())){
                return 1;
            }else if (bill2.getTime().before(bill1.getTime())) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    /**
     * @param cate1
     * @return 返回指定一级分类的所有账单
     */
    private List<Detail> getDataListByCate1(String cate1) {
        List<Detail> dataList = new ArrayList<>();
        for (Detail bill : bill_list) {
            String bill_cate1 = bill.getCategory1();
            if (bill_cate1.equals(cate1)) {
                dataList.add(bill);
            }
        }
        return dataList;
    }

    /**
     * @param cate2
     * @return 返回指定二级分类的所有账单
     */
    private List<Detail> getDataListByCate2(String cate2) {
        List<Detail> dataList = new ArrayList<>();
        for (Detail bill : bill_list) {
            String bill_cate2 = bill.getCategory2();
            if (bill_cate2.equals(cate2)) {
                dataList.add(bill);
            }
        }
        return dataList;
    }

    /**
     * @param account2
     * @return 返回指定二级账户的所有账单
     */
    private List<Detail> getDataListByAccount2(String account2) {
        List<Detail> dataList = new ArrayList<>();
        for (Detail bill : bill_list) {
            String bill_account2 = bill.getAccount2();
            if (bill_account2.equals(account2)) {
                dataList.add(bill);
            }
        }
        return dataList;
    }

    /**
     * @param member
     * @return 返回指定成员的所有账单
     */
    private List<Detail> getDataListByMember(String member) {
        List<Detail> dataList = new ArrayList<>();
        for (Detail bill : bill_list) {
            String bill_member = bill.getMember();
            if (bill_member != null) {
                if (bill_member.equals(member)) {
                    dataList.add(bill);
                }
            }
        }
        return dataList;
    }

    /**
     * @param trader
     * @return 返回指定商家的所有账单
     */
    private List<Detail> getDataListByTrader(String trader) {
        List<Detail> dataList = new ArrayList<>();
        for (Detail bill : bill_list) {
            String bill_trader = bill.getTrader();
            if (bill_trader != null) {
                if (bill_trader.equals(trader)) {
                    dataList.add(bill);
                }
            }
        }
        return dataList;
    }

    /**
     * @param project
     * @return 返回指定项目的所有账单
     */
    private List<Detail> getDataListByProject(String project) {
        List<Detail> dataList = new ArrayList<>();
        for (Detail bill : bill_list) {
            String bill_project = bill.getProject();
            if (bill_project != null) {
                if (bill_project.equals(project)) {
                    dataList.add(bill);
                }
            }
        }
        return dataList;
    }

}
