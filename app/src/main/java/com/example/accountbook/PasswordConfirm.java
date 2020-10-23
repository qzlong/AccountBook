package com.example.accountbook;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordConfirm extends AppCompatActivity {
    private SharedPreferences InputPassword;
    private EditText edit_input_password;
    private TextView out;
    private Button btn_password_confirm;
    private Button show;
    private Boolean isSetCode = false;
    private Boolean isSetTextCode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_confirm);
        setTitle("密码登录");
        edit_input_password = (EditText) this.findViewById(R.id.edit_input_password);
        btn_password_confirm = (Button) this.findViewById(R.id.btn_password_confirm);
        show = (Button) this.findViewById(R.id.layout);
        out = (TextView) this.findViewById(R.id.showtext);
        InputPassword = super.getSharedPreferences("test", MODE_PRIVATE);
    }

    public void SetPassword(View view) {
        SharedPreferences.Editor editor = InputPassword.edit();
        editor.putString("name", edit_input_password.getText().toString());
        editor.apply();
        isSetCode = true;
        isSetTextCode = true;
        Toast.makeText(this, "密码设置成功！", Toast.LENGTH_SHORT).show();
    }

    public void ConfirmPassword(View view) {
        String info = InputPassword.getString("name", "");
        String nowinfo = in.getText().toString();
        boolean match = false;
        if (isSetCode && isSetTextCode) {
            if (!TextUtils.isEmpty(nowinfo)) {
                if (nowinfo.equals(info)) {
                    match = true;
                }
                if (match) {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, PasswordConfirm.class));
                    finish();
                } else {
                    Toast.makeText(this, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "输入密码不能为空", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "未设置密码", Toast.LENGTH_SHORT).show();
        }
    }

    public void readInfo(View view) {
        String password = InputPassword.getString("name", "");
        out.setText(password);
    }
}
