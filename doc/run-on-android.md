
# 对weex android集成的官方文档细节补充

## 说明
[官方的集成文档](https://weex.apache.org/cn/guide/integrate-to-your-app.html)针对的读者是有基本android开发经验的读者，作为一个正儿八经的前端，我正好不是目标读者，所以配置的过程是一脚一个坑。并且文档的滞后性也导致配置过程出现一些问题，截至写本文的时间，官方文档引入的是0.5.1的weex_sdk, 而我现在引入的是0.11.0的版本。如果直接引入源码里面的sdk的话，build sdk需要额外引入一些其他gradle的插件，所以直接从jcenter引入weex_sdk依赖更简单.

这里对官方的文档中缺少的一些细节做个补充，大体上的过程和示例代码都按照官方文档的来，但是我把图片下载接口的实现部分去掉了，官方文档里面其实也没有实现，只是带了一句信息量很大的 `//实现你自己的图片下载，否则图片无法显示。`，至于为什么要自己实现，官方给出的解释：**Weex 所有暴露给 JS 的内置 module 或 component API 都是安全和可控的， 它们不会去访问系统的私有 API ，也不会去做任何 runtime 上的 hack 更不会去改变应用原有的功能定位。Weex SDK 只提供渲染，而不是其他的能力，如果你需要 像网络，图片，URL跳转这些特性，需要自己动手实现他们。**

### 前期准备
* 安装android studio

### 集成集成weex核心模块
1. 用android studio创建空android项目，最低版本选API 19, activity选择Empty Activity
2. 修改build.gradle（这里指的是app module下面的build.gradle）加入如下基础依赖：
```java
compile 'com.android.support:recyclerview-v7:25.3.1'
compile 'com.android.support:appcompat-v7:25.3.1'
compile 'com.taobao.android:weex_sdk:0.11.0'
compile 'com.alibaba:fastjson:1.1.45'
```
说明：recyclerview和appcompat的版本要根据自己安装的android SDK版本作修改，fastjson依赖也是必须引入，不然用0.11.0版本的weex_sdk在引擎初始化会崩溃。

3. 在MainActivity类所在的package下新建一个WXApplication类继承Application类
```java
// File: WXApplication.java
package com.example.xxx;
import android.app.Application;
import com.taobao.weex.WXSDKEngine;

public class WXApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    // 初始化weex环境，第二个参数是配置，官方文档中有图片下载的拓展，这里没有，所以传null
    WXSDKEngine.initialize(this, null);
  }
}
```
4. 修改AndroidManifest.xml文件
```xml
......
    <uses-permission android:name="android.permission.INTERNET"/>
    <application
        android:name=".WXApplication"
......
```
说明：加网络权限，并且将application指定为刚刚新建的WXApplication类

5. 关键部分，在MainActivity里面实例化一个weex instance，并渲染页面
```java
// File: MainActivity.java
package com.example.xxx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXViewUtils;

public class MainActivity extends AppCompatActivity implements IWXRenderListener {

    private static String BUNDLEURL = "http://100.84.234.5:8080/dist/app.weex.js";
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
                WXRenderStrategy.APPEND_ASYNC
        );
    }
    /**
     * Weex实例的事件回调
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
     * 原生activity的生命周期事件回调
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
```
说明：MainActivity类实现了IWXRenderListener，在onViewCreated回调里面将weex页面加载到前台，这里直接load了远程的js文件，而官方的例子是直接load本地assets资源里的js文件。

### 附录：[weex-demo地址](https://github.com/yxzhan/weex-demo)
### 参考链接：
* [集成 Weex 到已有应用](https://weex.apache.org/cn/guide/integrate-to-your-app.html)
* [weex 集成到 Android 开发记录](https://www.atatech.org/articles/65797)
* [阿里Weex框架Android平台初体验](http://www.jianshu.com/p/b6ba1fb55f8c)
* [kingofglory/weex-demo](https://github.com/kingofglory/weex-demo)
