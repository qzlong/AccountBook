package com.example.accountbook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.MainActivity;
import com.example.accountbook.R;
import com.example.accountbook.SetPatternCodeActivity;
import com.example.accountbook.bean.Detail;
import com.example.accountbook.helper.CsvIOHelper;
import com.example.accountbook.setting.AddTextCodeDialog;
import com.example.accountbook.setting.ChangeCodeDialog;
import com.example.accountbook.setting.ChangeCodeDialogListener;
import com.example.accountbook.setting.CustomDialog;
import com.example.accountbook.setting.CustomDialogClickListener;
import com.example.accountbook.setting.EmailEditDialog;
import com.example.accountbook.setting.MailWithAttachmentThread;
import com.example.accountbook.setting.Option;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

import org.litepal.LitePal;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;


public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> implements SharedPreferences.OnSharedPreferenceChangeListener{
    private List<Option> mOptionList;
    private Context context;
    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor editor;
    private Activity activity;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View optionView;
        TextView aboveText;
        TextView belowText;
        public ViewHolder(View view){
            super(view);
            optionView = view;
            aboveText = view.findViewById(R.id.aboveText);
            belowText = view.findViewById(R.id.belowText);
        }
    }
    public OptionAdapter(List<Option> optionList,Context context,Activity activity){
        this.mOptionList = optionList;
        this.context = context;
        this.activity = activity;
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
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            }
        });
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Click(View v, int position) throws URISyntaxException {
        switch (position){
            //case 1-3 为定时事件
//            case 1: //自动记账
//                //点击示例
//                Toast.makeText(v.getContext(),"自动记账",Toast.LENGTH_SHORT).show();
//                break;
//            case 2: //记账提醒
//                Toast.makeText(v.getContext(),"记账提醒",Toast.LENGTH_SHORT).show();
//                break;
//            case 3: //自动备份
//                Toast.makeText(v.getContext(),"自动备份",Toast.LENGTH_SHORT).show();
//                break;

            case 1: //指纹密码
                boolean isOpenFingerprintCode = sharedPreferences.getBoolean("isEnableFingerprintCode",false);
                if(!isOpenFingerprintCode) {//未设置指纹
                    boolean enable = enableFingerprintCode();
                    if(enable){
                        CustomDialog customDialog = new CustomDialog(context, new CustomDialogClickListener() {
                            @Override
                            public void clickConfirm() {
                                editor.putBoolean("isEnableFingerprintCode",true);
                                editor.apply();
                                Toast.makeText(context,"指纹密码已打开",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void clickCancel() {
                                //do nothing
                            }
                        });
                        customDialog.setMessage("确定授权使用指纹密码?");
                        customDialog.setTile("指纹密码授权");
                        customDialog.show();
                    }
                }else{
                    CustomDialog customDialog = new CustomDialog(context, new CustomDialogClickListener() {
                        @Override
                        public void clickConfirm() {
                            editor.putBoolean("isEnableFingerprintCode",false);
                            editor.apply();
                            Toast.makeText(context,"指纹密码已关闭",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void clickCancel() {
                            //do nothing
                        }
                    });
                    customDialog.setTile("解除授权");
                    customDialog.setMessage("确定关闭指纹密码?");
                    customDialog.show();
                }
                break;
            case 2: //文本密码
                boolean isSetTextCode = sharedPreferences.getBoolean("isSetTextCode",false);
                if(!isSetTextCode){
                    setTextCode();
                }else{
                    final ChangeCodeDialog changeCodeDialog = new ChangeCodeDialog(context, new ChangeCodeDialogListener() {
                        @Override
                        public void clickBtnChangeCode() {
                            setTextCode();
                        }

                        @Override
                        public void clickBtnCloseCode() {
                            CustomDialog customDialog =  new CustomDialog(context, new CustomDialogClickListener() {
                                @Override
                                public void clickConfirm() {
                                    editor.putBoolean("isSetTextCode",false);
                                    editor.apply();
                                    Toast.makeText(context,"文字密码已关闭",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void clickCancel() {
                                    //do nothing
                                }
                            });
                            customDialog.setMessage("是否关闭文字密码");
                            customDialog.setTile("关闭密码");
                            customDialog.show();
                        }
                    });
                    changeCodeDialog.show();
                }
                break;
            case 3: //图形密码
                boolean isSetPatternCode = sharedPreferences.getBoolean("isSetPatternCode",false);
                if(!isSetPatternCode){
                    setPatternCode();
                }else{
                    final ChangeCodeDialog changeCodeDialog = new ChangeCodeDialog(context, new ChangeCodeDialogListener() {
                        @Override
                        public void clickBtnChangeCode() {
                            setPatternCode();
                        }

                        @Override
                        public void clickBtnCloseCode() {
                            CustomDialog customDialog =  new CustomDialog(context, new CustomDialogClickListener() {
                                @Override
                                public void clickConfirm() {
                                    editor.putBoolean("isSetPatternCode",false);
                                    editor.apply();
                                    Toast.makeText(context,"图形密码已关闭",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void clickCancel() {
                                    //do nothing
                                }
                            });
                            customDialog.setMessage("是否关闭图形密码");
                            customDialog.setTile("关闭密码");
                            customDialog.show();
                        }
                    });
                    changeCodeDialog.show();
                }
                break;

            case 5: //绑定邮箱
                boolean isSetEmailAddress = sharedPreferences.getBoolean("isSetEmailAddress",false);
                if(!isSetEmailAddress) {
                    final EmailEditDialog emailEditDialog = new EmailEditDialog(context,editor);
                    emailEditDialog.setTile("绑定邮箱");
                    emailEditDialog.show();
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

            case 6: //数据导出
                boolean email = sharedPreferences.getBoolean("isSetEmailAddress",false);
                if(email){
                    dataOutput(v);
                }else{
                    Toast.makeText(context,"请先绑定邮箱",Toast.LENGTH_SHORT).show();
                }
                break;

            case 7: //数据导入
//                String gotten_file_path = dataInput(v);
//                Log.e("SELECTED_PATH", gotten_file_path);
                dataInput(v);
                Toast.makeText(v.getContext(),"数据导入",Toast.LENGTH_SHORT).show();
                break;

            case 9: //清空账单
                CustomDialog customDialog = new CustomDialog(context, new CustomDialogClickListener() {
                    @Override
                    public void clickConfirm() {
                        LitePal.deleteAll(Detail.class,"Money > ?","0");
                        Toast.makeText(context,"所有账单数据已清除",Toast.LENGTH_SHORT).show();
                        activity.finish();
                        activity.startActivity(activity.getIntent());
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

    private void setPatternCode() {
        context.startActivity(new Intent(activity, SetPatternCodeActivity.class));
    }

    private void dataInput(View v) throws URISyntaxException {
        MainActivity mainActivity = (MainActivity) activity;
        mainActivity.chooseFile(3);
//        mainActivity
//        return mainActivity.showBrowser();
//        mainActivity.showBrowser();
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
            String EmailAddress = sharedPreferences.getString("emailAddress",null);
            MailWithAttachmentThread mailWithAttachmentThread = new MailWithAttachmentThread(context, EmailAddress, new File(path));
            mailWithAttachmentThread.start();
            Toast.makeText(v.getContext(), "文件已发送至您的邮箱", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(v.getContext(), "文件导出失败", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean enableFingerprintCode(){
        FingerprintIdentify mFingerprintIdentify = new FingerprintIdentify(context);
        mFingerprintIdentify.setSupportAndroidL(true);
        mFingerprintIdentify.setExceptionListener(new BaseFingerprint.ExceptionListener() {
            @Override
            public void onCatchException(Throwable exception) {
                //Do nothing
            }
        });
        mFingerprintIdentify.init();
        boolean isHardwareEnable = mFingerprintIdentify.isHardwareEnable();
        if(!isHardwareEnable){
            Toast.makeText(context,"您的设备不支持指纹解锁功能",Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean isRegisteredFingerprint = mFingerprintIdentify.isRegisteredFingerprint();
        if(!isRegisteredFingerprint){
            Toast.makeText(context,"您未在您的设备中录入指纹，请先录入指纹",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}