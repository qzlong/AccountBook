package com.example.accountbook.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.R;
import com.example.accountbook.bean.DetailHeader;
import com.example.accountbook.decoration.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.we.swipe.helper.WeSwipe;
import cn.we.swipe.helper.WeSwipeHelper;
import cn.we.swipe.helper.WeSwipeProxyAdapter;

public class RVHeaderAdapter extends WeSwipeProxyAdapter<RVHeaderAdapter.ViewHolder> {
    private RVAdapter.OnItemClickListener childonItemClickListener;
    private RVAdapter.DeletedItemListener childdelectedItemListener;

    //创建ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView remain;
        public final TextView expend;
        public final TextView income;
        public final CardView itemView;
        public final TextView show;
        public final TextView title;
        public final TextView subtitle;
        public RecyclerView recyclerView;
        public ViewHolder(View v) {
            super(v);
            remain = (TextView) v.findViewById(R.id.statis_summary_remain);
            income = (TextView) v.findViewById(R.id.statis_summary_income);
            expend = (TextView) v.findViewById(R.id.statis_summary_expend);
            itemView = (CardView) v.findViewById(R.id.list_header_item);
            title = (TextView) v.findViewById(R.id.Title);
            subtitle = (TextView) v.findViewById(R.id.SubTitle);
            show = (TextView) v.findViewById(R.id.list_header_item_show);
            recyclerView = (RecyclerView) v.findViewById(R.id.newlist);
        }
    }

    //读取数据
    private Context mContext;
    private List<DetailHeader> mData = new ArrayList<>();
    public RVHeaderAdapter(Context context, List<DetailHeader> data) {
        this.mContext = context;
        this.mData.addAll(data);
        notifyDataSetChanged();
    }


    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull final RVHeaderAdapter.ViewHolder holder, int position) {
        RVAdapter mAdapter = new RVAdapter(mData.get(position).getDataList());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.recyclerView.setLayoutManager(mLayoutManager);
        holder.recyclerView.setAdapter(mAdapter);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        WeSwipe weSwipe = WeSwipe.attach(holder.recyclerView).setType(WeSwipeHelper.SWIPE_ITEM_TYPE_FLOWING);
        mAdapter.setWeSwipe(weSwipe);
        holder.recyclerView.addItemDecoration(new DividerDecoration(mContext, LinearLayoutManager.VERTICAL));
        holder.title.setText(mData.get(position).getTitle());
        holder.subtitle.setText(mData.get(position).getSubtitle());
        holder.remain.setText(mData.get(position).getRemain() + "");
        holder.expend.setText(mData.get(position).getExpend() + "");
        holder.income.setText(mData.get(position).getIncome() + "");
        mAdapter.setDeleteItemListener(childdelectedItemListener);
        mAdapter.setOnItemClickListener(childonItemClickListener);

//        列表项点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (holder.show.getText().equals("展示")) {
                    holder.show.setText("收起");
                    holder.show.setTextColor(Color.GRAY);
                    holder.recyclerView.setVisibility(View.VISIBLE);
                } else if(holder.show.getText().equals("收起")) {
                    holder.show.setText("展示");
                    holder.show.setTextColor(Color.parseColor("#219bff"));
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @NonNull
    @Override
    public RVHeaderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header_item, parent, false);
        return new RVHeaderAdapter.ViewHolder(v);
    }

    public void setData(List<DetailHeader> data) {
        mData = data;
        notifyDataSetChanged();
    }
    public void DataChanged() {
        notifyDataSetChanged();
    }
    /**
     * 设置回调监听
     */
    public void setOnItemClickListener(RVAdapter.OnItemClickListener listener) {
        this.childonItemClickListener = listener;
    }

    public void setDeleteItemListener(RVAdapter.DeletedItemListener deletedItemListener) {
        this.childdelectedItemListener = deletedItemListener;
    }



}