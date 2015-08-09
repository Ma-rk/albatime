//
//  AppDelegate.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 6. 30..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "AppDelegate.h"
#import "LoginModel.h"
#import "NetworkHandler.h"
#import "AFNetworking.h"

@interface AppDelegate ()

@end

@implementation AppDelegate

- (LoginModel *)loginModel {
    if (!_loginModel) {
        _loginModel = [LoginModel new];
    }
    return _loginModel;
}

- (NetworkHandler *)networkHandler {
    if (!_networkHandler) {
        _networkHandler = [NetworkHandler new];
    }
    return _networkHandler;
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    
    // check if the user has a signUp record
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    
    // Conditionally start at different places depends on signUp record
    // Segue 실행 방법 두 가지 다 동작하는데 어떤게 맞는지 모르겠다.
    if ([defaults boolForKey:@"hasSignUpRecord"]) {
        UIViewController *initViewController = [storyboard instantiateViewControllerWithIdentifier:@"LoginViewController"];
        //[self.window setRootViewController:initViewController];
        [(UINavigationController *)self.window.rootViewController pushViewController:initViewController animated:NO];
    } else {
        UIViewController *initViewController = [storyboard instantiateViewControllerWithIdentifier:@"WageViewController"];
        //[self.window setRootViewController:initViewController];
        [(UINavigationController *)self.window.rootViewController pushViewController:initViewController animated:NO];
    }
    
    // Start monitoring the internet connection
    [[AFNetworkReachabilityManager sharedManager] startMonitoring];
    [[AFNetworkReachabilityManager sharedManager] setReachabilityStatusChangeBlock:^(AFNetworkReachabilityStatus status)
    {
        if (status <= 0) {
            [[NSNotificationCenter defaultCenter] postNotificationName:@"networkDisconnected"
                                                                object:nil
                                                              userInfo:nil];
            NSLog(@"Reachability status: %@", AFStringFromNetworkReachabilityStatus(status));
        }
        NSLog(@"Reachability status: %@", AFStringFromNetworkReachabilityStatus(status));
        if (status > 0) {
            [[NSNotificationCenter defaultCenter] postNotificationName:@"networkConnected"
                                                                object:nil
                                                              userInfo:nil];
            NSLog(@"Reachability status: %@", AFStringFromNetworkReachabilityStatus(status));
        }
    }];
    
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
