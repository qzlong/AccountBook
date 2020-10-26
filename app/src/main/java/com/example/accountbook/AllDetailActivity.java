//package com.example.accountbook;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.accountbook.adapter.RVAdapter;
//import com.example.accountbook.adapter.RVHeaderAdapter;
//import com.example.accountbook.adapter.mSpinnerAdapter;
//import com.example.accountbook.bean.Detail;
//import com.example.accountbook.bean.DetailHeader;
//import com.example.accountbook.bean.FilterBean;
//import com.example.accountbook.decoration.DividerDecoration;
//
//import org.litepal.LitePal;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import cn.we.swipe.helper.WeSwipe;
//import cn.we.swipe.helper.WeSwipeHelper;
//
//public class StatisticsActivity extends AppCompatActivity{
//    //RecyclerView
//    private RecyclerView mRecyclerView;
//    private RVHeaderAdapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
//    private RVAdapter.OnItemClickListener mListItemClickListener;
//    private RVAdapter.DeletedItemListener mDeletedItemListener;
//    //Spinner
//    private mSpinnerAdapter<CharSequence> mSpinnerAdapter1;
//    private mSpinnerAdapter<CharSequence> mSpinnerAdapter3;
//    private AdapterView.OnItemSelectedListener mItemSelectListener1;
//    private AdapterView.OnItemSelectedListener mItemSelectListener3;
//    //button
//    private Button btn_category;
//    //summary
//    TextView remain_all;
//    TextView income_all;
//    TextView expend_all;
//    //date
//    private Calendar current_date = getCurrentDate();
//    private int current_month = current_date.get(Calendar.MONTH);
//    private int current_day = current_date.get(Calendar.DATE);
//    private int current_year = current_date.get(Calendar.YEAR);
//    //data
//    private Calendar startTime;
//    private Calendar endTime;
//    private String time_select;
//    private String cate_select;
//    private List<Detail> bill_list = new ArrayList<>();
//    private List<FilterBean.TableMode> tab_list = new ArrayList<>();
//    private List<DetailHeader> headers = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_statistics);
//        setTitle("收支详情");
//        initData();
//        initListener();
//        initView();
////        initAction();
//    }
//    public void initData() {
//        bill_list = LitePal.findAll(Detail.class);
//        Collections.sort(bill_list, new DetailComparetor());
//        startTime = bill_list.get(bill_list.size()-1).getTime();
//        endTime = bill_list.get(0).getTime();
//        time_select = "月";
//        cate_select = "一级分类";
//        if (tab_list.size() == 0) {
//            tab_list.add(new FilterBean.TableMode("全部类别"));
//        }
//        headers.add(new DetailHeader("十月", "", 100, bill_list));
//        headers.add(new DetailHeader("九月", "", 200, bill_list));
//        headers.add(new DetailHeader("九月", "", 200, bill_list));
//        headers.add(new DetailHeader("九月", "", 200, bill_list));
//        mAdapter = new RVHeaderAdapter(StatisticsActivity.this, headers);
//        mLayoutManager = new LinearLayoutManager(StatisticsActivity.this);
//        //mSpinnerAdapter1 = mSpinnerAdapter.createFromResource(StatisticsActivity.this, R.array.time, R.layout.spinner_item);
//        //mSpinnerAdapter3 = mSpinnerAdapter.createFromResource(StatisticsActivity.this, R.array.account, R.layout.spinner_item);
//    }
//
//    @SuppressLint("SetTextI18n")
//    private void initView() {
//        //RecyclerView
//        mRecyclerView = (RecyclerView) findViewById(R.id.accountList);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        //WeSwipe weSwipe = WeSwipe.attach(mRecyclerView).setType(WeSwipeHelper.SWIPE_ITEM_TYPE_FLOWING);
//        //mAdapter.setWeSwipe(weSwipe);
//        mRecyclerView.addItemDecoration(new DividerDecoration(StatisticsActivity.this, LinearLayoutManager.VERTICAL));
//        mAdapter.setDeleteItemListener(mDeletedItemListener);
//        mAdapter.setOnItemClickListener(mListItemClickListener);
//        //Spinner
//        //setSpinner(mSpinnerAdapter1, mItemSelectListener1, R.id.statis_spinner_time);
//        //setSpinner(mSpinnerAdapter3, mItemSelectListener3, R.id.statis_spinner_account);
//        //button
//        btn_category = (Button) findViewById(R.id.statis_button_category);
//        //summary
//        income_all = (TextView) findViewById(R.id.statis_summary_income);
//        expend_all = (TextView) findViewById(R.id.statis_summary_expend);
//        remain_all = (TextView) findViewById(R.id.statis_summary_remain);
//        getSummary(startTime, endTime);
//    }
//
//    public void getData() {
//        setDataByCategory();
//        int start_month = 0;
//        if (current_month > 6) {
//            start_month = current_month - 6;
//        }
//        List<Detail> newList = new ArrayList<>();
//        switch (time) {
//            case "全部时间":
//                newList = LitePal.findAll(Detail.class);
//                Collections.sort(newList, new DetailComparetor());
//                startTime =  newList.get(newList.size()-1).getTime();
//                endTime =  newList.get(0).getTime();
//                break;
//            case "本月":
//                startTime = current_date;
//                startTime.set(Calendar.DATE, 0);
//                endTime = current_date;
//                break;
//            case "上月":
//                startTime = current_date;
//                startTime.set(Calendar.DATE, 0);
//                startTime.add(Calendar.MONTH, -1);
//                endTime = current_date;
//                endTime.add(Calendar.MONTH, -1);
//                break;
//            case "最近半年" :
//                startTime = current_date;
//                startTime.set(Calendar.DATE, 0);
//                startTime.set(Calendar.MONTH, start_month);
//                endTime = current_date;
//                break;
//            case "最近一年":
//                startTime = current_date;
//                startTime.set(Calendar.DATE, 0);
//                startTime.set(Calendar.MONTH, 0);
//                endTime = current_date;
//                endTime.set(Calendar.MONTH, 11);
//                break;
//            default:;
//        }
//        newList = getDataByTime(startTime , endTime);
//        bill_list = newList;
//    }
//    /**
//     * 初始化Listener
//     * 1. 列表项点击 跳转到详情页
//     * 2. 删除按钮
//     * 3. 时间选择下拉框
//     */
//    private void initListener() {
//        //点击列表项
//        mListItemClickListener = (new RVAdapter.OnItemClickListener() {
//            @Override
//            //点击列表项动作 跳转详情页面
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(StatisticsActivity.this, BillDetailActivity.class);
//                //intent.putExtra("bill", (Serializable) bill_list.get(position));
//                intent.putExtra("id", bill_list.get(position).getId());
//                startActivityForResult(intent, 1);
//            }
//        });
//        //删除按钮
//        mDeletedItemListener = new RVAdapter.DeletedItemListener() {
//            @Override
//            //点击删除键动作
//            public void deleted(int position) {
//                LitePal.delete(Detail.class, bill_list.get(position).getId());
//            }
//        };
//        //时间选择
//        mItemSelectListener1 = new AdapterView.OnItemSelectedListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mSpinnerAdapter1.setSelectedPostion(position);
//                String[] times = getResources().getStringArray(R.array.time);
//                String date = times[position];
//                time = date;
//                //getData();
//                //mAdapter.setData(bill_list);
//                getSummary(startTime, endTime);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        };
//    }
//
//    public void initAction() {
//        //分类选择
//        btn_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(StatisticsActivity.this, TypeLabelActivity.class);
//                intent.putExtra("tabs", (Serializable) tab_list);
//                startActivityForResult(intent, 2);
//            }
//        });
//
//    }
//
//    /**
//     * 接受intent返回数据
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    getData();
//                    //mAdapter.setData(bill_list);
//                    getSummary(startTime, endTime);
//                }
//                break;
//            case 2:
//                if (resultCode == RESULT_OK) {
//                    tab_list = (List<FilterBean.TableMode>) data.getSerializableExtra("tabs");
//                    getData();
//                    //mAdapter.setData(bill_list);
//                    getSummary(startTime, endTime);
//                }
//            default:
//        }
//    }
//
//    public int dip2px(Context context, float dipValue) {
//        float m=context.getResources().getDisplayMetrics().density ;
//        return (int)(dipValue * m + 0.5f) ;
//    }
//
//    private void setCateBtn() {
//        if (tab_list.size() == 1) {
//            btn_category.setText(tab_list.get(0).getName());
//        }else {
//            btn_category.setText("自定义");
//        }
//    }
//
//    public void setSpinner(mSpinnerAdapter<CharSequence> adapter, AdapterView.OnItemSelectedListener listener, int id) {
//        Spinner spinner = (Spinner) findViewById(id);
//        adapter.setDropDownViewResource(R.layout.spinner_item);
//        spinner.setDropDownVerticalOffset(dip2px(StatisticsActivity.this,40));
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(listener);
//    }
//
//    private void setDataByCategory() {
//        setCateBtn();
//        if (tab_list.get(0).getName().equals("全部类别")) {
//            bill_list = LitePal.findAll(Detail.class);
//            Collections.sort(bill_list, new DetailComparetor());
//            return;
//        }
//        List<Detail> newList = new ArrayList<>();
//        for (FilterBean.TableMode tab : tab_list) {
//            List<Detail> tmp = LitePal.where("cate1=?", tab.getName()).find(Detail.class);
//            newList.addAll(tmp);
//        }
//        Collections.sort(newList, new DetailComparetor());
//        bill_list = newList;
//    }
//
//    @SuppressLint("SetTextI18n")
////    private void getSummary(Calendar startTime, Calendar endTime) {
////        float expend = getSumByTime(startTime, endTime, "PAY");
////        this.expend.setText(expend + "");
////        float income = getSumByTime(startTime, endTime, "INCOME");
////        this.income.setText(income + "");
////        float remain = income - expend;
////        this.remain.setText(remain + "");
////        if (remain < 0) {
////            this.remain.setTextColor(Color.parseColor("#32CD32"));
////        } else {
////            this.remain.setTextColor(Color.parseColor("#B22222"));
////        }
////    }
//
//
//
//    private class DetailComparetor implements Comparator<Detail> {
//        @Override
//        public int compare(Detail bill1, Detail bill2) {
//            if (before(bill1.getTime(), bill2.getTime())){
//                return 1;
//            }else if (before(bill2.getTime(), bill1.getTime())) {
//                return -1;
//            }else {
//                return 0;
//            }
//        }
//    }
//
//    private List<Detail> getDataByTime(Calendar startTime, Calendar endTime) {
//        List<Detail> data = new ArrayList<>();
//        for (Detail bill : bill_list) {
//            Calendar time = bill.getTime();
//            if (before(startTime, time)  && before(time, endTime)) {
//                data.add(bill);
//            }
//        }
//        return data;
//    }
//
//    public float getSumByTime(Calendar startTime, Calendar endTime, String type) {
//        float sum = 0;
//        for (Detail bill : bill_list) {
//            Calendar date = bill.getTime();
//            String bill_type = bill.getType();
//            if (before(startTime, date)  && before(date, endTime) && bill_type.equals(type)) {
//                sum += bill.getMoney();
//            }
//        }
//        return sum;
//    }
//
//    private Calendar getCurrentDate() {
//        return Calendar.getInstance();
//    }
//
//    private boolean inTimePeriod(Calendar startTime, Calendar endTime, Calendar date) {
//        int start = startTime.get(Calendar.YEAR);
//        int end = startTime.get(Calendar.YEAR);
//        int time = date.get(Calendar.YEAR);
//        if (start > time || end < time) {
//            return false;
//        }
//        start = startTime.get(Calendar.MONTH);
//        end = startTime.get(Calendar.MONTH);
//        time = date.get(Calendar.MONTH);
//        if (start > time || end < time) {
//            return false;
//        }
//        start = startTime.get(Calendar.DAY_OF_MONTH);
//        end = startTime.get(Calendar.DAY_OF_MONTH);
//        time = date.get(Calendar.DAY_OF_MONTH);
//        if (start > time || end < time) {
//            return false;
//        }
//        start = startTime.get(Calendar.HOUR_OF_DAY);
//        end = startTime.get(Calendar.HOUR_OF_DAY);
//        time = date.get(Calendar.HOUR_OF_DAY);
//        if (start > time || end < time) {
//            return false;
//        }
//        start = startTime.get(Calendar.MINUTE);
//        end = startTime.get(Calendar.MINUTE);
//        time = date.get(Calendar.MINUTE);
//        if (start > time || end < time) {
//            return false;
//        }
//        return true;
//    }
//
//    private boolean before(Calendar date1, Calendar date2) {
//        int time1 = date1.get(Calendar.YEAR);
//        int time2 = date2.get(Calendar.YEAR);
//        if (time1 > time2) {
//            return false;
//        }
//        time1 = date1.get(Calendar.MONTH);
//        time2 = date2.get(Calendar.MONTH);
//        if (time1 > time2) {
//            return false;
//        }
//        time1 = date1.get(Calendar.DAY_OF_MONTH);
//        time2 = date2.get(Calendar.DAY_OF_MONTH);
//        if (time1 > time2) {
//            return false;
//        }
//        time1 = date1.get(Calendar.HOUR_OF_DAY);
//        time2 = date2.get(Calendar.HOUR_OF_DAY);
//        if (time1 > time2) {
//            return false;
//        }
//        time1 = date1.get(Calendar.MINUTE);
//        time2 = date2.get(Calendar.MINUTE);
//        if (time1 > time2) {
//            return false;
//        }
//        return true;
//    }
//}
