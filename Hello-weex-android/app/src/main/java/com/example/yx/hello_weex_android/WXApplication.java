package com.example.yx.hello_weex_android;

import android.app.Application;
import com.taobao.weex.WXSDKEngine;

/**
 * Created by yx on 2017/5/18.
 */


public class WXApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WXSDKEngine.initialize(this, null);
    }
}