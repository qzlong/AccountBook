package com.example.accountbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.setting.CustomEditDialog;
import com.example.accountbook.setting.MailWithAttachmentThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder>{
    private List<Option> mOptionList;
    private Context context;
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
    public OptionAdapter(List<Option> optionList,Context context){
        this.mOptionList = optionList;
        this.context = context;
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
                try {
                    Click(v,position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return holder;
    }

    public void Click(View v, int position) throws IOException {
        switch (position){
            //case 1-3 为定时事件
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
                CustomEditDialog customEditDialog = new CustomEditDialog(context);
                customEditDialog.setTile("绑定邮箱");
                final EditText editMail = (EditText) customEditDialog.getEmail();
                final EditText editCode = (EditText) customEditDialog.getCode();
                customEditDialog.show();
                break;
            case 10: //数据导出

                String rootPath = "data/data/com.example.accountbook/a.txt";
                boolean file_b = new File(rootPath).createNewFile();
                FileOutputStream fop = new FileOutputStream(rootPath);
                // 构建FileOutputStream对象,文件不存在会自动新建
                OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
                // 构建OutputStreamWriter对象,参数可以指定编码,默认为操作系统默认编码,windows上是gbk
                writer.append("中文输入");
                // 写入到缓冲区
                writer.append("\r\n");
                // 换行
                writer.append("English");
                // 刷新缓存冲,写入到文件,如果下面已经没有写入的内容了,直接close也会写入
                writer.close();
                // 关闭写入流,同时会把缓冲区内容写入文件,所以上面的注释掉
                fop.close();
                // 关闭输出流,释放系统资源
                MailWithAttachmentThread mailWithAttachmentThread = new MailWithAttachmentThread(context,"a929482132@126.com",new File(rootPath));
                mailWithAttachmentThread.start();
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