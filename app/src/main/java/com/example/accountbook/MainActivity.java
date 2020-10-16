package com.example.accountbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.accountbook.adapter.NormalAdapter;
import com.example.accountbook.decoration.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.we.swipe.helper.WeSwipe;
import cn.we.swipe.helper.WeSwipeHelper;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NormalAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NormalAdapter.OnItemClickListener mListItemClickListener;
    private NormalAdapter.DeletedItemListener mDeletedItemListener;
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
        mAdapter = new NormalAdapter(bill_list);
        mLayoutManager = new LinearLayoutManager(this);
    }

    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        WeSwipe weSwipe = WeSwipe.attach(mRecyclerView).setType(WeSwipeHelper.SWIPE_ITEM_TYPE_FLOWING);
        mAdapter.setWeSwipe(weSwipe);
        mRecyclerView.addItemDecoration(new DividerDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter.setDeleteItemListener(mDeletedItemListener);
        mAdapter.setOnItemClickListener(mListItemClickListener);
    }
    private void getData() {//初始化数据\
        Bill bill1 = new Bill("1", "20", "1");
        bill_list.add(bill1);
        Bill bill2 = new Bill("2", "20", "1");
        bill_list.add(bill2);
        Bill bill3 = new Bill("3", "20", "1");
        bill_list.add(bill3);
        Bill bill4 = new Bill("4", "20", "1");
        bill_list.add(bill4);
        Bill bill5 = new Bill("5", "20", "1");
        bill_list.add(bill5);
        Bill bill6 = new Bill("1", "20", "1");
        bill_list.add(bill6);
        Bill bill7 = new Bill("2", "20", "1");
        bill_list.add(bill7);
        Bill bill8 = new Bill("3", "20", "1");
        bill_list.add(bill8);
        Bill bill9 = new Bill("4", "20", "1");
        bill_list.add(bill9);
        Bill bill10 = new Bill("5", "20", "1");
        bill_list.add(bill10);

    }

    public void initAction() {
        mListItemClickListener = (new NormalAdapter.OnItemClickListener() {
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

        mDeletedItemListener = new NormalAdapter.DeletedItemListener() {
            @Override
            //点击删除键动作
            public void deleted(int position) {
                mAdapter.deleteItem(position);
            }
        };
    }

}
