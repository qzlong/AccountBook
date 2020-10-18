package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.accountbook.adapter.RVAdapter;
import com.example.accountbook.adapter.mSpinnerAdapter;
import com.example.accountbook.decoration.DividerDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.we.swipe.helper.WeSwipe;
import cn.we.swipe.helper.WeSwipeHelper;

public class MainActivity extends AppCompatActivity {
        Button jumpToDetails;
        Button jumpToChart;
        private List<Bill> bill_list = new ArrayList<Bill>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            InitData();
            InitView();
            InitAction();
    }

    public void InitView() {
        jumpToDetails = (Button) findViewById(R.id.button_to_BillDetails);
        jumpToChart = (Button) findViewById(R.id.button_to_chartAnalysis);
    }
    public void InitAction() {
        jumpToDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BillDetailsActivity.class);
                intent.putExtra("data", (Serializable) bill_list);
                startActivity(intent);
            }
        });
        jumpToChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChartAnalysisActivity.class);
                intent.putExtra("data", (Serializable) bill_list);
                startActivity(intent);
            }
        });
    }
    public void InitData() {
        Bill bill1 = new Bill("1", "20", "10月");
        bill_list.add(bill1);
        Bill bill2 = new Bill("2", "30", "10月");
        bill_list.add(bill2);
        Bill bill3 = new Bill("3", "20", "10月");
        bill_list.add(bill3);
        Bill bill4 = new Bill("4", "50", "9月");
        bill_list.add(bill4);
        Bill bill5 = new Bill("4", "20", "8月");
        bill_list.add(bill5);
        Bill bill6 = new Bill("1", "200", "6月");
        bill_list.add(bill6);
        Bill bill7 = new Bill("2", "20", "5月");
        bill_list.add(bill7);
        Bill bill8 = new Bill("3", "55", "1月");
        bill_list.add(bill8);
        Bill bill9 = new Bill("4", "33", "1月");
        bill_list.add(bill9);
        Bill bill10 = new Bill("5", "20", "1月");
        bill_list.add(bill10);
    }


}
