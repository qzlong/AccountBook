package com.example.accountbook;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.adapter.NormalAdapter;

import java.util.Collections;
import java.util.List;

public class SimpleItemTouchCallback extends ItemTouchHelper.Callback {

    private NormalAdapter mAdapter;
    private List<Bill> mData;
    public SimpleItemTouchCallback(NormalAdapter adapter, List<Bill> data){
        mAdapter = adapter;
        mData = data;
    }
    //是否支持侧滑
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
    //设置支持的拖拽、滑动的方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN; //s上下拖拽
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END; //左->右和右->左滑动
        return makeMovementFlags(dragFlag,swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(mData, from, to);
        mAdapter.notifyItemMoved(from, to);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        mData.remove(pos);
        mAdapter.notifyItemRemoved(pos);
    }

    //状态改变时回调
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            NormalAdapter.ViewHolder holder = (NormalAdapter.ViewHolder)viewHolder;
            holder.itemView.setBackgroundColor(0xffbcbcbc); //设置拖拽和侧滑时的背景色
        }
    }

    //拖拽或滑动完成之后调用，用来清除一些状态
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        NormalAdapter.ViewHolder holder = (NormalAdapter.ViewHolder)viewHolder;
        holder.itemView.setBackgroundColor(0xffeeeeee); //背景色还原
    }



}