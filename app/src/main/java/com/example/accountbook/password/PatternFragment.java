package com.example.accountbook.password;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;

import com.example.accountbook.R;

import java.util.List;
public class PatternFragment extends Fragment {
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private PatternLockView mPatternLockView;
    private String password;
    private TextView text_warn;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.pattern_layout, container, false);
        this.mContext = getActivity();
        this.sharedPreferences = mContext.getSharedPreferences("setting",Context.MODE_PRIVATE);
        this.password = sharedPreferences.getString("patternCode","");
        text_warn = view.findViewById(R.id.text_warn);
        initPatternView(view);
        return view;
    }

    private void initPatternView(View view) {
        mPatternLockView = view.findViewById(R.id.pattern_lock_view);
        mPatternLockView.setDotCount(3);
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(mContext, R.dimen.pattern_lock_dot_size));
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(mContext, R.dimen.pattern_lock_dot_selected_size));
        mPatternLockView.setNormalStateColor(ResourceUtils.getColor(mContext,R.color.dot_black));
        mPatternLockView.setWrongStateColor(ResourceUtils.getColor(mContext,R.color.dot_red));
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(mContext, R.dimen.pattern_lock_path_width));
        mPatternLockView.setAspectRatioEnabled(true);
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150);
        mPatternLockView.setPathEndAnimationDuration(100);
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(mContext,R.color.dot_green));
        mPatternLockView.setInStealthMode(false);
        mPatternLockView.setTactileFeedbackEnabled(true);
        mPatternLockView.setInputEnabled(true);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                String password_input = PatternLockUtils.patternToString(mPatternLockView, pattern);
                if(password_input.equals(password)){
                    mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(mContext,R.color.dot_green));
                    getActivity().finish();
                }else{
                    mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(mContext,R.color.dot_red));
                    text_warn.setText("密码错误,请重新输入");
                }
            }

            @Override
            public void onCleared() {

            }
        });

    }
}