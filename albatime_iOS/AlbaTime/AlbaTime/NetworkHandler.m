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
        
    }
    return self;
}

- (void)sendResetRequest:(NSString *)email {
    // 이메일 발송요청 보내고 성공하면 success 에 0 / 실패하면 1 저장
    NSNumber *success = @0;

    NSDictionary *userInfo = @{@"data" : success};
    [self performSelector:@selector(postSendResultNoti:) withObject:userInfo afterDelay:2.0f];
}

- (void)postSendResultNoti:(NSDictionary *)userinfo {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"resetRequestSent"
                                                        object:nil
                                                      userInfo:userinfo];
}

- (BOOL)checkEmailAvailability:(NSString *)email {
    return NO;
}

- (void)uploadUserCredential:(NSMutableDictionary *)userCredential {
    NSLog(@"Upload user credentials");
}


@end


