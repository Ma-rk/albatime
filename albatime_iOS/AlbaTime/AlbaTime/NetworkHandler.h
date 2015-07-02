//
//  NetworkHandler.h
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 2..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NetworkHandler : NSObject

- (void)uploadUserCredential:(NSMutableDictionary *)userCredential;
- (BOOL)checkEmailAvailability:(NSString *)email;
- (void)sendResetRequest:(NSString *)email;

@end
