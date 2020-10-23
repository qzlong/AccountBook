package com.example.accountbook;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.accountbook.adapter.CAFragmentPagerAdapter;
import com.example.accountbook.adapter.mSpinnerAdapter;
import com.example.accountbook.bean.Detail;
import com.example.accountbook.fragments.LineChartFragment;
import com.example.accountbook.fragments.PieChartFragment;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChartAnalysisActivity extends AppCompatActivity {
    //ViewPager
    private ViewPager mViewPager;
    private PagerAdapter mVPAdapter;
    //private View chart_pie, chart_line;
    private List<Fragment> viewList = new ArrayList<>();
    //Spinner
    private mSpinnerAdapter mSpinnerAdapter1;
    private mSpinnerAdapter mSpinnerAdapter2;
    private Spinner mSpinner1;
    private AdapterView.OnItemSelectedListener mItemSelectListener1;
    private Spinner mSpinner2;
    private AdapterView.OnItemSelectedListener mItemSelectListener2;
    private TabItem tab_pieChart;
    private TabItem tab_linearChart;
    private TabLayout tabLayout;
    //

    private String field;
    private List<Detail> bill_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_analysis);
        setTitle("图表分析");
        initData();
        initView();
        initAction();
    }



    public void initData() {
        getData();
        mSpinnerAdapter1 = mSpinnerAdapter.createFromResource(ChartAnalysisActivity.this, R.array.time, R.layout.spinner_item);
        mSpinnerAdapter2 = mSpinnerAdapter.createFromResource(ChartAnalysisActivity.this, R.array.categories, R.layout.spinner_item);
    }

    public void initView() {
        //Spinner
        setSpinner(mSpinner1, mSpinnerAdapter1, mItemSelectListener1, R.id.chartAnalysis_spinner_time);
        setSpinner(mSpinner2, mSpinnerAdapter2, mItemSelectListener2, R.id.chartAnalysis_spinner_category);
        //tab
        tabLayout = (TabLayout) findViewById(R.id.chartAnalysis_tabLayout);
        tab_pieChart = (TabItem) findViewById(R.id.tab_pieChart);
        tab_linearChart = (TabItem) findViewById(R.id.tab_linearChart);
        //pieChart
        PieChartFragment pieChartFragment = new PieChartFragment();
        List<PieEntry> pieEntries = getPieEntries("All");
        Bundle args = new Bundle();
        args.putSerializable("PieEntry", (Serializable) pieEntries);
        //args.putSerializable("data", (Serializable) bill_list);
        args.putSerializable("field", field);
        pieChartFragment.setArguments(args);
        //lineChart
        LineChartFragment lineChartFragment = new LineChartFragment();
        List<Entry> lineEntries = getLineEntries();
        Bundle args2 = new Bundle();
        args2.putSerializable("LineEntry", (Serializable)lineEntries);
        lineChartFragment.setArguments(args2);

        //ViewPager
        viewList.add(pieChartFragment);
        viewList.add(lineChartFragment);
        mVPAdapter = new CAFragmentPagerAdapter(getSupportFragmentManager(), viewList);
        mViewPager = (ViewPager) findViewById(R.id.chartAnlysis_viewPager);

        mViewPager.setAdapter(mVPAdapter);
        mViewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(mViewPager);




    }
    private void initAction() {
    }

    public void getData() {
//        Calendar date1 = Calendar.getInstance();
//        date1.set(2020, 3, 12, 18, 30);
//        Bill bill1 = new Bill((float) 30.0, "早餐", date1);
////        bill_list.add(bill1);
//        Calendar date2 = Calendar.getInstance();
//        date2.set(2020, 4, 13, 18, 30);
//        Bill bill2 = new Bill((float) 40.0, "午餐", date2);
////        bill_list.add(bill2);
//        Calendar date3 = Calendar.getInstance();
//        date3.set(2020, 4, 14, 18, 30);
//        Bill bill3 = new Bill((float) 40.0, "午餐", date3);
////        bill_list.add(bill3);
//        Calendar date4 = Calendar.getInstance();
//        date4.set(2020, 5, 16, 18, 30);
//        Bill bill4 = new Bill((float) 40.0, "晚餐", date4);
////        bill_list.add(bill4);
//
//        Calendar date5 = Calendar.getInstance();
//        date5.set(2020, 7,12, 18, 30);
//        Bill bill5 = new Bill((float) 200.0, "转账", date5);
////        bill_list.add(bill5);
//        Calendar date6 = Calendar.getInstance();
//        date6.set(2020, 8, 12, 18, 30);
//        Bill bill6 = new Bill((float) 40.0, "打车", date6);
//        bill_list.add(bill5);
//        Calendar date7 = Calendar.getInstance();
//        date7.set(2020, 8, 14, 18, 30);
//        Bill bill7 = new Bill((float) 10.0, "晚餐", date7);
//        bill_list.add(bill4);
//        Calendar date8 = Calendar.getInstance();
//        date8.set(2020, 9, 5, 18, 30);
//        Bill bill8 = new Bill((float) 20.0, "购物", date8);
//        bill_list.add(bill3);
//        Calendar date9 = Calendar.getInstance();
//        date9.set(2020, 9, 4, 18, 30);
//        Bill bill9 = new Bill((float) 50.0, "晚餐", date9);
//        bill_list.add(bill2);
//        Calendar date11 = Calendar.getInstance();
//        date11.set(2020, 9, 6, 18, 30);
//        Bill bill10 = new Bill((float) 40.0, "晚餐", date11);
//        bill_list.add(bill1);
//        bill_list.add(bill6);
//        bill_list.add(bill7);
//        bill_list.add(bill8);
//        bill_list.add(bill9);
//        bill_list.add(bill10);
        bill_list = LitePal.findAll(Detail.class);
    }
    public int dip2px(Context context, float dipValue) {
        float m=context.getResources().getDisplayMetrics().density ;
        return (int)(dipValue * m + 0.5f) ;
    }
    public void setSpinner(Spinner spinner, mSpinnerAdapter<CharSequence> adapter, AdapterView.OnItemSelectedListener listener, int id) {
        spinner = (Spinner) findViewById(id);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setDropDownVerticalOffset(dip2px(ChartAnalysisActivity.this,40));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listener);
    }



    public List<PieEntry> getPieEntries(String field) {
        List<String> exists = new ArrayList<>();
        List<Float> counts = new ArrayList<>();
        switch (field) {
            case "All" :
                for (Detail bill : bill_list) {
                    String category1 = bill.getCategory1();
                    int index = exists.indexOf(category1);
                    if (index < 0) {
                        exists.add(category1);
                        counts.add(bill.getMoney());
                        index = exists.indexOf(category1);
                    }
                    counts.set(index, counts.get(index) + bill.getMoney());
                }
            case "Category1":
                break;
            case "Category2":
                break;
            default:;
        }

        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < exists.size(); i++) {
            PieEntry pieEntry = new PieEntry(counts.get(i), exists.get(i));
            entries.add(pieEntry);
        }
        return entries;
    }


    public float[] getSumByMonth() {
        float[] sum_month = new float[12];
        for (Detail bill : bill_list) {
            int index = bill.getTime().get(Calendar.MONTH);
            sum_month[index] += bill.getMoney();
        }
        return sum_month;
    }
    public List<Entry> getLineEntries() {
        List<Entry> entries = new ArrayList<>();
        float[] sum_month = getSumByMonth();
        for (int i = 0; i < 12; i++) {
            Entry entry = new Entry(i+1 , sum_month[i]);
            entries.add(entry);
        }
        return entries;
    }

}