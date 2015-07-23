//
//  NetworkHandler.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 2..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "NetworkHandler.h"
#import "Definitions.h"

@interface NetworkHandler ()

@property (nonatomic, strong) NSURLSession *session;

@end

@implementation NetworkHandler

- (instancetype)init
{
    self = [super init];
    if (self) {
        NSURLSessionConfiguration *sessionConfig = [NSURLSessionConfiguration defaultSessionConfiguration];
        sessionConfig.timeoutIntervalForRequest = 10.0;
        sessionConfig.timeoutIntervalForResource = 30.0;
        self.session = [NSURLSession sessionWithConfiguration:sessionConfig];
    }
    return self;
}

    // login request
- (void)userAuthenticationWithEmail:(NSString *)email andPassword:(NSString *)password {
    
    NSString *urlString = [NSString stringWithFormat:@"%@/user", BASE_URL];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:urlString]];
    [request setHTTPMethod:@"POST"];
    [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"content-type"];
    NSString *postString = [NSString stringWithFormat:@"email=%@&pw=%@", email, password];
    NSData *data = [postString dataUsingEncoding:NSUTF8StringEncoding];
    [request setHTTPBody:data];
    [request setValue:[NSString stringWithFormat:@"%lu", [data length]] forHTTPHeaderField:@"Content-Length"];
    
    [[self.session dataTaskWithRequest:request
                     completionHandler:^(NSData *data, NSURLResponse *response, NSError *error)
      {
          if (error) {
              NSLog(@"Login failed with Error : %@", error);
              if ([self.delegate respondsToSelector:@selector(loginFailedWithError:)]) {
                  [self.delegate loginFailedWithError:[NSString stringWithFormat:@"%@", error]];
              }
          }
          else {
              NSLog(@"SignUp success Response : %@", response);
              NSDictionary *JSON = [[NSDictionary alloc] initWithDictionary:[NSJSONSerialization JSONObjectWithData:data
                                                                                                            options:NSJSONReadingAllowFragments
                                                                                                              error:nil]];
              NSLog(@"Data : %@", JSON);
              BOOL successIndicator = [JSON objectForKey:@"result"];
              if (successIndicator) {
                  if ([self.delegate respondsToSelector:@selector(loginSucceed)]) {
                      [self.delegate loginSucceed];
                  }
                  if ([self.delegate respondsToSelector:@selector(loginSucceedWithEmail:andPassword:)]) {
                      [self.delegate loginSucceedWithEmail:email andPassword:password];
                  }
              }
              else {
                  if ([self.delegate respondsToSelector:@selector(loginFailedWithError:)]) {
                      [self.delegate loginFailedWithError:[JSON objectForKey:@"errorCode"]];
                  }
              }
          }
      }] resume];
}

- (void)signUpWithUserCredential:(NSMutableDictionary *)userCredential {
    
    // set user type info
    NSString *type;
    if ([userCredential[@"identity"] isEqualToString:@"I'm an employee"])
        type = USR_TYP_01;
    else if ([userCredential[@"identity"] isEqualToString:@"I'm an employer"])
        type = USR_TYP_02;
    
    NSString *urlString = [NSString stringWithFormat:@"%@/account", BASE_URL];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:urlString]];
    [request setHTTPMethod:@"POST"];
    [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"content-type"];
    NSString *postString = [NSString stringWithFormat:@"email=%@&pw=%@&nick=%@&type=%@", userCredential[@"email"], userCredential[@"password"], userCredential[@"username"], type];
    NSData *data = [postString dataUsingEncoding:NSUTF8StringEncoding];
    [request setHTTPBody:data];
    [request setValue:[NSString stringWithFormat:@"%lu", [data length]] forHTTPHeaderField:@"Content-Length"];
    
    [[self.session dataTaskWithRequest:request
                     completionHandler:^(NSData *data, NSURLResponse *response, NSError *error)
    {
        if (error) {
            NSLog(@"SignUp failed with Error : %@", error);
            if ([self.delegate respondsToSelector:@selector(signUpFailedWithError:)]) {
                [self.delegate signUpFailedWithError:[NSString stringWithFormat:@"%@", error]];
            }
        }
        else {
            NSLog(@"SignUp success Response : %@", response);
            NSDictionary *JSON = [[NSDictionary alloc] initWithDictionary:[NSJSONSerialization JSONObjectWithData:data
                                                                                                          options:NSJSONReadingAllowFragments
                                                                                                            error:nil]];
            NSLog(@"Data : %@", JSON);
            BOOL successIndicator = [JSON objectForKey:@"result"];
            if (successIndicator) {
                if ([self.delegate respondsToSelector:@selector(signUpSucceed)]) {
                    [self.delegate signUpSucceed];
                }
                if ([self.delegate respondsToSelector:@selector(signUpSucceedWithUserCredential:)]) {
                    [self.delegate signUpSucceedWithUserCredential:userCredential];
                }
            }
            else {
                if ([self.delegate respondsToSelector:@selector(signUpFailedWithError:)]) {
                    [self.delegate signUpFailedWithError:[JSON objectForKey:@"errorCode"]];
                }
            }
        }
        
    }] resume];
}

- (void)sendResetRequest:(NSString *)email {
    [self performSelector:@selector(resetEmailSent) withObject:nil afterDelay:2.0f];
    
    if (YES) {
        if ([self.delegate respondsToSelector:@selector(resetEmailSent)]) {
            [self.delegate resetEmailSent];
        }
    }
    else {
        if ([self.delegate respondsToSelector:@selector(resetEmailNotSent)]) {
            [self.delegate resetEmailNotSent];
        }
    }
}


    // return YES if given email is available, NO if not
- (BOOL)checkEmailAvailability:(NSString *)email {
    
    __block BOOL isEmailAvailable;
    
    NSString *urlString = [NSString stringWithFormat:@"%@/account", BASE_URL];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:urlString]];
    [request setHTTPMethod:@"GET"];
    [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"content-type"];
    NSString *postString = [NSString stringWithFormat:@"email=%@", email];
    NSData *data = [postString dataUsingEncoding:NSUTF8StringEncoding];
    [request setHTTPBody:data];
    [request setValue:[NSString stringWithFormat:@"%lu", [data length]] forHTTPHeaderField:@"Content-Length"];
    
    [[self.session dataTaskWithRequest:request
                     completionHandler:^(NSData *data, NSURLResponse *response, NSError *error)
      {
          if (error) {
              // return YES in case error so that server can have chance to determine its availability
              NSLog(@"Checking email availability failed with Error : %@", error);
              isEmailAvailable = YES;
          }
          else {
              NSLog(@"SignUp success Response : %@", response);
              NSDictionary *JSON = [[NSDictionary alloc] initWithDictionary:[NSJSONSerialization JSONObjectWithData:data
                                                                                                            options:NSJSONReadingAllowFragments
                                                                                                              error:nil]];
              NSLog(@"Data : %@", JSON);
              NSInteger foundEmailNum = [[JSON objectForKey:@"result"] integerValue];
              if (foundEmailNum == 0) {
                  isEmailAvailable = YES;
              }
              else if (foundEmailNum == 1) {
                  isEmailAvailable = NO;
              }
              else {
                  NSLog(@"Checking email availability succeed with unknown error : %@", [JSON objectForKey:@"errorCode"]);
                  isEmailAvailable = YES;
              }
          }
      }] resume];
    
    return isEmailAvailable;
}

@end


