//
//  AppDelegate.h
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 6. 30..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import <UIKit/UIKit.h>

@class LoginModel;
@class NetworkHandler;

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) LoginModel *loginModel;
@property (strong, nonatomic) NetworkHandler *networkHandler;

@end

