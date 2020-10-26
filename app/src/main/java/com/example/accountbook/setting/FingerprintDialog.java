package com.example.accountbook.setting;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.accountbook.R;

public class FingerprintDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    private TextView btn_cancel;
    private TextView message;
    private CustomDialogClickListener customDialogClickListener;

    public FingerprintDialog(@NonNull Context context, CustomDialogClickListener clickListener) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.customDialogClickListener = clickListener;
        initView();
    }

    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fingerprint_dialog_layout, null);
        message = view.findViewById(R.id.message);
        btn_cancel = view.findViewById(R.id.dialog_confirm_cancel);
        btn_cancel.setOnClickListener(this);
        super.setContentView(view);
    }




    public FingerprintDialog setMessage(String s) {
        message.setText(s);
        message.setVisibility(View.VISIBLE);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_confirm_cancel:
                FingerprintDialog.this.cancel();
                customDialogClickListener.clickCancel();
                dismiss();
                break;
        }
    }
}