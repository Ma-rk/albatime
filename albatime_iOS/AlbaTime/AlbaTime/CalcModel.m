//
//  CalcModel.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 26..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "CalcModel.h"
#import "SSKeyChain.h"
#import "AppDelegate.h"
#import "NetworkHandler.h"
#import "Definitions.h"

@interface CalcModel () <NetworkHandlerDelegate>

@property (strong, nonatomic) NetworkHandler *networkHandler;

@end

@implementation CalcModel

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.networkHandler = [(AppDelegate *)[[UIApplication sharedApplication] delegate] networkHandler];
        self.defaults = [NSUserDefaults standardUserDefaults];
    }
    return self;
}

- (void)creatNewJobWithJobInfo:(NSMutableDictionary *)jobInfo {
    NSMutableDictionary *tokenDic = [[NSMutableDictionary alloc] initWithDictionary:[self getToken]];
    [jobInfo setObject:tokenDic[@"token"] forKey:@"token"];
    [jobInfo setObject:tokenDic[@"id"] forKey:@"id"];
    [jobInfo setObject:tokenDic[@"tokenSeq"] forKey:@"tokenSeq"];
    
    [self.networkHandler uplaodNewJobInfo:jobInfo];
}

- (NSMutableDictionary *)getToken {
    NSMutableDictionary *tokenDic = [NSMutableDictionary new];
    
    NSString *token = [SSKeychain passwordForService:SERVICE_TITLE
                                             account:[self.defaults objectForKey:@"email"]];
    [tokenDic setObject:token forKey:@"token"];
    [tokenDic setObject:[self.defaults objectForKey:@"id"] forKey:@"id"];
    [tokenDic setObject:[self.defaults objectForKey:@"tokenSeq"] forKey:@"tokenSeq"];
    
    return tokenDic;
}

- (void)removeUserDefaults {
    NSString *appDomain = [[NSBundle mainBundle] bundleIdentifier];
    [[NSUserDefaults standardUserDefaults] removePersistentDomainForName:appDomain];
}

- (void)removeAccessToken {
    NSError *error;
    if ([SSKeychain deletePasswordForService:SERVICE_TITLE
                                     account:[self.defaults objectForKey:@"email"]
                                       error:&error]) {
        if ([self.delegate respondsToSelector:@selector(removeTokenSucceed)]) {
            [self.delegate removeTokenSucceed];
        }
    }
    else {
        if ([self.delegate respondsToSelector:@selector(removeTokenFailedWithError:)]) {
            [self.delegate removeTokenFailedWithError:[NSString stringWithFormat:@"%@", error]];
        }
    }
}

#pragma mark - NetworkHandler Delegate Methods

- (void)newJobCreatedWithJobInfo:(NSDictionary *)jobInfo {
    // DB에 잡 정보 저장
}

@end
