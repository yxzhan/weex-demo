---
title: weex android集成的官方文档细节补充
type: guide
has_chapter_content: false
chapter_title: Intro
version: 1.0
---
# weex android集成的官方文档细节补充

## 说明
[官方的集成文档](https://weex.apache.org/cn/guide/integrate-to-your-app.html)针对的读者是有基本android开发经验的读者，本人正好不在这个范围内，所以配置的过程是一脚一个坑。并且文档的滞后性也导致配置过程出现很多错误，截至写本文的时间，官方文档引入的是0.5.1的weex_sdk, 而我现在引入的是0.11.0的版本。如果直接引入源码里面的sdk的话，build sdk需要额外引入一些其他gradle的插件，所以直接从jcenter引入weex_sdk依赖更简单.

提示：这里对官方的文档中缺少的一些细节做个补充，大体上的过程和示例代码都按照官方文档的来，但是为了便于理解整个引入的过程，只是对最基础的weex_sdk进行引入，对于图片下载的实现类也省略了。另外官方的例子jsbundle是加载app本地资源，这里是直接load本地服务器上的jsbundle。

### 前期准备
* 安装android studio

### 源码集成配置步骤
1. 用android studio创建空android项目，最低版本选PI 19, activity选择Empty Activity
2. 修改build.gradle（这里指的是app module下面的build.gradle）加入如下基础依赖：
```java
compile 'com.android.support:recyclerview-v7:25.3.1'
compile 'com.android.support:appcompat-v7:25.3.1'
compile 'com.taobao.android:weex_sdk:0.11.0'
```
说明：recyclerview和appcompat的版本要根据自己安装的android SDK版本作修改

3. 在MainActivity类所在的package下新建一个WXApplication类继承Application类

```java
// File: WXApplication.java
package com.example.xxx;
import android.app.Application;
import com.taobao.weex.WXSDKEngine;

// 在onCreate handler初始化weex engine
public class WXApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    // 第二个参数是配置，官方文档中有图片下载的拓展，这里没有，所以传null
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

5. 最关键的部分，在MainActivity里面实例化一个weex instance，并渲染页面
```java


```


Tips：
android的gradle貌似类似于前端的npm
要注意修改AndroidMainifest.xml这个描述文件