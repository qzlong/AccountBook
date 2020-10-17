package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.accountbook.adapter.RVAdapter;
import com.example.accountbook.adapter.mSpinnerAdapter;
import com.example.accountbook.decoration.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.we.swipe.helper.WeSwipe;
import cn.we.swipe.helper.WeSwipeHelper;

public class MainActivity extends AppCompatActivity {
        //RecyclerView
        private RecyclerView mRecyclerView;
        private RVAdapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private RVAdapter.OnItemClickListener mListItemClickListener;
        private RVAdapter.DeletedItemListener mDeletedItemListener;
        //Spinner
        private mSpinnerAdapter mSpinnerAdapter1;
        private mSpinnerAdapter mSpinnerAdapter2;
        private mSpinnerAdapter mSpinnerAdapter3;
        private Spinner mSpinner1;
        private AdapterView.OnItemSelectedListener mItemSelectListener1;
        private Spinner mSpinner2;
        private AdapterView.OnItemSelectedListener mItemSelectListener2;
        private Spinner mSpinner3;
        private AdapterView.OnItemSelectedListener mItemSelectListener3;
        //AccountList data
        private List<Bill> bill_list = new ArrayList<Bill>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            initData();
            initAction();
        initView();

    }

    public void initData() {
        getData();
        mAdapter = new RVAdapter(bill_list);
        mLayoutManager = new LinearLayoutManager(this);
        mSpinnerAdapter1 = mSpinnerAdapter.createFromResource(this, R.array.time, R.layout.spinner_item);
        mSpinnerAdapter2 = mSpinnerAdapter.createFromResource(this, R.array.categories, R.layout.spinner_item);
        mSpinnerAdapter3 = mSpinnerAdapter.createFromResource(this, R.array.account, R.layout.spinner_item);
    }

    public void initView() {
        //RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.accountList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        WeSwipe weSwipe = WeSwipe.attach(mRecyclerView).setType(WeSwipeHelper.SWIPE_ITEM_TYPE_FLOWING);
        mAdapter.setWeSwipe(weSwipe);
        mRecyclerView.addItemDecoration(new DividerDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter.setDeleteItemListener(mDeletedItemListener);
        mAdapter.setOnItemClickListener(mListItemClickListener);

        //Spinner
        setSpinner(mSpinner1, mSpinnerAdapter1, mItemSelectListener1, R.id.statis_spinner_time);
        setSpinner(mSpinner2, mSpinnerAdapter2, mItemSelectListener2, R.id.statis_spinner_category);
        setSpinner(mSpinner3, mSpinnerAdapter3, mItemSelectListener2, R.id.statis_spinner_account);

    }
    private void getData() {//初始化数据\
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

    public void initAction() {
        mListItemClickListener = (new RVAdapter.OnItemClickListener() {
            //@Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(MainActivity.this,"click " + position + " item", Toast.LENGTH_SHORT).show();
//            }
            @Override
            //长按列表项动作
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this,"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });

        mDeletedItemListener = new RVAdapter.DeletedItemListener() {
            @Override
            //点击删除键动作
            public void deleted(int position) {
                mAdapter.deleteItem(position);
            }
        };
        mItemSelectListener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter1.setSelectedPostion(position);
                String[] times = getResources().getStringArray(R.array.time);
                String date = times[position];
                List<Bill> tmp_list = null;
                switch (date) {
                    case "本月":
                        tmp_list = setDataByDate("10月");
                        break;
                    case "上月":
                        tmp_list = setDataByDate("9月");
                        break;
                    default:
                        tmp_list = bill_list;
                }
                mAdapter.setData(tmp_list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        mItemSelectListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter2.setSelectedPostion(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

    }
    public int dip2px(Context context, float dipValue) {
        float m=context.getResources().getDisplayMetrics().density ;
        return (int)(dipValue * m + 0.5f) ;
    }
    public void setSpinner(Spinner spinner, mSpinnerAdapter adapter, AdapterView.OnItemSelectedListener listener, int id) {
        spinner = (Spinner) findViewById(id);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setDropDownVerticalOffset(dip2px(this,40));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listener);
    }
    public List<Bill> setDataByDate(String date) {
            List<Bill> data = new ArrayList<Bill>();
            for (int i = 0; i < bill_list.size(); i++) {
                if (bill_list.get(i).getDate().equals(date)) {
                    assert data != null;
                    data.add(bill_list.get(i));
                }
            }
            return data;
    }


}
