package com.example.accountbook;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

import java.util.List;

public class PasswordConfirm extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences preferences;
    private EditText edit_input_password;
    private Button btn_password_confirm;
    private Button btn_select_text_code;
    private Button btn_select_graph_code;
    private Button btn_select_fingerprint_code;
    private String password_from_preference;
    private String password_input;
    private boolean isSetTextCode;
    private boolean isSetGraphCode;
    private boolean isSetFingerprintCode;
    private PatternLockView patternLockView;
    private FingerprintIdentify fingerprintIdentify = new FingerprintIdentify(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_confirm);
        initView();
        getDefaultValue();
        setBtnListener();

    }

    private void setBtnListener() {

        btn_select_text_code.setOnClickListener(this);
        btn_select_graph_code.setOnClickListener(this);
        btn_select_fingerprint_code.setOnClickListener(this);
        btn_password_confirm.setOnClickListener(this);
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

            }

            @Override
            public void onCleared() {

            }
        });
    }

    private void getDefaultValue() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        password_from_preference = preferences.getString("text_password",null);
        isSetFingerprintCode = preferences.getBoolean("is_set_fingernail_code",false);
        isSetTextCode = preferences.getBoolean("is_set_text_code",false);
        isSetGraphCode = preferences.getBoolean("is_set_graph_code",false);
    }

    private void initView() {
        fingerprintIdentify.init();
        patternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        edit_input_password = (EditText) this.findViewById(R.id.edit_input_password);
        btn_password_confirm = (Button) this.findViewById(R.id.btn_password_confirm);
        btn_select_fingerprint_code = (Button) findViewById(R.id.btn_password_confirm);//
        btn_select_graph_code = (Button) findViewById(R.id.btn_password_confirm);//
        btn_select_text_code = (Button) findViewById(R.id.btn_password_confirm);//
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_password_confirm:
                //文字密码验证
                password_input = edit_input_password.getText().toString();
                boolean match = password_from_preference.equals(password_input);
                if(!match){
                    if(password_input==null |password_input.length()==0){
                        Toast.makeText(this,"输入的密码不能为空",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this,"验证成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                //图形密码验证
                //TODO

                //指纹密码验证
                //TODO

                break;

            case R.id.btn_graph_analysis://切换到文字密码登录模块
                if(!isSetTextCode){
                    Toast.makeText(this,"您未设置文字密码",Toast.LENGTH_SHORT).show();
                    btn_select_text_code.setClickable(false);
                }else{
                    //TODO
                }
                break;
            case R.id.btn_save://切换到图形密码登录模块
                if(!isSetGraphCode){
                    Toast.makeText(this,"您未设置图形密码",Toast.LENGTH_SHORT).show();
                    btn_select_graph_code.setClickable(false);
                }else{
                    //TODO
                }
                break;
            case R.id.btn_keep_account://指纹密码登录模块
                if(!isSetFingerprintCode){
                    Toast.makeText(this,"您未授权使用指纹密码",Toast.LENGTH_SHORT).show();
                    btn_select_fingerprint_code.setClickable(false);
                }else{
                    //TODO
                    fingerprintIdentify.startIdentify(Integer.MAX_VALUE, new BaseFingerprint.IdentifyListener() {
                        @Override
                        public void onSucceed() {
                            Toast.makeText(PasswordConfirm.this,"验证成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onNotMatch(int availableTimes) {
                            Toast.makeText(PasswordConfirm.this,"验证失败",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed(boolean isDeviceLocked) {

                        }

                        @Override
                        public void onStartFailedByDeviceLocked() {
                            Toast.makeText(PasswordConfirm.this,"错误次数过多,请稍后再试",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    //重写keyEvent，防止用户使用Back返回MainActivity
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            //do nothing
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
