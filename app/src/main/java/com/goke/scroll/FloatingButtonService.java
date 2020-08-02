package com.goke.scroll;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class FloatingButtonService extends Service {
    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private ScrollTextView scrollTextView;
    private final IBinder mBinder = new MyCustomBinder();


    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = 800;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.x = 0;
        layoutParams.y = 50;
    }

    private static final String TAG = "FloatingButtonService";
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setColor(int color) {
        scrollTextView.setTextColor(color);
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void setText(String text) {
        Log.d(TAG, "setText: "+text);
        if(text!=null&&!"".equals(text)){
            scrollTextView.setText(text);
        }else {
            scrollTextView.setText("测试文字 测试文字 测试文字 测试文字 测试文字 测试文字");
        }
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void setTextSize(String textSize){
        Log.d(TAG, "setTextSize: "+textSize);
        if(textSize!=null&&!"".equals(textSize)){
            int mTextSize = Integer.parseInt(textSize);
            if(mTextSize < 1){
                scrollTextView.setTextSize(1f);
            }else {
                scrollTextView.setTextSize(mTextSize);
            }
        }else {
            scrollTextView.setTextSize(20f);
        }
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void setSpeed(String speed) {
        Log.d(TAG, "setSpeed: "+speed);
        if(speed!=null&&!"".equals(speed)){
            int mSpeed = Integer.parseInt(speed);
            if(mSpeed > 100|| mSpeed < 1){
                scrollTextView.setSpeed(1);
            }else {
                scrollTextView.setSpeed(mSpeed);
            }
        }else {
            scrollTextView.setSpeed(1);
        }
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void setIntervals(String time){
        Log.d(TAG, "setIntervals: "+time);
        if(time!=null&&!"".equals(time)){
            int mTime = Integer.parseInt(time);
            if(mTime < 0){
                scrollTextView.setIntervals(0);
            }else {
                scrollTextView.setIntervals(mTime*1000);
            }
        }else {
            scrollTextView.setIntervals(0);
        }
    }

    public void setUp(){
        Log.d(TAG, "setUp: "+layoutParams.y);
        layoutParams.y =  layoutParams.y + 10;
        if(layoutParams.y>getHeightPixels()){
            layoutParams.y = getHeightPixels();
        }
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void setDown(){
        Log.d(TAG, "setDown: "+layoutParams.y);
        layoutParams.y =  layoutParams.y - 10;
        if(layoutParams.y<0){
            layoutParams.y = 0;
        }
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void setLeft(){
        Log.d(TAG, "setLeft: "+layoutParams.x);
        layoutParams.x =  layoutParams.x - 10;
        if(layoutParams.x<-(getWidthPixels()-layoutParams.width)/2){
            layoutParams.x = -(getWidthPixels()-layoutParams.width)/2;
        }
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void setRight(){
        Log.d(TAG, "setRight: "+layoutParams.x);
        layoutParams.x =  layoutParams.x + 10;
        if(layoutParams.x>(getWidthPixels()-layoutParams.width)/2){
            layoutParams.x = (getWidthPixels()-layoutParams.width)/2;
        }
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    private int getHeightPixels(){
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    private int getWidthPixels(){
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        Log.d(TAG, "getWidthPixels: "+outMetrics.widthPixels);
        return outMetrics.widthPixels;
    }

    public void resetSetting(){
        Log.d(TAG, "resetSetting: 执行了");
        scrollTextView.setText("测试文字 测试文字 测试文字 测试文字 测试文字 测试文字");
        scrollTextView.setSpeed(4);
        scrollTextView.setIntervals(0);
        scrollTextView.setTextSize(20f);
        scrollTextView.setTextColor(Color.BLACK);
        layoutParams.width=800;
        layoutParams.x = 0;
        layoutParams.y = 50;
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void resetLocation() {
        Log.d(TAG, "resetLocation: 执行了");
        layoutParams.x = 0;
        layoutParams.y = 50;
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void setWidth(String width) {
        Log.d(TAG, "setWidth: "+width);
        if(width!=null&&!"".equals(width)){
            int mWidth = Integer.parseInt(width);
            if(mWidth < 1){
               layoutParams.width=1;
            }else if(mWidth>getWidthPixels()){
              layoutParams.width= getWidthPixels();
            }else {
                layoutParams.width= mWidth;
            }
        }else {
            layoutParams.width= 800;
        }
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public class MyCustomBinder extends Binder {
        FloatingButtonService getService() {
            return FloatingButtonService.this;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatingWindow() {
            View view = View.inflate(getApplicationContext(),R.layout.scrolltextview,null);
            scrollTextView = view.findViewById(R.id.scrollText);
            scrollTextView.setText("测试文字 测试文字 测试文字 测试文字 测试文字 测试文字");
            scrollTextView.setSpeed(4);
            scrollTextView.setHorizontal(true);
            scrollTextView.setBackgroundColor(Color.alpha(1));
            windowManager.addView(scrollTextView, layoutParams);


    }

}
