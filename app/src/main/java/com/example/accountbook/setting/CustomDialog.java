package com.example.accountbook.setting;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.accountbook.R;

public class CustomDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    private TextView btn_sure;
    private TextView btn_cancel;
    private TextView title;
    private TextView message;
    private CustomDialogClickListener customDialogClickListener;

    public CustomDialog(@NonNull Context context, CustomDialogClickListener clickListener) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.customDialogClickListener = clickListener;
        initView();
    }

    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.customdialog, null);
        title = view.findViewById(R.id.title);
        message = view.findViewById(R.id.message);
        btn_sure = view.findViewById(R.id.dialog_confirm_sure);
        btn_cancel = view.findViewById(R.id.dialog_confirm_cancel);
        btn_sure.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        super.setContentView(view);
    }


    public CustomDialog setTile(String s) {
        title.setText(s);
        return this;
    }


    public CustomDialog setMessage(String s) {
        message.setText(s);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_confirm_sure:
                CustomDialog.this.cancel();
                customDialogClickListener.clickConfirm();
                dismiss();
                break;
            case R.id.dialog_confirm_cancel:
                CustomDialog.this.cancel();
                customDialogClickListener.clickCancel();
                dismiss();
                break;
        }
    }
}