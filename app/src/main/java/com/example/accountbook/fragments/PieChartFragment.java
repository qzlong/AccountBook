package com.example.accountbook.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.accountbook.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PieChartFragment extends Fragment{
    private static final String ARG_PARAM1 = "PieEntry";
    private static final String ARG_PARAM2 = "field";
    //private static final String ARG_PARAM3 = "data";


    private List<PieEntry> entries = new ArrayList<>();
    private String field;
    //private List<Bill> bill_list = new ArrayList<>();

    public PieChartFragment() {
        // Required empty public constructor
    }

    public static PieChartFragment newInstance(List<PieEntry> entries, String field){
        PieChartFragment fragment = new PieChartFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) entries);
        args.putString(ARG_PARAM2, field);
        //args.putSerializable(ARG_PARAM3, (Serializable) bill_list);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            entries = (List<PieEntry>) getArguments().getSerializable(ARG_PARAM1);
            //bill_list = (List<Bill>) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.chart_pie, container, false);
        PieChart pieChart = (PieChart) view.findViewById(R.id.chart_pie);
        //dataset
        PieDataSet pieDataSet = new PieDataSet(entries, field);
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

        //pieChart.setTransparentCircleColor(Color.WHITE);
        //pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleRadius(30f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the pieChart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // add a selection listener
        //pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        // pieChart.spin(2000, 0, 360);

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

        return view;
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
