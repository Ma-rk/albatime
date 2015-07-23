//
//  LoginModel.h
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 3..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol LoginModelDelegate <NSObject>
@optional

- (void)savePswdSucceed;
- (void)savePswdFailed;

@end

@interface LoginModel : NSObject

@property (weak, nonatomic) id<LoginModelDelegate> delegate;
@property (strong, nonatomic) NSString *email;
@property BOOL autoLogin;

- (void)loadUserDefaults;
- (BOOL)validateEmail:(NSString *)candidate;
- (void)tryAutoLogin;
- (void)turnOnAutoLogin;
- (void)turnOffAutoLogin;

@end
