package com.example.accountbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button mDelItemBtn;
    private RecyclerView mRecyclerView;
    private NormalAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NormalAdapter.OnItemClickListener mListItemClick_Listener;
    private NormalAdapter.OnItemClickListener mButtonClick_Listener;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper.Callback mItemTouchCallBack;
    private List<Bill> bill_list = new ArrayList<Bill>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initAction();

    }


    public void initData() {
        getData();
        mAdapter = new NormalAdapter(bill_list);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter.setOnItemClickListener(new NormalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"click " + position + " item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this,"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initView() {
        mDelItemBtn = (Button) findViewById(R.id.listItem_delete_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        // 设置Item之间间隔样式
        //mRecyclerView.addItemDecoration(new MDLinearRvDividerDecoration(this, LinearLayoutManager.VERTICAL));

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
//        mDelItemBtn.setOnClickListener(new mOnBtnClickListener());
//        ItemTouchHelper helper = new ItemTouchHelper(new SimpleItemTouchCallback(mAdapter, bill_list));
//        helper.attachToRecyclerView(mRecyclerView);
//        mHelper = new ItemTouchHelper(new SimpleItemTouchCallback(mAdapter, bill_list));
//        mHelper.attachToRecyclerView(mRecyclerView);
        mItemTouchHelper = new ItemTouchHelper(new SimpleItemTouchCallback(mAdapter, bill_list));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
//    //按钮点击事件监听
//    class mOnBtnClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            int id = v.getId();
//            if(id == R.id.listItem_delete_btn){
//                mAdapter.deleteItem();
//                mLayoutManager.scrollToPosition(0);
//            }
//        }
//    }

}
