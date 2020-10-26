package com.example.accountbook.password;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.accountbook.R;

public class TextFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private String password;
    private EditText edit_input_text_code;
    private Button btn_confirm;
    private TextView text_warn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.text_layout, container, false);
        mContext = getActivity();
        sharedPreferences = mContext.getSharedPreferences("setting",Context.MODE_PRIVATE);
        password = sharedPreferences.getString("textCode",null);
        edit_input_text_code = view.findViewById(R.id.edit_input_text_code);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
        text_warn = view.findViewById(R.id.text_warn);
//        Toast.makeText(mContext,password,Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                String password_input = edit_input_text_code.getText().toString();
                if(password_input.equals(password)){
                    getActivity().finish();
                }else{
                    text_warn.setVisibility(View.VISIBLE);
                    text_warn.setText("密码错误！！！");
                }
                break;
            default:
                break;
        }
    }
}