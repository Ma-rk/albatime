//
//  CalcModel.h
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 26..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import <Foundation/Foundation.h>

@class NetworkHandler;

@protocol CalcModelDelegate <NSObject>
@optional

- (void)removePswdSucceed;
- (void)removePswdFailedWithError:(NSString *)error;
- (void)showAlertViewTitle:(NSString *)title message:(NSString *)message;

@end

@interface CalcModel : NSObject

@property (weak, nonatomic) id<CalcModelDelegate> delegate;
@property (strong, nonatomic) NetworkHandler *networkHandler;
@property (strong, nonatomic) NSUserDefaults *defaults;
@property BOOL hasNetworkConnection;

- (void)removePassword;
- (void)removeUserDefaults;

@end
