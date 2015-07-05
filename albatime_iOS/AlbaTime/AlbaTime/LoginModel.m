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

@end

@implementation LoginModel

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.networkHandler = [(AppDelegate *)[[UIApplication sharedApplication] delegate] networkHandler];
    }
    return self;
}

- (void)loadUserDefaults {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    self.autoLogin = [defaults objectForKey:@"autoLogin"];
    self.email = [defaults objectForKey:@"email"];
}

- (void)saveUserDefaultsWithEmail:(NSString *)email {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setBool:self.autoLogin forKey:@"autoLogin"];
    [defaults setObject:self.email forKey:@"email"];
    [defaults synchronize];
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
    NSString *password = [SSKeychain passwordForService:SERVICE_TITLE
                                                account:self.email];
    if (password) {
        if ([self.delegate respondsToSelector:@selector(findPswdSucceed)]) {
            [self.delegate findPswdSucceed];
            [self userAuthenticationWithEmail:self.email andPassword:password];
        }
    }
    else {
        if ([self.delegate respondsToSelector:@selector(findPswdFailed)]) {
            [self.delegate findPswdFailed];
        }
    }
}

- (void)userAuthenticationWithEmail:(NSString *)email andPassword:(NSString *)password {
    [self.networkHandler userAuthenticationWithEmail:self.email andPassword:password];
}

- (void)savePassword:(NSString *)password forService:(NSString *)serviceName withAccount:(NSString *)account {
    if ([SSKeychain setPassword:password
                     forService:serviceName
                        account:account]) {
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

- (BOOL)checkEmailAvailability:(NSString *)candidate {
    if ([self.networkHandler checkEmailAvailability:candidate])
        return YES;
    else
        return NO;
}

- (void)signUpWithUserCredential:(NSMutableDictionary *)userCredential {
    [self.networkHandler signUpWithUserCredential:userCredential];
}

- (void)sendResetRequest:(NSString *)email {
    [self.networkHandler sendResetRequest:email];
}

#pragma mark - NetworkHandler Delegate Methods

- (void)loginSucceedWithEmail:(NSString *)email andPassword:(NSString *)password {
    [self saveUserDefaultsWithEmail:email];
    [self savePassword:password forService:SERVICE_TITLE withAccount:email];
}

- (void)signUpSucceedWithEmail:(NSString *)email {
    [self saveUserDefaultsWithEmail:email];
}

@end
