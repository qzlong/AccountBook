package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.adapter.RVAdapter;
import com.example.accountbook.adapter.RVHeaderAdapter;
import com.example.accountbook.adapter.mSpinnerAdapter;
import com.example.accountbook.bean.Detail;
import com.example.accountbook.bean.DetailHeader;
import com.example.accountbook.decoration.DividerDecoration;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity{
    //RecyclerView
    private RecyclerView mRecyclerView;
    private RVHeaderAdapter mAdapter;
    private RVAdapter.OnItemClickListener mListItemClickListener;
    private RVAdapter.DeletedItemListener mDeletedItemListener;
    //Spinner
    private mSpinnerAdapter<CharSequence> mSpinnerAdapter1;
    private mSpinnerAdapter<CharSequence> mSpinnerAdapter2;
    private mSpinnerAdapter<CharSequence> mSpinnerAdapter3;
    private AdapterView.OnItemSelectedListener mItemSelectListener1;
    private AdapterView.OnItemSelectedListener mItemSelectListener2;
    private AdapterView.OnItemSelectedListener mItemSelectListener3;
    //summary
    TextView remain_all;
    TextView income_all;
    TextView expend_all;
    //button
    Button goback;
    //data
    private String time_select;
    private String cate_select;
    private String select_state;                            //当前选择哪种分类
    private List<Detail> bill_list = new ArrayList<>();     //全部账单
    private List<DetailHeader> headers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        setTitle("收支详情");
        initData();
        initView();
    }
    public void initData() {

//        headers.add(new DetailHeader("十月", "", 100, bill_list));
//        headers.add(new DetailHeader("九月", "", 200, bill_list));
//        headers.add(new DetailHeader("九月", "", 200, bill_list));
//        headers.add(new DetailHeader("九月", "", 200, bill_list));

    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        //Button
        goback = (Button) findViewById(R.id.goback);
        //Spinner
        mSpinnerAdapter1 = mSpinnerAdapter.createFromResource(StatisticsActivity.this, R.array.time, R.layout.spinner_item);
        mSpinnerAdapter2 = mSpinnerAdapter.createFromResource(StatisticsActivity.this, R.array.categories, R.layout.spinner_item);
        mSpinnerAdapter3 = mSpinnerAdapter.createFromResource(StatisticsActivity.this, R.array.others, R.layout.spinner_item);
        initSelectListener();
        setSpinner(mSpinnerAdapter1, mItemSelectListener1, R.id.statis_spinner_time);
        setSpinner(mSpinnerAdapter2, mItemSelectListener2, R.id.statis_spinner_category);
        setSpinner(mSpinnerAdapter3, mItemSelectListener3, R.id.statis_spinner_other);
        //data
        getBillList();
        getData();
        //summary
        remain_all = (TextView) findViewById(R.id.statis_summary_remain);
        income_all = (TextView) findViewById(R.id.statis_summary_income);
        expend_all = (TextView) findViewById(R.id.statis_summary_expend);
        getSummary();
        //RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.accountList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(StatisticsActivity.this));
        mAdapter = new RVHeaderAdapter(StatisticsActivity.this, headers);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerDecoration(StatisticsActivity.this, LinearLayoutManager.VERTICAL));
        initClickListener();
        mAdapter.setDeleteItemListener(mDeletedItemListener);
        mAdapter.setOnItemClickListener(mListItemClickListener);

    }

    private void getBillList() {
        bill_list = LitePal.findAll(Detail.class);
        Collections.sort(bill_list, new DetailComparetor());
        time_select = "时间";
        cate_select = "分类";
        select_state = "无";
    }
    /**
     * 设置要显示的headers
     */
    public void getData() {
        if (headers.size() != 0) {
            headers.clear();
        }
        List<Integer> exist_year = getExistYear();
        if (exist_year.size() == 0) return;
        switch (select_state) {
            case "时间":
                if (time_select.equals("年")) {
                    for (int year : exist_year) {
                        DetailHeader detailHeader = new DetailHeader();
                        detailHeader.setDataList(getDataListByYear(year));
                        detailHeader.setType("时间");
                        detailHeader.setTitle(year + "年");
                        detailHeader.setSubtitle("");
                        headers.add(detailHeader);
                    }
                }else {
                    for (int year : exist_year) {
                        List<Integer> exist_month = getExistMonth(year);
                        for (int month : exist_month) {
                            DetailHeader detailHeader = new DetailHeader();
                            detailHeader.setDataList(getDataListByMonth(year, month));
                            detailHeader.setType("时间");
                            detailHeader.setTitle(month + "月");
                            detailHeader.setSubtitle(year + "");
                            headers.add(detailHeader);
                        }
                    }
                }
                break;
            case "分类":
                if (cate_select.equals("二级分类")) {
                    List<String> exist_cate2_pay = getExistCate2("PAY");
                    for (String cate2 : exist_cate2_pay) {
                        DetailHeader detailHeader = new DetailHeader();
                        detailHeader.setDataList(getDataListByCate2(cate2));
                        detailHeader.setType("分类");
                        detailHeader.setTitle(cate2);
                        detailHeader.setSubtitle("");
                        headers.add(detailHeader);
                    }
                } else {
                    List<String> exist_cate1_pay = getExistCate1("PAY");
                    for (String cate1 : exist_cate1_pay) {
                        DetailHeader detailHeader = new DetailHeader();
                        detailHeader.setDataList(getDataListByCate1(cate1));
                        detailHeader.setType("分类");
                        detailHeader.setTitle(cate1);
                        detailHeader.setSubtitle("");
                        headers.add(detailHeader);
                    }
                }
                break;
            case "账户":
                List<String> exist_account2 = getExistAccount2();
                for (String account2 : exist_account2) {
                    DetailHeader detailHeader = new DetailHeader();
                    detailHeader.setDataList(getDataListByAccount2(account2));
                    detailHeader.setType("账户");
                    detailHeader.setTitle(account2);
                    detailHeader.setSubtitle("");
                    headers.add(detailHeader);
                }
                break;
            case "成员":
                List<String> exist_member = getExistMember();
                for (String member : exist_member) {
                    DetailHeader detailHeader = new DetailHeader();
                    detailHeader.setDataList(getDataListByMember(member));
                    detailHeader.setType("成员");
                    detailHeader.setTitle(member);
                    detailHeader.setSubtitle("");
                    headers.add(detailHeader);
                }
                break;
            case "商家":
                List<String> exist_trader = getExistTrader();
                for (String trader : exist_trader) {
                    DetailHeader detailHeader = new DetailHeader();
                    detailHeader.setDataList(getDataListByTrader(trader));
                    detailHeader.setType("商家");
                    detailHeader.setTitle(trader);
                    detailHeader.setSubtitle("");
                    headers.add(detailHeader);
                }
                break;
            case "项目":
                List<String> exist_project = getExistProject();
                for (String project : exist_project) {
                    DetailHeader detailHeader = new DetailHeader();
                    detailHeader.setDataList(getDataListByProject(project));
                    detailHeader.setType("项目");
                    detailHeader.setTitle(project);
                    detailHeader.setSubtitle("");
                    headers.add(detailHeader);
                }
                break;
            default:
                for (int year : exist_year) {
                    List<Integer> exist_month = getExistMonth(year);
                    for (int month : exist_month) {
                        DetailHeader detailHeader = new DetailHeader();
                        detailHeader.setDataList(getDataListByMonth(year, month));
                        detailHeader.setTitle((month + 1) + "月");
                        detailHeader.setSubtitle(year + "");
                        headers.add(detailHeader);
                    }
                }
                break;
        }
    }

    /**
     * @return 返回账单存在的所有月份
     */
    private List<Integer> getExistMonth(int year) {
        List<Integer> month_exist = new ArrayList<>();
        for (Detail bill : bill_list) {
            int bill_month = bill.getTime().get(Calendar.MONTH);
            int bill_year = bill.getTime().get(Calendar.YEAR);
            if (month_exist.indexOf(bill_month) < 0 && bill_year == year) {
                month_exist.add(bill_month);
            }
        }
        return month_exist;
    }
    /**
     * @return 返回账单存在的的所有年份
     */
    private List<Integer> getExistYear() {
        List<Integer> year_exist = new ArrayList<>();
        for (Detail bill : bill_list) {
            int bill_year = bill.getTime().get(Calendar.YEAR);
            if (year_exist.indexOf(bill_year) < 0) {
                year_exist.add(bill_year);
            }
        }
        return year_exist;
    }
    /**
     * @param year
     * @param month
     * @return 返回指定年月的账单
     */
    private List<Detail> getDataListByMonth(int year, int month) {
        List<Detail> dataList = new ArrayList<>();
        for (Detail bill : bill_list) {
            int bill_year = bill.getTime().get(Calendar.YEAR);
            int bill_month = bill.getTime().get(Calendar.MONTH);
            if (bill_year == year && bill_month == month) {
                dataList.add(bill);
            }
        }
        return dataList;
    }

    /**
     * @param year
     * @return 返回指定年份的账单
     */
    private List<Detail> getDataListByYear(int year) {
        List<Detail> dataList = new ArrayList<>();
        for (Detail bill : bill_list) {
            int bill_year = bill.getTime().get(Calendar.YEAR);
            if (bill_year == year) {
                dataList.add(bill);
            }
        }
        return dataList;
    }

    /**
     * @param type
     * @return 返回指定类型的所有账单的所有一级分类
     */
    private List<String> getExistCate1(String type) {
        List<String> cate1_exist = new ArrayList<>();
        for (Detail bill : bill_list) {
            String bill_cate1 = bill.getCategory1();
            String bill_type = bill.getType();
            if (cate1_exist.indexOf(bill_cate1) < 0 && bill_type.equals(type)) {
                cate1_exist.add(bill_cate1);
            }
        }
        return cate1_exist;
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
     * @param type
     * @return 返回指定类型的所有账单的所有二级分类
     */
    private List<String> getExistCate2(String type) {
        List<String> cate2_exist = new ArrayList<>();
        for (Detail bill : bill_list) {
            String bill_cate2 = bill.getCategory2();
            String bill_type = bill.getType();
            if (cate2_exist.indexOf(bill_cate2) < 0 && bill_type.equals(type))  {
                cate2_exist.add(bill_cate2);
            }
        }
        return cate2_exist;
    }
    /**
     * @param cate2
     * @return 返回指定一级分类的所有账单
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
     * @return 返回指定类型的所有账单的所有二级账户
     */
    private List<String> getExistAccount2() {
        List<String> account2_exist = new ArrayList<>();
        for (Detail bill : bill_list) {
            String bill_account2 = bill.getAccount2();
            String bill_type = bill.getType();
            if (account2_exist.indexOf(bill_account2) < 0 && (bill_type.equals("PAY") || bill_type.equals("INCOME")))  {
                account2_exist.add(bill_account2);
            }
        }
        return account2_exist;
    }
    /**
     * @param account2
     * @return 返回指定一级账户的所有账单
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
     * @return 返回所有成员
     */
    private List<String> getExistMember() {
        List<String> member_exist = new ArrayList<>();
        for (Detail bill : bill_list) {
            String member = bill.getMember();
            if (member_exist.indexOf(member) < 0)  {
                member_exist.add(member);
            }
        }
        return member_exist;
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
     * @return 返回所有商家
     */
    private List<String> getExistTrader() {
        List<String> trader_exist = new ArrayList<>();
        for (Detail bill : bill_list) {
            String trader = bill.getTrader();
            if (trader_exist.indexOf(trader) < 0)  {
                trader_exist.add(trader);
            }
        }
        return trader_exist;
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
     * @return 返回所有项目
     */
    private List<String> getExistProject() {
        List<String> project_exist = new ArrayList<>();
        for (Detail bill : bill_list) {
            String project = bill.getTrader();
            if (project_exist.indexOf(project) < 0)  {
                project_exist.add(project);
            }
        }
        return project_exist;
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

    /**
     * 设置选择器listener
     */
    private void initSelectListener() {
        //时间选择
        mItemSelectListener1 = new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter1.setSelectedPostion(position);
                String[] times = getResources().getStringArray(R.array.time);
                time_select = times[position];
                select_state = "时间";
                getData();
                mAdapter.setData(headers);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        mItemSelectListener2 = new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter1.setSelectedPostion(position);
                String[] cates = getResources().getStringArray(R.array.categories);
                cate_select = cates[position];
                select_state = "分类";
                getData();
                mAdapter.setData(headers);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        mItemSelectListener3 = new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter1.setSelectedPostion(position);
                String[] others = getResources().getStringArray(R.array.others);
                select_state = others[position];
                getData();
                mAdapter.setData(headers);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }
    /**
     * 设置点击Listener
     * 1. 列表项点击 跳转到详情页
     * 2. 删除按钮
     * 3. 返回
     */
    private void initClickListener() {
        //点击列表项
        mListItemClickListener = (new RVAdapter.OnItemClickListener() {
            @Override
            //点击列表项动作 跳转详情页面
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(StatisticsActivity.this, BillDetailActivity.class);
                intent.putExtra("id", bill_list.get(position).getId());
                startActivityForResult(intent, 1);
            }
        });
        //删除按钮
        mDeletedItemListener = new RVAdapter.DeletedItemListener() {
            @Override
            //点击删除键动作
            public void deleted(int position) {
                LitePal.delete(Detail.class, bill_list.get(position).getId());
            }
        };
        //返回
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 接受intent返回数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { //由账单详情页返回，需更新当前数据
            if (resultCode == RESULT_OK) {
                getBillList();
                getData();
                mAdapter.setData(headers);
                getSummary();
            }
        }
    }

    public int dip2px(Context context, float dipValue) {
        float m=context.getResources().getDisplayMetrics().density ;
        return (int)(dipValue * m + 0.5f) ;
    }

    /**
     * 设置选择器
     * @param adapter
     * @param listener
     * @param id
     */
    public void setSpinner(mSpinnerAdapter<CharSequence> adapter, AdapterView.OnItemSelectedListener listener, int id) {
        Spinner spinner = (Spinner) findViewById(id);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setDropDownVerticalOffset(dip2px(StatisticsActivity.this,40));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listener);
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

}
