package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bigkoo.pickerview.view.TimePickerView;
import com.example.accountbook.adapter.mSpinnerAdapter;
import com.example.accountbook.bean.Detail;
import com.example.accountbook.pickers.DatePicker;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.litepal.LitePal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChartAnalysisActivity extends AppCompatActivity {
    //Spinner
    private mSpinnerAdapter mSpinnerAdapter1;
    private mSpinnerAdapter mSpinnerAdapter2;
    private Spinner mSpinner2;
    private Spinner mSpinner1;
    private AdapterView.OnItemSelectedListener mItemSelectListener1;
    private AdapterView.OnItemSelectedListener mItemSelectListener2;
    //button
    private Button goback;
    private Button btn_setStartTime;
    private Button btn_setEndTime;
    private Button btn_confirmTimePicker;
    private Button btn_cancel;
    //pickerview
    private ConstraintLayout timePicker;
    private TimePickerView startTimePickerView;
    private TimePickerView endTimePickerView;
    private DatePicker startTimePicker = new DatePicker();
    private DatePicker endTimePicker = new DatePicker();
    //piechart
    private PieChart pieChart;
    private String cate_title;
    private OnChartValueSelectedListener mChartSelectListener;
    //date
    private Calendar current_date = getCurrentDate();
    private final int current_month = current_date.get(Calendar.MONTH);
    private final int start_month = (current_month > 6) ? current_month - 6 : 0;
    //data
    private List<Detail> bill_list = new ArrayList<>(); //将要显示的账单数据
    private Calendar startTime;     //显示数据的开始时间
    private Calendar endTime;       //显示数据的结束时间
    private String category_select; //显示数据选择的类别
    private String time_select;     //显示数据选择的时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_analysis);
        setTitle("图表分析");
        initData();
        initView();
    }

    public void initData() {
        getBillList();
    }

    public void initView() {
        //Spinner
        mSpinnerAdapter1 = mSpinnerAdapter.createFromResource(ChartAnalysisActivity.this, R.array.chart_time, R.layout.spinner_item);
        mSpinnerAdapter2 = mSpinnerAdapter.createFromResource(ChartAnalysisActivity.this, R.array.chart_categories, R.layout.spinner_item);
        initSelectListener();
        setSpinner(mSpinner1, mSpinnerAdapter1, mItemSelectListener1, R.id.chartAnalysis_spinner_time);
        setSpinner(mSpinner2, mSpinnerAdapter2, mItemSelectListener2, R.id.chartAnalysis_spinner_category);
        //button
        goback = (Button) findViewById(R.id.goback);
        btn_setStartTime = (Button) findViewById(R.id.btn_startTime);
        btn_setEndTime = (Button) findViewById(R.id.btn_endTime);
        btn_confirmTimePicker = (Button) findViewById(R.id.btn_confirm);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        setTimeBtn();
        //pickerView
        timePicker = (ConstraintLayout) findViewById(R.id.timePicker);
        startTimePickerView = startTimePicker.getDatePicker(ChartAnalysisActivity.this, btn_setStartTime);
        endTimePickerView = endTimePicker.getDatePicker(ChartAnalysisActivity.this, btn_setEndTime);
        initClickListener();
        //pieChart
        pieChart = (PieChart) findViewById(R.id.chart_pie);
        initChartListener();
        setPieChart();
    }

    private void getBillList() {
        bill_list = LitePal.findAll(Detail.class);
        if (bill_list.size() == 0) {
            startTime = (Calendar) current_date.clone();
            endTime = (Calendar) current_date.clone();
            return;
        }
        Collections.sort(bill_list, new DetailComparetor());
        startTime = bill_list.get(bill_list.size()-1).getTime();
        endTime = bill_list.get(0).getTime();
        time_select = "全部时间";
        category_select = "全部分类";
        cate_title = "全部分类";
    }

    private void setPieChart() {
        //pieEntry
        List<PieEntry> pieEntries = getPieEntriesFromBillList();
        //dataset
        PieDataSet pieDataSet = new PieDataSet(pieEntries, cate_title);
        pieDataSet.setColors(getColors());
        pieDataSet.setValueTextSize(15);
        pieDataSet.setValueTextColor(Color.DKGRAY);
        //piedata
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.DKGRAY);
        //setdata
        pieChart.setData(pieData);
        pieChart.invalidate();
