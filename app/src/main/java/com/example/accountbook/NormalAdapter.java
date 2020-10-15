package com.example.accountbook;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.Bill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.ViewHolder>{


    private NormalAdapter.OnItemClickListener onItemClickListener;
    private OnStartDragListener onStartDragListener;
    //创建ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView describe;
        public final TextView date;
        public final TextView money;
        public final LinearLayout itemView;
        public ViewHolder(View v) {
            super(v);
            describe = (TextView) v.findViewById(R.id.describe);
            money = (TextView) v.findViewById(R.id.money);
            date = (TextView) v.findViewById(R.id.date);
            itemView = (LinearLayout) v.findViewById((R.id.list_item));
        }
    }
    //读取数据
    private List<Bill> mData;
    public NormalAdapter(List<Bill> data) {
        this.mData = data;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.describe.setText(mData.get(position).getBillCategory());
        holder.money.setText(mData.get(position).getMoney());
        holder.date.setText(mData.get(position).getDate());
        //点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if(onItemClickListener != null) {
                        int pos = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, pos);
                    }
                }
            });
        //长按事件
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onItemClickListener != null) {
                        int pos = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, pos);
                    }
                    //表示此事件已经消费，不会触发单击事件
                    return true;
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
        notifyItemRemoved(position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(NormalAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnStartDragListener(NormalAdapter.OnStartDragListener listener){
        this.onStartDragListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public interface OnStartDragListener{
        void startDrag(RecyclerView.ViewHolder holder);
    }


}