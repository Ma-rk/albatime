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

@interface CalcModel ()

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


- (void)removeUserDefaults {
    NSString *appDomain = [[NSBundle mainBundle] bundleIdentifier];
    [[NSUserDefaults standardUserDefaults] removePersistentDomainForName:appDomain];
}

- (void)removePassword {
    NSError *error;
    if ([SSKeychain deletePasswordForService:SERVICE_TITLE
                                     account:[self.defaults objectForKey:@"email"]
                                       error:&error]) {
        if ([self.delegate respondsToSelector:@selector(removePswdSucceed)]) {
            [self.delegate removePswdSucceed];
        }
    }
    else {
        if ([self.delegate respondsToSelector:@selector(removePswdFailedWithError:)]) {
            [self.delegate removePswdFailedWithError:[NSString stringWithFormat:@"%@", error]];
        }
    }
}

@end
