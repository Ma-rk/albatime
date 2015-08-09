//
//  LoginModel.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 3..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "LoginModel.h"
#import "SSKeyChain.h"
#import "Definitions.h"
#import "NetworkHandler.h"
#import "AppDelegate.h"

@interface LoginModel ()

@property (strong, nonatomic) NSUserDefaults *defaults;

/*
 이미 회원가입한 사람 로그인하면 이메일에 딸린 ID값을 받아와서 덮어쓰는 과정이 필요함 / 그래야 다른 기기에서 로그인시 그 기기의 ID값-이메일 간 consistency 보장
 */

@end

@implementation LoginModel

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.networkHandler = [(AppDelegate *)[[UIApplication sharedApplication] delegate] networkHandler];
        self.defaults = [NSUserDefaults standardUserDefaults];
        [self observeDisconnectedNotification];
        self.hasNetworkConnection = YES;
    }
    return self;
}

- (void)loadUserDefaults {
    self.autoLogin = [self.defaults boolForKey:@"autoLogin"];
    self.email = [self.defaults objectForKey:@"email"];
    
    // proceed autoLogin if possible
    if (!self.hasNetworkConnection) {
        NSString *title = @"No network connection!";
        NSString *message = @"Please connect internet";
        [self.delegate showAlertViewTitle:title
                                  message:message];
    }
    else if (self.autoLogin && self.email) {
        NSString *password = [self retrievePasswordForEmail:self.email];
        if (password) {
            [self.networkHandler userAuthenticationWithEmail:self.email
                                                    password:password];
        }
    }
    else {
        if ([self.delegate respondsToSelector:@selector(setViewElementsAfterUserDefaultsLoaded)])
            [self.delegate setViewElementsAfterUserDefaultsLoaded];
    }
}

- (BOOL)validateEmail:(NSString *)candidate {
    NSString *emailRegex =
    @"(?:[a-z0-9!#$%\\&'*+/=?\\^_`{|}~-]+(?:\\.[a-z0-9!#$%\\&'*+/=?\\^_`{|}"
    @"~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\"
    @"x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-"
    @"z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5"
    @"]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-"
    @"9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21"
    @"-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES[c] %@", emailRegex];
    
    return [emailTest evaluateWithObject:candidate];
}

- (void)saveLoginInfoWithEmail:(NSString *)email password:(NSString *)password {
    [self.defaults setObject:email
                      forKey:@"email"];
    [self savePassword:password
            forService:SERVICE_TITLE
           withAccount:email];
    
    // save autoLogin state
    if (self.autoLogin)
        [self.defaults setBool:YES
                        forKey:@"autoLogin"];
    else
        [self.defaults setBool:NO
                        forKey:@"autoLogin"];
    
    [self.defaults synchronize];
}

- (void)saveSignUpInfoWithUserCredential:(NSMutableDictionary *)userCredential {
    NSString *email = userCredential[@"email"];
    [self savePassword:userCredential[@"password"]
            forService:SERVICE_TITLE
           withAccount:email];
    [self.defaults setObject:email
                      forKey:@"email"];
    [self.defaults setObject:userCredential[@"identity"]
                      forKey:@"identity"];
    [self.defaults setObject:userCredential[@"username"]
                      forKey:@"username"];
    [self.defaults synchronize];
}

- (void)savePassword:(NSString *)password forService:(NSString *)serviceName withAccount:(NSString *)email {
    NSError *error;
    if ([SSKeychain setPassword:password
                     forService:SERVICE_TITLE
                        account:email
                          error:&error]) {
        if ([self.delegate respondsToSelector:@selector(savePasswordSucceed)]) {
            [self.delegate savePasswordSucceed];
        }
    }
    else {
        if ([self.delegate respondsToSelector:@selector(savePasswordFailedWithError:)]) {
            [self.delegate savePasswordFailedWithError:[NSString stringWithFormat:@"%@", error]];
        }
    }
}

- (NSString *)retrievePasswordForEmail:(NSString *)email {
    return [SSKeychain passwordForService:SERVICE_TITLE
                                  account:email];
}

#pragma mark - Set internet disconnection notification

- (void)observeDisconnectedNotification {
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(disconnectionAlert:)
                                                 name:@"networkDisconnected"
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(networkConnected:)
                                                 name:@"networkConnected"
                                               object:nil];
}

- (void)disconnectionAlert:(NSNotification *)notification {
    self.hasNetworkConnection = NO;
    NSString *title = @"WARNING!\nYou have no network connection!";
    NSString *message = @"Connect internet before further modification, otherwise you may lose your recent changes";
    [self.delegate showAlertViewTitle:title
                              message:message];
}

- (void)networkConnected:(NSNotification *)notification {
    self.hasNetworkConnection = YES;
}

@end
