package com.example.accountbook.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.R;
import com.example.accountbook.bean.Detail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.we.swipe.helper.WeSwipeHelper;
import cn.we.swipe.helper.WeSwipeProxyAdapter;

public class RVAdapter extends WeSwipeProxyAdapter<RVAdapter.ViewHolder> {
    public static final int ITEM_TYPE_CONTENT = 0;
    public static final int ITEM_TYPE_HEADER = 1;
    private int mHeaderCount = 1;
    private int mExpandedPosition = -1;
    private OnItemClickListener onItemClickListener;
    private DeletedItemListener delectedItemListener;
    //创建ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder implements WeSwipeHelper.SwipeLayoutTypeCallBack {
        public final TextView describe;
        public final TextView date;
        public final TextView money;
        public final ConstraintLayout itemView;
        public final LinearLayout content;
        public final TextView delete;
        public final TextView type;
        public final TextView cate1;
        public ViewHolder(View v) {
            super(v);
            describe = (TextView) v.findViewById(R.id.describe);
            money = (TextView) v.findViewById(R.id.money);
            date = (TextView) v.findViewById(R.id.date);
            itemView = (ConstraintLayout) v.findViewById(R.id.list_item);
            content = (LinearLayout) v.findViewById(R.id.listItem_content);
            type = (TextView) v.findViewById(R.id.type);
            cate1 = (TextView) v.findViewById(R.id.cate1);
            delete = (TextView) v.findViewById(R.id.listItem_delete);
        }
        @Override
        public float getSwipeWidth() {
            return delete.getWidth();
        }

        @Override
        public View needSwipeLayout() {
            return itemView;
        }

        @Override
        public View onScreenView() {
            return content;
        }
    }
    //读取数据
    private List<Detail> mData;
    public RVAdapter(List<Detail> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    //绑定数据
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
            Calendar dateCal = mData.get(position).getTime();
            String dateStr = (dateCal.get(Calendar.MONTH) + 1) + "月" + dateCal.get(Calendar.DATE) + "日";
            holder.date.setText(dateStr);
            holder.describe.setText(mData.get(position).getCategory2());
            holder.cate1.setText(mData.get(position).getCategory1());
            String moneyStr = String.valueOf(mData.get(position).getMoney());
            holder.money.setText(moneyStr);

            String type = mData.get(position).getType();
            if (type.equals("PAY")){
                holder.type.setText("支出");
                holder.money.setTextColor(Color.parseColor("#32CD32"));
            }else if(type.equals("INCOME")) {
                holder.type.setText("收入");
                holder.money.setTextColor(Color.parseColor("#B22222"));
                holder.describe.setText(mData.get(position).getCategory2());
            }else if(type.equals("LOAN")) {
                holder.describe.setText("借贷");
                holder.type.setText(mData.get(position).getCategory1());
                holder.cate1.setText(mData.get(position).getCategory2());
            }else if(type.equals("TRANSFER")) {
                holder.describe.setText("转账");
                holder.type.setText(mData.get(position).getAccount1());
                holder.cate1.setText(mData.get(position).getAccount2());
            }

            //        列表项点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if(onItemClickListener != null) {
                        int pos = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, pos);
                    }
                }
            });
            //删除按钮点击事件
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    delectedItemListener.deleted(pos);
                    mData.remove(pos);
                    proxyNotifyItemRemoved(pos);
                }
            });



    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @NonNull
    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //实例化view
//        View v = null;
//        if (viewType == ITEM_TYPE_HEADER){
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
//            HeaderViewHolder viewHolder = new RVAdapter.HeaderViewHolder(v);
//            return viewHolder;
//        }else if (viewType == ITEM_TYPE_CONTENT) {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
//            ContentViewHolder viewHolder = new RVAdapter.ContentViewHolder(v);
//            return viewHolder;
//        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new RVAdapter.ViewHolder(v);
    }


    public void addNewItem(Detail newBill) {
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(0, newBill);
        notifyItemInserted(0);
    }

    public void deleteItem(int position) {
        if(mData == null || mData.isEmpty()) {
            return;
        }
        mData.remove(position);
        proxyNotifyItemRemoved(position);
    }
     public void setData(List<Detail> data) {
        if (mData.equals(data)) {
            return;
        }
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
        this.onItemClickListener = listener;
    }

    public void setDeleteItemListener(DeletedItemListener deletedItemListener) {
        this.delectedItemListener = deletedItemListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface DeletedItemListener {
        void deleted(int position);
    }


}