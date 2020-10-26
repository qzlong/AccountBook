package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.accountbook.adapter.CAFragmentPagerAdapter;
import com.example.accountbook.adapter.mSpinnerAdapter;
import com.example.accountbook.bean.Detail;
import com.example.accountbook.bean.FilterBean;
import com.example.accountbook.fragments.LineChartFragment;
import com.example.accountbook.fragments.PieChartFragment;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
    private Spinner mSpinner2;
    private Spinner mSpinner1;
    private AdapterView.OnItemSelectedListener mItemSelectListener1;
    private AdapterView.OnItemSelectedListener mItemSelectListener2;
    private TabLayout tabLayout;
    //button
    private Button btn_category;
    //fragment
    private PieChartFragment pieChartFragment;
    private LineChartFragment lineChartFragment;
    //date
    private Calendar current_date = getCurrentDate();
    private int current_month = current_date.get(Calendar.MONTH);
    private int current_day = current_date.get(Calendar.DATE);
    private int current_year = current_date.get(Calendar.YEAR);
    //data
    private Calendar startTime;
    private Calendar endTime;
    private String category;
    private String time;
    private List<Detail> bill_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_analysis);
        setTitle("图表分析");
        initData();
        initListener();
        initView();
        initAction();
    }

    public void initData() {
        bill_list = LitePal.findAll(Detail.class);
        Collections.sort(bill_list, new DetailComparetor());
        startTime = bill_list.get(bill_list.size()-1).getTime();
        endTime = bill_list.get(0).getTime();
        time = "全部时间";
        category = "全部类别";
        mSpinnerAdapter1 = mSpinnerAdapter.createFromResource(ChartAnalysisActivity.this, R.array.time, R.layout.spinner_item);
        mSpinnerAdapter2 = mSpinnerAdapter.createFromResource(ChartAnalysisActivity.this, R.array.categories, R.layout.spinner_item);
    }

    public void initView() {
        //Spinner
        setSpinner(mSpinner1, mSpinnerAdapter1, mItemSelectListener1, R.id.chartAnalysis_spinner_time);
        setSpinner(mSpinner2, mSpinnerAdapter2, mItemSelectListener2, R.id.chartAnalysis_spinner_category);
        //tab
        tabLayout = (TabLayout) findViewById(R.id.chartAnalysis_tabLayout);
        //button
        //btn_category = (Button) findViewById(R.id.chartAnalysis_spinner_category);
        //pieChart
        pieChartFragment = new PieChartFragment();
        setPieChart(pieChartFragment, bill_list, category);
        //lineChart
        lineChartFragment = new LineChartFragment();
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

    private void setPieChart(Fragment pieChartFragment, List<Detail> bill_list, String field) {
        List<PieEntry> pieEntries = getPieEntries(field, bill_list);
        Bundle args = new Bundle();
        args.putSerializable("PieEntry", (Serializable) pieEntries);
        args.putSerializable("field", field);
        pieChartFragment.setArguments(args);
    }

    private void initListener() {
        //时间选择
        mItemSelectListener1 = new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter1.setSelectedPostion(position);
                String[] times = getResources().getStringArray(R.array.time);
                String date = times[position];
                time = date;
                getData();
                pieChartFragment = new PieChartFragment();
                setPieChart(pieChartFragment, bill_list, category);
                viewList.clear();
                viewList.add(pieChartFragment);
                viewList.add(lineChartFragment);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        mItemSelectListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter2.setSelectedPostion(position);
                String[] categories = getResources().getStringArray(R.array.categories);
                String cate = categories[position];
                category = cate;
                getData();
                pieChartFragment = new PieChartFragment();
                setPieChart(pieChartFragment, bill_list, category);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void initAction() {
//        btn_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ChartAnalysisActivity.this, TypeLabelActivity.class);
//                intent.putExtra("tabs", (Serializable) tab_list);
//                startActivityForResult(intent, 1);
//            }
//        });
    }

    public void getAllData() {

    }

    /**
     * 获取指定时间区段和类别的数据
     */
    public void getData() {
        switch (category) {
            case "全部类别":
                bill_list = LitePal.findAll(Detail.class);
                break;
            case "一级支出":
            case "二级支出":
                bill_list = LitePal.where("type like ?", "PAY").find(Detail.class);
                break;
            case "一级收入":
            case "二级收入":
                bill_list = LitePal.where("type like ?", "INCOME").find(Detail.class);
                break;
        }
        Collections.sort(bill_list, new DetailComparetor());

        int start_month = 0;
        if (current_month > 6) {
            start_month = current_month - 6;
        }
        List<Detail> newList = new ArrayList<>();
        switch (time) {
            case "全部时间":
                newList = LitePal.findAll(Detail.class);
                Collections.sort(newList, new DetailComparetor());
                startTime =  newList.get(newList.size()-1).getTime();
                endTime =  newList.get(0).getTime();
                break;
            case "本月":
                startTime = current_date;
                startTime.set(Calendar.DATE, 0);
                endTime = current_date;
                break;
            case "上月":
                startTime = current_date;
                startTime.set(Calendar.DATE, 0);
                startTime.add(Calendar.MONTH, -1);
                endTime = current_date;
                endTime.add(Calendar.MONTH, -1);
                break;
            case "最近半年" :
                startTime = current_date;
                startTime.set(Calendar.DATE, 0);
                startTime.set(Calendar.MONTH, start_month);
                endTime = current_date;
                break;
            case "最近一年":
                startTime = current_date;
                startTime.set(Calendar.DATE, 0);
                startTime.set(Calendar.MONTH, 0);
                endTime = current_date;
                endTime.set(Calendar.MONTH, 11);
                break;
            default:;
        }
        newList = getDataByTime(startTime , endTime);
        bill_list = newList;
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

    public List<PieEntry> getPieEntries(String field, List<Detail> bill_list) {
        List<String> exists = new ArrayList<>();
        List<Float> counts = new ArrayList<>();
        switch (field) {
            case "全部类别":
            case "一级支出":
            case "一级收入":
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
            case "二级收入":
            case "二级支出":
                for (Detail bill : bill_list) {
                        String category2 = bill.getCategory2();
                        int index = exists.indexOf(category2);
                        if (index < 0) {
                            exists.add(category2);
                            counts.add(bill.getMoney());
                            index = exists.indexOf(category2);
                        }
                        counts.set(index, counts.get(index) + bill.getMoney());
                }
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

    private class DetailComparetor implements Comparator<Detail> {
        @Override
        public int compare(Detail bill1, Detail bill2) {
            if (bill1.getTime().after(bill2.getTime())){
                return -1;
            }else if (bill1.getTime().before(bill2.getTime())) {
                return 1;
            }else {
                return 0;
            }
        }
    }

    private List<Detail> getDataByTime(Calendar startTime, Calendar endTime) {
        List<Detail> data = new ArrayList<>();
        for (Detail bill : bill_list) {
            Calendar time = bill.getTime();
            if (time.after(startTime)  && time.before(endTime)) {
                data.add(bill);
            }
        }
        return data;
    }

    private Calendar getCurrentDate() {
        return Calendar.getInstance();
    }
    private void setStart(Calendar date) {
        startTime.set(date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DATE),
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE),
                date.get(Calendar.SECOND));
    }
    private void setEnd(Calendar date) {
        endTime.set(date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DATE),
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE),
                date.get(Calendar.SECOND));
    }
}