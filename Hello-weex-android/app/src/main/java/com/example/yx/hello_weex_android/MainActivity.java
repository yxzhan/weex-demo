package com.example.yx.hello_weex_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXViewUtils;

public class MainActivity extends AppCompatActivity implements IWXRenderListener {

    private static String BUNDLEURL = "http://192.168.0.103:8080/dist/app.weex.js";
    WXSDKInstance mWXSDKInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init weex instance
        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);

        renderWeexPage();
    }
    protected void renderWeexPage() {
        mWXSDKInstance.renderByUrl(
                "hello-weex",
                BUNDLEURL,
                null,
                null,
                WXViewUtils.getScreenWidth(this),
                WXViewUtils.getScreenHeight(this),
                WXRenderStrategy.APPEND_ASYNC
        );
    }
    /**
     * Implement IWXRenderListener interface
     */
    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        setContentView(view);
    }
    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
    }
    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
    }
    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        Log.e("weex error", msg);
    }

    /**
     * Native activity life circle events handlers
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityResume();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityPause();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityStop();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityDestroy();
        }
    }
}
