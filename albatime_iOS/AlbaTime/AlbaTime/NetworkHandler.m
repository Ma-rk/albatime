//
//  NetworkHandler.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 2..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "NetworkHandler.h"
#import "AFNetworking.h"

@implementation NetworkHandler

- (instancetype)init
{
    self = [super init];
    if (self) {
        // generate NSURLSession here
    }
    return self;
}

    // login request
- (void)userAuthenticationWithEmail:(NSString *)email andPassword:(NSString *)password {
    
    // send login request
    
    // if succeed
    if (YES) {
        if ([self.delegate respondsToSelector:@selector(loginSucceed)]) {
            [self.delegate loginSucceed];
        }
        if ([self.delegate respondsToSelector:@selector(loginSucceedWithEmail:andPassword:)]) {
            [self.delegate loginSucceedWithEmail:email andPassword:password];
        }
    }
    // else if fail
    else {
        if ([self.delegate respondsToSelector:@selector(loginFailed)]) {
            [self.delegate loginFailed];
        }
    }
}

- (void)signUpWithUserCredential:(NSMutableDictionary *)userCredential {
    
    // send signUp request
    
    if (YES) {
        if ([self.delegate respondsToSelector:@selector(signUpSucceed)]) {
            [self.delegate signUpSucceed];
        }
        if ([self.delegate respondsToSelector:@selector(signUpSucceedWithEmail:)]) {
            [self.delegate signUpSucceedWithEmail:[userCredential objectForKey:@"email"]];
        }
    }
    // else if fail
    else {
        if ([self.delegate respondsToSelector:@selector(signUpFailed)]) {
            [self.delegate signUpFailed];
        }
    }
}

- (void)sendResetRequest:(NSString *)email {
    [self performSelector:@selector(resetEmailSent) withObject:nil afterDelay:2.0f];
    
    /*
    if (YES) {
        if ([self.delegate respondsToSelector:@selector(resetEmailSent)]) {
            [self.delegate resetEmailSent];
        }
    }
    // else if fail
    else {
        if ([self.delegate respondsToSelector:@selector(resetEmailNotSent)]) {
            [self.delegate resetEmailNotSent];
        }
    }
     */
}


- (BOOL)checkEmailAvailability:(NSString *)email {
    return YES;
}

@end


