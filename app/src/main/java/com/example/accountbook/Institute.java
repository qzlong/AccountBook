package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class Institute extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute);
        SharedPreferences.Editor editor = getSharedPreferences("Date",MODE_PRIVATE).edit();
        //editor.
    }

}