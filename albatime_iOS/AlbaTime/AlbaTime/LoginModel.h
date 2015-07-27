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

- (void)savePswdSucceed;
- (void)savePswdFailedWithError:(NSString *)error;
- (void)setViewElementsAfterUserDefaultsLoaded;

@end

@interface LoginModel : NSObject

@property (weak, nonatomic) id<LoginModelDelegate> delegate;
@property (strong, nonatomic) NSString *email;
@property (strong, nonatomic) NetworkHandler *networkHandler;
@property BOOL autoLogin;

- (void)saveLoginInfoWithEmail:(NSString *)email andPswd:(NSString *)password;
- (void)saveSignUpInfoWithUserCredential:(NSMutableDictionary *)userCredential;
- (void)loadUserDefaults;
- (BOOL)validateEmail:(NSString *)candidate;

@end
