package com.wangyuelin.sender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wangyuelin.sender.pages.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginActivity.open(this);
    }
}
