package com.example.accountbook.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.Bill;
import com.example.accountbook.R;

import java.util.ArrayList;
import java.util.List;

import cn.we.swipe.helper.WeSwipeHelper;
import cn.we.swipe.helper.WeSwipeProxyAdapter;

public class NormalAdapter extends WeSwipeProxyAdapter<NormalAdapter.ViewHolder> {


    private NormalAdapter.OnItemClickListener onItemClickListener;
    private DeletedItemListener delectedItemListener;
    //创建ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder implements WeSwipeHelper.SwipeLayoutTypeCallBack {
        public final TextView describe;
        public final TextView date;
        public final TextView money;
        public final ConstraintLayout itemView;
        public final LinearLayout content;
        public final TextView delete;
        public ViewHolder(View v) {
            super(v);
            describe = (TextView) v.findViewById(R.id.describe);
            money = (TextView) v.findViewById(R.id.money);
            date = (TextView) v.findViewById(R.id.date);
            itemView = (ConstraintLayout) v.findViewById(R.id.list_item);
            content = (LinearLayout) v.findViewById(R.id.listItem_content);
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
    private List<Bill> mData;
    public NormalAdapter(List<Bill> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    //绑定数据
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.describe.setText(mData.get(position).getBillCategory());
        holder.money.setText(mData.get(position).getMoney());
        holder.date.setText(mData.get(position).getDate());
        //列表项点击事件
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    if(onItemClickListener != null) {
//                        int pos = holder.getLayoutPosition();
//                        onItemClickListener.onItemClick(holder.itemView, pos);
//                    }
//                }
//            });
//        //列表项长按事件（查看详情）
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if(onItemClickListener != null) {
//                        int pos = holder.getLayoutPosition();
//                        onItemClickListener.onItemLongClick(holder.itemView, pos);
//                    }
//                    //表示此事件已经消费，不会触发单击事件
//                    return true;
//                }
//            });
        //删除按钮点击事件
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                delectedItemListener.deleted(pos);
                //mData.remove(pos);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //实例化view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        //实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public void addNewItem(Bill newBill) {
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

    /**
     * 设置回调监听
     */
    public void setOnItemClickListener(NormalAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

//    public void setOnStartDragListener(NormalAdapter.OnStartDragListener listener){
//        this.onStartDragListener = listener;
//    }

    public void setDeleteItemListener(DeletedItemListener deletedItemListener) {
        this.delectedItemListener = deletedItemListener;
    }

    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    //接口
    public interface OnStartDragListener{
        void startDrag(RecyclerView.ViewHolder holder);
    }
    public interface DeletedItemListener {
        void deleted(int position);
    }


}