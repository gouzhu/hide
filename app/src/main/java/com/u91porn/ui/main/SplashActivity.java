package com.u91porn.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.u91porn.R;
import com.u91porn.ui.BaseAppCompatActivity;


/**
 * @author flymegoc
 */
public class SplashActivity extends BaseAppCompatActivity {

    //显示广告页面的时间，5 秒
    long showTime = 5;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3616281855076159/5656030177");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
            }
        });

        //延迟5000ms跳转到主页面
        handler.postDelayed(myRunnable, showTime * 1000);
        handler.sendEmptyMessage(111);//給Handler对象发送信息
    }

    //创建Handler对象
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==111){
                showTime--;//时间减一秒
                if (showTime>0){
                    handler.sendEmptyMessageDelayed(111,1000);//一秒后給自己发送一个信息
                }

            }

        }
    };

    //创建Runnable对象
    Runnable myRunnable=new Runnable() {
        @Override
        public void run() {
            jundToMainActivity();
        }
    };

    //跳转到主页的方法，并关闭自身页面
    public void jundToMainActivity(){
        Intent intent = new Intent(SplashActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }

    //关闭页面
    public void closeSplash(View view){
        handler.removeCallbacks(myRunnable);//移出Runnable对象
        jundToMainActivity();

    }

    //回退键的监听方法，
    // 这里如果直接关闭页面，线程没有关闭的话，5秒后还是会启动主页面，除非移出线程对象
    @Override
    public void onBackPressed() {
        // super.onBackPressed();不让它关闭
        Toast.makeText(this,"广告之后更精彩！",Toast.LENGTH_SHORT).show();

        //如果按回退键，关闭程序，代码设计
        // finish();//关闭页面
        // handler.removeCallbacks(myRunnable);//取消runnable对象

    }
}
