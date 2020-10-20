package com.example.accountbook;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder>{
    private List<Option> mOptionList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View optionView;
        TextView aboveText;
        TextView belowText;
        public ViewHolder(View view){
            super(view);
            optionView = view;
            aboveText = (TextView)view.findViewById(R.id.aboveText);
            belowText = (TextView)view.findViewById(R.id.belowText);
        }
    }
    public OptionAdapter(List<Option> optionList){
        mOptionList = optionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.optionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Click(v,position);
            }
        });
        return holder;
    }

    public void Click(View v, int position){
        switch (position){
            case 1: //自动记账
                //点击示例
                Toast.makeText(v.getContext(),"自动记账",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),ChartAnalysis.class);
                v.getContext().startActivity(intent);
                break;
            case 2: //记账提醒
                Toast.makeText(v.getContext(),"记账提醒",Toast.LENGTH_SHORT).show();
                break;
            case 3: //自动备份
                Toast.makeText(v.getContext(),"自动备份",Toast.LENGTH_SHORT).show();
                break;
            case 5: //指纹密码
                Toast.makeText(v.getContext(),"指纹密码",Toast.LENGTH_SHORT).show();
                break;
            case 6: //文本密码
                Toast.makeText(v.getContext(),"文本密码",Toast.LENGTH_SHORT).show();
                break;
            case 7: //图形密码
                Toast.makeText(v.getContext(),"图形密码",Toast.LENGTH_SHORT).show();
                break;
            case 9: //绑定邮箱
                Toast.makeText(v.getContext(),"绑定邮箱",Toast.LENGTH_SHORT).show();
                break;
            case 10: //数据导出
                Toast.makeText(v.getContext(),"数据导出",Toast.LENGTH_SHORT).show();
                break;
            case 11: //数据导入
                Toast.makeText(v.getContext(),"数据导入",Toast.LENGTH_SHORT).show();
                break;
            case 13: //清空账单
                Toast.makeText(v.getContext(),"清空账单",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Option option = mOptionList.get(position);
        holder.aboveText.setText(option.getAboveText());
        holder.aboveText.setTextColor(Color.BLACK);
        holder.belowText.setText(option.getBelowText());
        holder.belowText.setTextColor(Color.GRAY);
        if(option.getAboveText().equals(" ")){
            Log.d("OptionAdapter", option.getAboveText()+option.getBelowText());
            holder.belowText.setTextColor(Color.BLUE);
        }
    }

    @Override
    public int getItemCount(){
        return mOptionList.size();
    }
}