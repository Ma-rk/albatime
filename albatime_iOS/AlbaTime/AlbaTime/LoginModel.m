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

- (void)tryAutoLogin {
    
    // 로그아웃 또는 비번 변경하면 키체인에서 비번 지운다
    NSString *password = [SSKeychain passwordForService:SERVICE_TITLE
                                                account:self.email];
    if (password) {
        // Don't authenticate when auto-login
        //[self userAuthenticationWithEmail:self.email andPassword:password];
        [self.networkHandler.delegate loginSucceed];
    }
    else {
        NSLog(@"Find password from keychain failed");
    }
}

- (void)turnOnAutoLogin {
    [self.defaults setBool:YES forKey:@"autoLogin"];
    [self.defaults synchronize];
}

- (void)turnOffAutoLogin {
    [self.defaults setBool:NO forKey:@"autoLogin"];
    [self.defaults synchronize];
}

#pragma mark - NetworkHandler Delegate Methods

- (void)loginSucceedWithEmail:(NSString *)email andPassword:(NSString *)password {
    [self saveUserDefaultsWithEmail:email];
    [self savePassword:password forService:SERVICE_TITLE withAccount:email];
}

- (void)signUpSucceedWithUserCredential:(NSMutableDictionary *)userCredential {
    [self saveUserDefaultsWithEmail:userCredential[@"email"]];
    [self savePassword:userCredential[@"password"] forService:SERVICE_TITLE withAccount:userCredential[@"email"]];
    [self.defaults setObject:userCredential[@"username"] forKey:@"name"];
    [self.defaults setObject:userCredential[@"identity"] forKey:@"identity"];
    [self.defaults synchronize];
}

- (void)saveUserDefaultsWithEmail:(NSString *)email {
    [self.defaults setObject:email forKey:@"email"];
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
