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
              NSLog(@"Login success Response : %@", response);
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

    [self checkEmailAvailability:email];
    
    NSString *urlString = [NSString stringWithFormat:@"%@/reset", BASE_URL];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:urlString]];
    [request setHTTPMethod:@"POST"];
    [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"content-type"];
    NSString *postString = [NSString stringWithFormat:@"email=%@", email];
    NSData *data = [postString dataUsingEncoding:NSUTF8StringEncoding];
    [request setHTTPBody:data];
    [request setValue:[NSString stringWithFormat:@"%lu", [data length]] forHTTPHeaderField:@"Content-Length"];
    
    [[self.session dataTaskWithRequest:request
                     completionHandler:^(NSData *data, NSURLResponse *response, NSError *error)
      {
          if (error) {
              NSLog(@"Password reset request failed with Error : %@", error);
              if ([self.delegate respondsToSelector:@selector(resetEmailNotSent)]) {
                  [self.delegate resetEmailNotSent];
              }
          }
          else {
              NSLog(@"Password reset request success response : %@", response);
              NSDictionary *JSON = [[NSDictionary alloc] initWithDictionary:[NSJSONSerialization JSONObjectWithData:data
                                                                                                            options:NSJSONReadingAllowFragments
                                                                                                              error:nil]];
              NSLog(@"Data : %@", JSON);
              BOOL successIndicator = [JSON objectForKey:@"result"];
              if (successIndicator) {
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
      }] resume];
}

- (void)checkEmailAvailability:(NSString *)email {
    
    NSString *urlString = [NSString stringWithFormat:@"%@/account", BASE_URL];
    urlString = [urlString stringByAppendingString:[NSString stringWithFormat:@"?email=%@", email]];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:urlString]];
    
    [[self.session dataTaskWithRequest:request
                     completionHandler:^(NSData *data, NSURLResponse *response, NSError *error)
      {
          if (error) {
              // return YES in case error so that server can have chance to determine its availability
              NSLog(@"Checking email availability failed with Error : %@", error);
          }
          else {
              NSLog(@"SignUp success Response : %@", response);
              NSDictionary *JSON = [[NSDictionary alloc] initWithDictionary:[NSJSONSerialization JSONObjectWithData:data
                                                                                                            options:NSJSONReadingAllowFragments
                                                                                                              error:nil]];
              NSLog(@"Data : %@", JSON);
              NSInteger foundEmailNum = [[JSON objectForKey:@"data"] integerValue];
              if ([self.delegate respondsToSelector:@selector(emailCheckResult:)]) {
                  [self.delegate emailCheckResult:foundEmailNum];
              }
          }
      }] resume];
}

@end


