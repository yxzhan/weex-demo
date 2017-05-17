# hello-weex

> hello-weex

A setting up demo of weex in ios platform.

## Brief setup procedure
* Install `weex-toolkit` and `weex-devtool` npm package globally
* Use `weex init` command to generate an demo project
* Create an empty ios project in xcode
* Install sdk with cocoapods (or import from source code)
* Setup SDK weex environment
* Run webpack build process, run a server to serve the js bundle, run a debug server, build and run ios simulator
* Boot a ios simulator in shell `xcrun instruments -w "iPhone 5s"`
## getting start

```bash
npm install
```

## file structure

* `src/*`: all source code
* `hello-weex-ios/*`: ios intergate example
* `app.js`: entrance of the Weex page
* `build/*`: some build scripts
* `dist/*`: where places generated code
* `assets/*`: some assets for Web preview
* `index.html`: a page with Web preview and qrcode of Weex js bundle
* `weex.html`: Web render
* `.babelrc`: babel config (preset-2015 by default)
* `.eslintrc`: eslint config (standard by default)

## npm scripts

```bash
# build both two js bundles for Weex and Web
npm run build

# build the two js bundles and watch file changes
npm run dev

# start a Web server at 8080 port
npm run serve

# start weex-devtool for debugging with native
npm run debug
```

## Integrate ios && Devtool

### Podfile
CocoaPods dependencies
```
source 'git@github.com:CocoaPods/Specs.git' 
target 'hello-weex-ios' do
    platform :ios, '7.0' 
    pod 'WeexSDK', '~> 0.11.0'
    pod 'WXDevtool', '~> 0.9.5'
end
```

```bash
pod install
```
> Note: After install dependencies you need to open the project with the newly generated workspace file (**.xcworkspace).

### weex-ios-sdk
```
// File: AppDelegate.m
#import <WeexSDK/WeexSDK.h>

// Add to method "didFinishLaunchingWithOptions"
//init sdk enviroment
[WXSDKEngine initSDKEnviroment];
//set the log level
[WXLog setLogLevel: WXLogLevelAll];
```

```
// File: ViewController.h
#import <WeexSDK/WXSDKInstance.h>

// Delclare properites
@property (nonatomic, strong) WXSDKInstance *instance;
@property (nonatomic, strong) UIView *weexView;
```

```
- (void)viewDidLoad {
    .......
    [self render];
    .......
}

// Instanciate the weex instance
- (void)render {
    // Destroy the current weex instance
    [_instance destroyInstance];
    
    // The url of the JS bundle
    NSURL *url = [NSURL URLWithString: @"http://100.84.234.5:8080/dist/app.weex.js"];
    CGFloat width = self.view.frame.size.width;

    // initiate weex instance
    _instance = [[WXSDKInstance alloc] init];
    _instance.viewController = self;
    _instance.frame = CGRectMake(0, 0, width, CGRectGetHeight(self.view.frame));
    __weak typeof(self) weakSelf = self;
    _instance.onCreate = ^(UIView *view) {
        weakSelf.weexView = view;
        [weakSelf.view addSubview:weakSelf.weexView];
    };
    
    
    NSString *randomURL = [NSString stringWithFormat:@"%@%@random=%d",url.absoluteString,url.query?@"&":@"?",arc4random()];
    // call the renderWith URL Method
    [_instance renderWithURL:[NSURL URLWithString:randomURL] options:@{@"bundleUrl":url.absoluteString} data:nil];
}

- (void)dealloc {
    [_instance destroyInstance];
    ....
}
```

### devtool
```object-c
// File: ViewController.m
// import Library
#import <TBWXDevtool/WXDevtool.h>


- (void)viewDidLoad {
    ......
    // Launch the Devtool
    [WXDevTool launchDevToolDebugWithUrl:@"ws://100.84.234.5:8088/debugProxy/native"];

    // Add an event listener to refresh the weex view
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(notificationRefreshInstance:) name:@"RefreshInstance" object:nil];
    ......
}

// Add a refresh Event handler method to ViewController
- (void)notificationRefreshInstance:(NSNotification *)notification {
    // Refresh function,
    [self render];
}

// remove Event Oberver when dealloc
- (void)dealloc {
    ....
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}
```

Watch files change
```
weex debug ./dist/app.weex.js
```

### Implement WXImgLoaderProtocol

* Import a cocoapods dependencies called `SDWebImage` (version 3.7.x)
* Create an weex handler (WXImgLoaderDefaultImpl) which is an object-c interface to implement `WXImgLoaderProtocol`
* Register the image handler after init weex sdk environment

## notes
Native bundle has not implement the image component, so `<image>` label doesn't work. 
You can config more babel, ESLint and PostCSS plugins in `webpack.config.js`.


## Todos
* Build native code and Run simulator with command line tool
* Integrate weex in a swift project
* Try android platform also
* Web platform live reload