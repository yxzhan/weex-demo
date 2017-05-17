//
//  ViewController.m
//  hello-weex-ios
//
//  Created by Yanxiang  on 2017/5/16.
//  Copyright © 2017年 Yanxiang . All rights reserved.
//

#import "ViewController.h"
#import <TBWXDevtool/WXDevtool.h>

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    // Launch the Devtool
    [WXDevTool launchDevToolDebugWithUrl:@"ws://100.84.234.5:8088/debugProxy/native"];
    
    // Add an event listener to refresh the weex view
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(refreshInstance:) name:@"RefreshInstance" object:nil];

    [self render];
}


- (void)refreshInstance:(NSNotification *)notification {
    // Refresh function
    [self render];
}

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

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {
    [_instance destroyInstance];
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}


@end
