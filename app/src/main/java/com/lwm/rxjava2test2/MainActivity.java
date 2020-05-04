package com.lwm.rxjava2test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonbtn1 = (Button) findViewById(R.id.btn_btn1);
        Button buttonbtn2 = (Button) findViewById(R.id.btn_btn2);
        Button buttonbtn3 = (Button) findViewById(R.id.btn_btn3);
        Button buttonbtn4 = (Button) findViewById(R.id.btn_btn4);
        buttonbtn1.setOnClickListener(this);
        buttonbtn2.setOnClickListener(this);
        buttonbtn3.setOnClickListener(this);
        buttonbtn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_btn1:
                startActivity(MainActivity.this,Main1Activity.class);
                break;
            case R.id.btn_btn2:
                startActivity(MainActivity.this,Main2Activity.class);
                break;
            case R.id.btn_btn3:
                startActivity(MainActivity.this,Main3Activity.class);
                break;
            case R.id.btn_btn4:
                startActivity(MainActivity.this,Main4Activity.class);
                break;
        }
    }

    public void startActivity(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
    }
}
