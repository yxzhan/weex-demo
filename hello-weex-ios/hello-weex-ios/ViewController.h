//
//  ViewController.h
//  hello-weex-ios
//
//  Created by Yanxiang  on 2017/5/16.
//  Copyright © 2017年 Yanxiang . All rights reserved.
//

#import <UIKit/UIKit.h>
#import <WeexSDK/WXSDKInstance.h>

@interface ViewController : UIViewController

@property (nonatomic, strong) WXSDKInstance *instance;
@property (nonatomic, strong) UIView *weexView;

@end

