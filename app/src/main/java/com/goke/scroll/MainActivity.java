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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingButtonService floatingButtonService;
    Button textButton,speedButton,blackButton,whiteButton,intervalsButton;
    EditText editText,speedText,intervalsText;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(MainActivity.this, FloatingButtonService.class));
        Intent intent = new Intent(this, FloatingButtonService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        textButton = findViewById(R.id.textButton);
        speedButton = findViewById(R.id.speedButton);
        blackButton = findViewById(R.id.blackButton);
        whiteButton = findViewById(R.id.whiteButton);
        intervalsButton = findViewById(R.id.intervalsButton);
        editText = findViewById(R.id.editText);
        speedText = findViewById(R.id.speedText);
        intervalsText = findViewById(R.id.intervalsText);

        textButton.setOnClickListener(this);
        speedButton.setOnClickListener(this);
        blackButton.setOnClickListener(this);
        whiteButton.setOnClickListener(this);
        intervalsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textButton:
                floatingButtonService.setText(editText.getText().toString());
                break;
            case R.id.intervalsButton:
                floatingButtonService.setIntervals(intervalsText.getText().toString());
                break;
            case R.id.speedButton:
                floatingButtonService.setSpeed(speedText.getText().toString());
                floatingButtonService.setTextSize("");
                break;
            case R.id.blackButton:
                floatingButtonService.setColor(Color.BLACK);
                break;
            case R.id.whiteButton:
                floatingButtonService.setColor(Color.WHITE);
                break;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           FloatingButtonService.MyCustomBinder binder = (FloatingButtonService.MyCustomBinder)service;
           floatingButtonService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
                       return;
        }
    };
}
