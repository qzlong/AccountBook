package com.example.accountbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.cardemulation.CardEmulation;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.ChartAnalysisActivity;
import com.example.accountbook.R;
import com.example.accountbook.helper.CsvIOHelper;
import com.example.accountbook.setting.AddTextCodeDialog;
import com.example.accountbook.setting.CustomDialog;
import com.example.accountbook.setting.CustomDialogClickListener;
import com.example.accountbook.setting.CustomEditDialog;
import com.example.accountbook.setting.MailWithAttachmentThread;
import com.example.accountbook.setting.Option;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> implements SharedPreferences.OnSharedPreferenceChangeListener{
    private List<Option> mOptionList;
    private Context context;
    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor editor;
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
        this.sharedPreferences = context.getSharedPreferences("setting",Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.optionView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Click(View v, int position) throws IOException {
        switch (position){
            //case 1-3 为定时事件
            case 1: //自动记账
                //点击示例
                Toast.makeText(v.getContext(),"自动记账",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), ChartAnalysisActivity.class);
                v.getContext().startActivity(intent);
                break;
            case 2: //记账提醒
                Toast.makeText(v.getContext(),"记账提醒",Toast.LENGTH_SHORT).show();
                break;
            case 3: //自动备份
                Toast.makeText(v.getContext(),"自动备份",Toast.LENGTH_SHORT).show();
                break;

            case 5: //指纹密码
                boolean isOpenFingerprintCode = sharedPreferences.getBoolean("isOpenFingerprintCode",false);
                if(!isOpenFingerprintCode) {//未设置指纹
                    enableFingerprintCode();
                }else{
                    CustomDialog customDialog = new CustomDialog(context, new CustomDialogClickListener() {
                        @Override
                        public void clickConfirm() {
                            editor.putBoolean("isOpenFingerprintCode",false);
                            Toast.makeText(context,"指纹密码已关闭",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void clickCancel() {
                            //do nothing
                        }
                    });
                }
                break;
            case 6: //文本密码
                boolean isSetTextCode = sharedPreferences.getBoolean("isSetTextCode",false);
                if(!isSetTextCode){
                    setTextCode();
                }else{

                }
                break;
            case 7: //图形密码
                boolean isSetPatternCode = sharedPreferences.getBoolean("isSetPatternCode",false);
                if(!isSetPatternCode){
                    //TODO
                }else{
                    //TODO
                }
                break;

            case 9: //绑定邮箱
                boolean isSetEmailAddress = sharedPreferences.getBoolean("isSetEmailAddress",false);
                if(!isSetEmailAddress) {
                    final CustomEditDialog customEditDialog = new CustomEditDialog(context,editor);
                    customEditDialog.setTile("绑定邮箱");
                    customEditDialog.show();
                }else{
                    CustomDialog customDialog = new CustomDialog(context, new CustomDialogClickListener() {
                        @Override
                        public void clickConfirm() {
                            editor.putBoolean("isSetEmailAddress",false);
                            editor.apply();
                            Toast.makeText(context,"邮箱已解除绑定",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void clickCancel() {
                            //Do nothing
                        }
                    });
                    customDialog.setTile("解除绑定");
                    customDialog.setMessage("确定取消绑定该邮箱？");
                    customDialog.show();
                }
                break;

            case 10: //数据导出
                dataOutput(v);
                break;

            case 11: //数据导入
                Toast.makeText(v.getContext(),"数据导入",Toast.LENGTH_SHORT).show();
                break;

            case 13: //清空账单
                CustomDialog customDialog = new CustomDialog(context, new CustomDialogClickListener() {
                    @Override
                    public void clickConfirm() {
                        //TODO
                    }
                    @Override
                    public void clickCancel() {
                        //Do nothing
                    }
                }).setMessage("确定删除所有账单数据？该操作不可逆!!").setTile("警告");
                customDialog.show();
                break;
            default:
        }
    }

    private void setTextCode() {
        AddTextCodeDialog addTextCodeDialog = new AddTextCodeDialog(context,editor);
        addTextCodeDialog.show();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void dataOutput(View v){
        String rootPath = "data/data/com.example.accountbook/";
        Calendar calendar = Calendar.getInstance();
        String filename = calendar.get(Calendar.YEAR) +"_"+calendar.get(Calendar.MONTH)+"_"+calendar.get(Calendar.DAY_OF_MONTH)+
                "_"+calendar.get(Calendar.HOUR_OF_DAY)+"_"+calendar.get(Calendar.MINUTE)+"_"+calendar.get(Calendar.SECOND)+".csv";
        String path = rootPath+filename;
        CsvIOHelper csvIOHelper = new CsvIOHelper();
        boolean createFile = csvIOHelper.writeDetail(path);
        if(createFile) {
            MailWithAttachmentThread mailWithAttachmentThread = new MailWithAttachmentThread(context, "a929482132@126.com", new File(path));
            mailWithAttachmentThread.start();
            Toast.makeText(v.getContext(), "文件已发送至您的邮箱", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(v.getContext(), "文件导出失败", Toast.LENGTH_SHORT).show();
        }
    }
    private void enableFingerprintCode(){
        FingerprintIdentify mFingerprintIdentify = new FingerprintIdentify(context);
        mFingerprintIdentify.setSupportAndroidL(true);
        mFingerprintIdentify.setExceptionListener(new BaseFingerprint.ExceptionListener() {
            @Override
            public void onCatchException(Throwable exception) {

            }
        });
        mFingerprintIdentify.init();
        boolean isHardwareEnable = mFingerprintIdentify.isHardwareEnable();
        if(!isHardwareEnable){
            Toast.makeText(context,"您的设备不支持指纹解锁功能",Toast.LENGTH_SHORT).show();
            return;
        }
        boolean isRegisteredFingerprint = mFingerprintIdentify.isRegisteredFingerprint();
        if(!isRegisteredFingerprint){
            Toast.makeText(context,"您未在您的设备中录入指纹，请先录入指纹",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}