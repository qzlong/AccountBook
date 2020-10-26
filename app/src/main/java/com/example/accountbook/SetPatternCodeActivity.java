package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;

import java.util.List;

public class SetPatternCodeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView text_warn;
    private PatternLockView mPatternLockView;
    private int times = 0;
    private String input_first;
    private String input_second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pattern_code);
        sharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        text_warn = findViewById(R.id.text_warn);
        text_warn.setText("请绘制手势密码");
        text_warn.setTextColor(ResourceUtils.getColor(SetPatternCodeActivity.this,R.color.text_black));
        initPatternView();
    }
    private void initPatternView() {
        mPatternLockView = findViewById(R.id.pattern_lock_view);
        mPatternLockView.setDotCount(3);
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(SetPatternCodeActivity.this, R.dimen.pattern_lock_dot_size));
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(SetPatternCodeActivity.this, R.dimen.pattern_lock_dot_selected_size));
        mPatternLockView.setNormalStateColor(ResourceUtils.getColor(SetPatternCodeActivity.this,R.color.dot_black));
        mPatternLockView.setWrongStateColor(ResourceUtils.getColor(SetPatternCodeActivity.this,R.color.dot_red));
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(SetPatternCodeActivity.this, R.dimen.pattern_lock_path_width));
        mPatternLockView.setAspectRatioEnabled(true);
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150);
        mPatternLockView.setPathEndAnimationDuration(100);
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(SetPatternCodeActivity.this,R.color.dot_blue));
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
                switch (times){
                    case 0:
                        times++;
                        input_first =  PatternLockUtils.patternToString(mPatternLockView, pattern);
                        if(input_first.length()<4){
                            times--;
                            mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(SetPatternCodeActivity.this,R.color.dot_red));
                            text_warn.setText("至少需要绘制四个点");
                            text_warn.setTextColor(ResourceUtils.getColor(SetPatternCodeActivity.this,R.color.text_red));
                        }else{
                            text_warn.setText("绘制成功，请再次输入密码");
                            text_warn.setTextColor(ResourceUtils.getColor(SetPatternCodeActivity.this,R.color.dot_green));
                            mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(SetPatternCodeActivity.this,R.color.dot_black));
                        }
                        break;
                    case 1:
                        input_second = PatternLockUtils.patternToString(mPatternLockView, pattern);
                        if(input_first.equals(input_second)){
                            mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(SetPatternCodeActivity.this,R.color.dot_green));
                            Toast.makeText(SetPatternCodeActivity.this,"图形密码设置完毕",Toast.LENGTH_SHORT).show();
                            editor.putBoolean("isSetPatternCode",true);
                            editor.putString("patternCode",input_second);
                            editor.apply();
                            finish();
                        }else{
                            text_warn.setTextColor(ResourceUtils.getColor(SetPatternCodeActivity.this,R.color.text_red));
                            text_warn.setText("两次密码不一致，请再次输入密码");
                        }

                }
            }
            @Override
            public void onCleared() {

            }
        });

    }

}