//
//  LoginModel.h
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 3..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import <Foundation/Foundation.h>

@class NetworkHandler;

@protocol LoginModelDelegate <NSObject>
@optional

- (void)savePasswordSucceed;
- (void)savePasswordFailedWithError:(NSString *)error;
- (void)setViewElementsAfterUserDefaultsLoaded;
- (void)showAlertViewTitle:(NSString *)title message:(NSString *)message;

@end

@interface LoginModel : NSObject

@property (weak, nonatomic) id<LoginModelDelegate> delegate;
@property (strong, nonatomic) NSString *email;
@property (strong, nonatomic) NetworkHandler *networkHandler;
@property BOOL autoLogin;
@property BOOL hasNetworkConnection;

- (void)saveLoginInfoWithEmail:(NSString *)email password:(NSString *)password;
- (void)saveSignUpInfoWithUserCredential:(NSMutableDictionary *)userCredential;
- (void)loadUserDefaults;
- (BOOL)validateEmail:(NSString *)candidate;
- (BOOL)hasSignUpRecord;

@end
