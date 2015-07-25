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

@interface LoginModel () <NetworkHandlerDelegate>

@property (strong, nonatomic) NetworkHandler *networkHandler;
@property (strong, nonatomic) NSUserDefaults *defaults;

@end

@implementation LoginModel

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.networkHandler = [(AppDelegate *)[[UIApplication sharedApplication] delegate] networkHandler];
        self.defaults = [NSUserDefaults standardUserDefaults];
    }
    return self;
}

- (void)loadUserDefaults {
    self.autoLogin = [self.defaults objectForKey:@"autoLogin"];
    self.email = [self.defaults objectForKey:@"email"];
    
    // proceed autoLogin if possible
    if (self.autoLogin && self.email) {
        // 로그아웃 또는 비번 변경하면 키체인에서 비번 지운다
        NSString *password = [SSKeychain passwordForService:SERVICE_TITLE
                                                    account:self.email];
        if (password) {
            // 자동로그인시 서버통신해서 인증 받을거면 1번, 그냥 로그인하려면 2번으로 진행
            [self.networkHandler userAuthenticationWithEmail:self.email andPassword:password]; //1
            //[self.networkHandler.delegate loginSucceed]; //2
        }
        else {
            NSLog(@"Find password from keychain failed");
        }
    }
    
    if ([self.delegate respondsToSelector:@selector(setViewElementsAfterUserDefaultsLoaded)])
        [self.delegate setViewElementsAfterUserDefaultsLoaded];
}

- (BOOL)validateEmail: (NSString *)candidate {
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

#pragma mark - NetworkHandler Delegate Methods

- (void)loginSucceedWithUserCredential:(NSMutableDictionary *)userCredential {
    NSString *email = userCredential[@"email"];
    [self savePassword:userCredential[@"password"]
            forService:SERVICE_TITLE
           withAccount:email];
    [self.defaults setObject:email forKey:@"email"];
    [self.defaults setObject:userCredential[@"token"] forKey:@"token"];
    [self.defaults setObject:userCredential[@"id"] forKey:@"id"];
    [self.defaults setObject:userCredential[@"tokenSeq"] forKey:@"tokenSeq"];

    // save autoLogin state
    if (self.autoLogin)
        [self.defaults setBool:YES forKey:@"autoLogin"];
    else
        [self.defaults setBool:NO forKey:@"autoLogin"];
    
    [self.defaults synchronize];
}

- (void)signUpSucceedWithUserCredential:(NSMutableDictionary *)userCredential {
    NSString *email = userCredential[@"email"];
    [self savePassword:userCredential[@"password"]
            forService:SERVICE_TITLE
           withAccount:email];
    [self.defaults setObject:email forKey:@"email"];
    [self.defaults setObject:userCredential[@"token"] forKey:@"token"];
    [self.defaults setObject:userCredential[@"id"] forKey:@"id"];
    [self.defaults setObject:userCredential[@"tokenSeq"] forKey:@"tokenSeq"];
    [self.defaults setObject:userCredential[@"username"] forKey:@"name"];
    [self.defaults setObject:userCredential[@"identity"] forKey:@"identity"];
    [self.defaults synchronize];
}

- (void)savePassword:(NSString *)password forService:(NSString *)serviceName withAccount:(NSString *)email {
    if ([SSKeychain setPassword:password
                     forService:serviceName
                        account:email]) {
        if ([self.delegate respondsToSelector:@selector(savePswdSucceed)]) {
            [self.delegate savePswdSucceed];
        }
    }
    else {
        if ([self.delegate respondsToSelector:@selector(savePswdFailed)]) {
            [self.delegate savePswdFailed];
        }
    }
}

@end
