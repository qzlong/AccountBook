package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.adapter.RVAdapter;
import com.example.accountbook.adapter.mSpinnerAdapter;
import com.example.accountbook.bean.Detail;
import com.example.accountbook.decoration.DividerDecoration;

import org.litepal.LitePal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.we.swipe.helper.WeSwipe;
import cn.we.swipe.helper.WeSwipeHelper;

public class StatisticsActivity extends AppCompatActivity {
    //RecyclerView
    private RecyclerView mRecyclerView;
    private RVAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RVAdapter.OnItemClickListener mListItemClickListener;
    private RVAdapter.DeletedItemListener mDeletedItemListener;
    //Spinner
    private mSpinnerAdapter<CharSequence> mSpinnerAdapter1;
    private mSpinnerAdapter<CharSequence> mSpinnerAdapter2;
    private mSpinnerAdapter<CharSequence> mSpinnerAdapter3;
    private Spinner mSpinner1;
    private AdapterView.OnItemSelectedListener mItemSelectListener1;
    private Spinner mSpinner2;
    private AdapterView.OnItemSelectedListener mItemSelectListener2;
    private Spinner mSpinner3;
    private AdapterView.OnItemSelectedListener mItemSelectListener3;
    //AccountList data
    //private List<Bill> bill_list = new ArrayList<Bill>();
    private List<Detail> bill_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Intent intent = getIntent();
        setTitle("收支详情");
        initData();
        initAction();
        initView();

    }
    public void initData() {
        getData();
        mAdapter = new RVAdapter(bill_list);
        mLayoutManager = new LinearLayoutManager(StatisticsActivity.this);
        mSpinnerAdapter1 = mSpinnerAdapter.createFromResource(StatisticsActivity.this, R.array.time, R.layout.spinner_item);
        mSpinnerAdapter2 = mSpinnerAdapter.createFromResource(StatisticsActivity.this, R.array.categories, R.layout.spinner_item);
        mSpinnerAdapter3 = mSpinnerAdapter.createFromResource(StatisticsActivity.this, R.array.account, R.layout.spinner_item);
    }

    @SuppressLint("SetTextI18n")
    public void initView() {
        //RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.accountList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        WeSwipe weSwipe = WeSwipe.attach(mRecyclerView).setType(WeSwipeHelper.SWIPE_ITEM_TYPE_FLOWING);
        mAdapter.setWeSwipe(weSwipe);
        mRecyclerView.addItemDecoration(new DividerDecoration(StatisticsActivity.this, LinearLayoutManager.VERTICAL));
        mAdapter.setDeleteItemListener(mDeletedItemListener);
        mAdapter.setOnItemClickListener(mListItemClickListener);

        //Spinner
        setSpinner(mSpinnerAdapter1, mItemSelectListener1, R.id.statis_spinner_time);
        setSpinner(mSpinnerAdapter2, mItemSelectListener2, R.id.statis_spinner_category);
        setSpinner(mSpinnerAdapter3, mItemSelectListener2, R.id.statis_spinner_account);

        //summary
        TextView sum = (TextView) findViewById(R.id.detail_sum);
        int current_month = Calendar.getInstance().get(Calendar.MONTH);
        sum.setText(getSum(0, current_month) + "");

    }
    private void getData() {//初始化数据
        bill_list = LitePal.findAll(Detail.class);
    }

    public void initAction() {
        //长按列表项
        mListItemClickListener = (new RVAdapter.OnItemClickListener() {
            //@Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(MainActivity.StatisticsActivity.this,"click " + position + " item", Toast.LENGTH_SHORT).show();
//            }
            @Override
            //长按列表项动作
            public void onItemLongClick(View view, int position) {
                Intent intent = new Intent(StatisticsActivity.this, BillDetailActivity.class);
                intent.putExtra("bill", (Serializable) bill_list.get(position));
                startActivityForResult(intent, 1);

            }
        });
        //删除按钮
        mDeletedItemListener = new RVAdapter.DeletedItemListener() {
            @Override
            //点击删除键动作
            public void deleted(int position) {
                bill_list.get(position).delete();
            }
        };
        //时间选择
        mItemSelectListener1 = new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter1.setSelectedPostion(position);
                TextView detail_sum = (TextView) findViewById(R.id.detail_sum);
                float sum = 0;
                String[] times = getResources().getStringArray(R.array.time);
                String date = times[position];
                List<Detail> tmp_list = null;
                int current_month = Calendar.getInstance().get(Calendar.MONTH);
                int start_month = 0;
                if (current_month > 6) {
                    start_month = current_month - 6;
                }
                switch (date) {
                    case "本月":
                        tmp_list = getDataByMonth(current_month);
                        sum = getSum(current_month, current_month);
                        break;
                    case "上月":
                        tmp_list = getDataByMonth(current_month - 1);
                        sum = getSum(current_month - 1, current_month - 1);
                        break;
                    case "最近半年" :
                        tmp_list = getDataByMonth(start_month, current_month);
                        sum = getSum(start_month, current_month);
                        break;
                    case "最近一年":
                        tmp_list = getDataByMonth(0, 11);
                        sum = getSum(0, 11);
                    default:
                        tmp_list = bill_list;
                }
                mAdapter.setData(tmp_list);
                detail_sum.setText(sum + "");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    getData();
                    mAdapter.setData(bill_list);
                }
                break;
            default:
        }
    }
    public int dip2px(Context context, float dipValue) {
        float m=context.getResources().getDisplayMetrics().density ;
        return (int)(dipValue * m + 0.5f) ;
    }
    public void setSpinner(mSpinnerAdapter<CharSequence> adapter, AdapterView.OnItemSelectedListener listener, int id) {
        Spinner spinner = (Spinner) findViewById(id);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setDropDownVerticalOffset(dip2px(StatisticsActivity.this,40));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listener);
    }

    public List<Detail> getDataByMonth(int month) {
        List<Detail> data = new ArrayList<>();
        for (Detail bill : bill_list) {
            if (bill.getTime().get(Calendar.MONTH) == month) {
                data.add(bill);
            }
        }
        return data;
    }
    private List<Detail> getDataByMonth(int start_month, int current_month) {
        List<Detail> data = new ArrayList<>();
        for (Detail bill : bill_list) {
            int month = bill.getTime().get(Calendar.MONTH);
            if (month >= start_month && month <= current_month) {
                data.add(bill);
            }
        }
        return data;
    }
    public float getSum(int startTime, int endTime) {
        float sum = 0;
        for (Detail bill : bill_list) {
            int month = bill.getTime().get(Calendar.MONTH);
            if (month >= startTime && month <= endTime) {
                sum += bill.getMoney();
            }
        }
        return sum;
    }

}
