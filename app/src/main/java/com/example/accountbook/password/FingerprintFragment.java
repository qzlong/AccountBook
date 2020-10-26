package com.example.accountbook.password;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.accountbook.R;
import com.example.accountbook.setting.CustomDialogClickListener;
import com.example.accountbook.setting.FingerprintDialog;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

import java.util.Calendar;

public class FingerprintFragment extends Fragment {
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fingerprint_layout, container, false);
        context = getActivity();
        final FingerprintIdentify fingerprintIdentify = new FingerprintIdentify(context);
        fingerprintIdentify.setSupportAndroidL(true);              // 支持安卓L及以下系统
        fingerprintIdentify.init();

        final FingerprintDialog fingerprintDialog = new FingerprintDialog(context, new CustomDialogClickListener() {
            @Override
            public void clickConfirm() {
                //do nothing
            }

            @Override
            public void clickCancel() {
                fingerprintIdentify.cancelIdentify();
                Toast.makeText(context,"指纹解锁功能已关闭",Toast.LENGTH_SHORT).show();
            }
        });
        fingerprintDialog.show();

        fingerprintIdentify.startIdentify(Integer.MAX_VALUE, new BaseFingerprint.IdentifyListener() {
            @Override
            public void onSucceed() {
                getActivity().finish();
            }

            @Override
            public void onNotMatch(int availableTimes) {
                fingerprintDialog.setMessage("匹配失败,请重试");
            }

            @Override
            public void onFailed(boolean isDeviceLocked) {
                fingerprintDialog.setMessage("指纹解锁已被锁定，请尝试其他方法");
            }

            @Override
            public void onStartFailedByDeviceLocked() {
                fingerprintDialog.setMessage("指纹解锁已被锁定，请尝试其他方法");
            }
        });
        return view;
    }
}