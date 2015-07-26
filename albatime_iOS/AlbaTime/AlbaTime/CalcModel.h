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

- (void)removeTokenSucceed;
- (void)removeTokenFailedWithError:(NSString *)error;

@end

@interface CalcModel : NSObject

@property (weak, nonatomic) id<CalcModelDelegate> delegate;
@property (strong, nonatomic) NetworkHandler *networkHandler;
@property (strong, nonatomic) NSUserDefaults *defaults;

- (NSMutableDictionary *)getToken;
- (void)creatNewJobWithJobInfo:(NSMutableDictionary *)jobInfo;
- (void)removeAccessToken;
- (void)removeUserDefaults;

@end