//        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);


        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleRadius(30f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // add a selection listener
        pieChart.setOnChartValueSelectedListener(mChartSelectListener);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.DKGRAY);
        pieChart.setEntryLabelTextSize(12f);
    }

    private void initSelectListener() {
        //时间选择
        mItemSelectListener1 = new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter1.setSelectedPostion(position);
                String[] times = getResources().getStringArray(R.array.chart_time);
                time_select = times[position];
                if (time_select.equals("自定义")){
                    timePicker.setVisibility(View.VISIBLE);
                } else {
                    setBillList();
                    setPieChart();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        //分类选择
        mItemSelectListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter2.setSelectedPostion(position);
                String[] categories = getResources().getStringArray(R.array.chart_categories);
                category_select = categories[position];
                cate_title = category_select;
                setBillList();
                setPieChart();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

    private void initClickListener () {
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_setStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimePickerView.show();
            }
        });
        btn_setEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimePickerView.show();
            }
        });
        btn_confirmTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBillList();
                setPieChart();
                timePicker.setVisibility(View.GONE);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.setVisibility(View.GONE);
            }
        });


    }

    private void initChartListener() {
        mChartSelectListener = new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
                Log.i("VAL SELECTED", e.getData() +
                        "Value: " + e.getY() + ", index: " + h.getX()
                                + ", DataSet index: " + h.getDataSetIndex());

                Intent intent = new Intent(ChartAnalysisActivity.this, ChartDetailActivity.class);
                intent.putExtra("startTime", (Serializable) startTime);
                intent.putExtra("endTime", (Serializable) endTime);
                intent.putExtra("category", e.getData() + "");
                startActivity(intent);

            }

            @Override
            public void onNothingSelected() {

            }
        };
    }
    /**
     * 获取指定时间区段和类别的数据至detail_list
     */
    public void setBillList() {
        bill_list = LitePal.findAll(Detail.class);
        if (bill_list.size() == 0) return;
        switch (category_select) {
            case "全部类别":  break;
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

        if (bill_list.size() <= 1) return;
        startTime = (Calendar) current_date.clone();
        endTime = (Calendar) current_date.clone();
        switch (time_select) {
            case "全部时间":
                startTime = bill_list.get(bill_list.size()-1).getTime();
                endTime = bill_list.get(0).getTime();
                return;
            case "本月":
                startTime.set(Calendar.DAY_OF_MONTH, 0);
                break;
            case "上月":
                startTime.set(Calendar.DAY_OF_MONTH, 0);
                startTime.add(Calendar.MONTH, -1);
                endTime.set(Calendar.DAY_OF_MONTH, 0);
                break;
            case "最近半年" :
                startTime.set(Calendar.DAY_OF_MONTH, 0);
                startTime.set(Calendar.MONTH, start_month);
                break;
            case "最近一年":
                startTime.set(Calendar.DAY_OF_MONTH, 0);
                startTime.set(Calendar.MONTH, 0);
                endTime.set(Calendar.MONTH, 12);
                endTime.set(Calendar.DAY_OF_MONTH, 0);
                break;
            case "自定义":
                startTime = startTimePicker.getTime();
                endTime = endTimePicker.getTime();
                break;
            default:;
        }
        setTimeBtn();
        bill_list = getDataByTimeFromBillList(startTime, endTime);
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

    private void setTimeBtn() {
        String startTime_text = startTime.get(Calendar.YEAR) + "年" + (startTime.get(Calendar.MONTH)+1) + "月" + startTime.get(Calendar.DAY_OF_MONTH) + "日";
        String endTime_text = endTime.get(Calendar.YEAR) + "年" + (endTime.get(Calendar.MONTH)+1) +"月" + endTime.get(Calendar.DAY_OF_MONTH) +"日";
        btn_setStartTime.setText(startTime_text);
        btn_setEndTime.setText(endTime_text);
    }

    public List<PieEntry> getPieEntriesFromBillList() {
        if (bill_list.size() == 0) {
            return new ArrayList<>();
        }
        List<String> exists = new ArrayList<>();
        List<Float> counts = new ArrayList<>();
        switch (category_select) {
            case "全部分类":
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
                break;
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
            PieEntry pieEntry = new PieEntry(counts.get(i), exists.get(i), exists.get(i));
            entries.add(pieEntry);
        }
        return entries;
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

    private List<Detail> getDataByTimeFromBillList(Calendar startTime, Calendar endTime) {
        List<Detail> data = new ArrayList<>();
        for (Detail bill : bill_list) {
            Calendar date = bill.getTime();
            if (date.after(startTime)) {
                if (date.before(endTime)) {
                    data.add(bill);
                }
            }
        }
        return data;
    }

    private Calendar getCurrentDate() {
        return Calendar.getInstance();
    }

    public ArrayList<Integer> getColors() {
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }
}