package com.example.accountbook.setting;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.accountbook.R;

public class ChangeCodeDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    private TextView text_cancel;
    private Button btn_change_code;
    private Button btn_close_code;
    private ChangeCodeDialogListener changeCodeDialogListener;

    public ChangeCodeDialog(@NonNull Context context, ChangeCodeDialogListener changeCodeDialogListener) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.changeCodeDialogListener = changeCodeDialogListener;
        initView();
    }

    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.change_code_dialog, null);
        text_cancel = view.findViewById(R.id.txt_cancel);
        btn_change_code = view.findViewById(R.id.btn_change_code);
        btn_close_code = view.findViewById(R.id.btn_close_code);
        text_cancel.setOnClickListener(this);
        btn_change_code.setOnClickListener(this);
        btn_close_code.setOnClickListener(this);
        super.setContentView(view);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_code:
                ChangeCodeDialog.this.cancel();
                changeCodeDialogListener.clickBtnChangeCode();
                dismiss();
                break;
            case R.id.btn_close_code:
                ChangeCodeDialog.this.cancel();
                changeCodeDialogListener.clickBtnCloseCode();
                dismiss();
                break;
            case R.id.txt_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }
}