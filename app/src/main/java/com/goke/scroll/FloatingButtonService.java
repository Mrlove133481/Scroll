package com.goke.scroll;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
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
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        //layoutParams.x = 300;
        layoutParams.y = 50;
    }

    private static final String TAG = "FloatingButtonService";
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setColor(int blue) {
        scrollTextView.setTextColor(blue);
        windowManager.updateViewLayout(scrollTextView,layoutParams);

    }

    public void setText(String toString) {
        scrollTextView.setText(toString);
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void setTextSize(String textSize){
        scrollTextView.setTextSize(30);
        windowManager.updateViewLayout(scrollTextView,layoutParams);
    }

    public void setSpeed(String speed) {
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
            scrollTextView.setText("测试文字");
            scrollTextView.setSpeed(4);
            scrollTextView.setHorizontal(true);
            scrollTextView.setBackgroundColor(Color.alpha(1));
            windowManager.addView(scrollTextView, layoutParams);
    }

}
