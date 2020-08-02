package com.goke.scroll;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ScrollService scrollService;
    Button textButton,speedButton,blackButton,whiteButton,intervalsButton,textSizeButton;
    Button upButton,downButton,leftButton,rightButton,resetLocationButton,resetButton,widthButton;
    EditText editText,speedText,intervalsText,textSizeText,widthText;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, ScrollService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        startService(intent);

        findViewById();
        buttonSetOnClickListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textButton:
                scrollService.setText(editText.getText().toString());
                break;
            case R.id.intervalsButton:
                scrollService.setIntervals(intervalsText.getText().toString());
                break;
            case R.id.speedButton:
                scrollService.setSpeed(speedText.getText().toString());
                break;
            case R.id.blackButton:
                scrollService.setColor(Color.BLACK);
                break;
            case R.id.whiteButton:
                scrollService.setColor(Color.WHITE);
                break;
            case R.id.textSizeButton:
                scrollService.setTextSize(textSizeText.getText().toString());
                break;
            case R.id.resetButton:
                scrollService.resetSetting();
                break;
            case R.id.resetLocationButton:
                scrollService.resetLocation();
                break;
            case R.id.upButton:
                scrollService.setUp();
                break;
            case R.id.downButton:
                scrollService.setDown();
                break;
            case R.id.leftButton:
                scrollService.setLeft();
                break;
            case R.id.rightButton:
                scrollService.setRight();
                break;
            case R.id.widthButton:
                scrollService.setWidth(widthText.getText().toString());
                break;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ScrollService.MyCustomBinder binder = (ScrollService.MyCustomBinder)service;
            scrollService = binder.getService();
            scrollService.setTextSize("20");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
                       return;
        }
    };

    private void findViewById(){
        textButton = findViewById(R.id.textButton);
        speedButton = findViewById(R.id.speedButton);
        blackButton = findViewById(R.id.blackButton);
        whiteButton = findViewById(R.id.whiteButton);
        intervalsButton = findViewById(R.id.intervalsButton);
        textSizeButton = findViewById(R.id.textSizeButton);
        upButton = findViewById(R.id.upButton);
        downButton = findViewById(R.id.downButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        resetLocationButton = findViewById(R.id.resetLocationButton);
        resetButton = findViewById(R.id.resetButton);
        widthButton = findViewById(R.id.widthButton);

        editText = findViewById(R.id.editText);
        speedText = findViewById(R.id.speedText);
        intervalsText = findViewById(R.id.intervalsText);
        textSizeText = findViewById(R.id.textSizeText);
        widthText = findViewById(R.id.widthText);
    }

    private void buttonSetOnClickListener(){
        textButton.setOnClickListener(this);
        speedButton.setOnClickListener(this);
        blackButton.setOnClickListener(this);
        whiteButton.setOnClickListener(this);
        intervalsButton.setOnClickListener(this);
        textSizeButton.setOnClickListener(this);
        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        resetLocationButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        widthButton.setOnClickListener(this);
    }

}
