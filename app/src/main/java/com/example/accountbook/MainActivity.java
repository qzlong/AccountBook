package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.accountbook.bean.Bill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
                startActivity(intent);
            }
        });
        jumpToChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChartAnalysisActivity.class);
                startActivity(intent);
            }
        });
    }
    public void InitData() {

    }


}
