//
//  NetworkHandler.h
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 2..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol NetworkHandlerDelegate <NSObject>
@optional

- (void)loginSucceed;
- (void)loginSucceedWithEmail:(NSString *)email andPassword:(NSString *)password;
- (void)loginFailedWithError:(NSString *)error;
- (void)signUpSucceed;
- (void)signUpSucceedWithUserCredential:(NSMutableDictionary *)userCredential;
- (void)signUpFailedWithError:(NSString *)error;
- (void)resetEmailSent;
- (void)resetEmailNotSent;

@end

@interface NetworkHandler : NSObject

@property (weak, nonatomic) id<NetworkHandlerDelegate> delegate;

- (void)userAuthenticationWithEmail:(NSString *)email andPassword:(NSString *)password;
- (BOOL)checkEmailAvailability:(NSString *)email;
- (void)sendResetRequest:(NSString *)email;
- (void)signUpWithUserCredential:(NSMutableDictionary *)userCredential;

@end
