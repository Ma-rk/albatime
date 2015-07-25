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
    
    NSString *urlString = [NSString stringWithFormat:@"%@/account", BASE_URL];
    urlString = [urlString stringByAppendingString:[NSString stringWithFormat:@"?email=%@&pw=%@", email, password]];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:urlString]];
    [request setHTTPMethod:@"PUT"];
    
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
              NSInteger successIndicator = [[JSON objectForKey:@"result"] integerValue];
              if (successIndicator == 1) {
                  NSMutableDictionary *userCredential = [NSMutableDictionary new];
                  [userCredential setObject:email forKey:@"email"];
                  [userCredential setObject:password forKey:@"password"];
                  // add token info which are aquired from data
                  [userCredential setObject:[[JSON objectForKey:@"data"] objectForKey:@"currentJwToken"] forKey:@"token"];
                  [userCredential setObject:[[JSON objectForKey:@"data"] objectForKey:@"id"] forKey:@"id"];
                  [userCredential setObject:[[JSON objectForKey:@"data"] objectForKey:@"userJwTokenKeySeq"] forKey:@"tokenSeq"];

                  // in LoginViewController.m
                  if ([self.delegate respondsToSelector:@selector(loginSucceed)]) {
                      [self.delegate loginSucceed];
                  }
                  // in LoginModel.m
                  if ([self.delegate respondsToSelector:@selector(loginSucceedWithUserCredential:)]) {
                      [self.delegate loginSucceedWithUserCredential:userCredential];
                  }
              }
              else if (successIndicator == 0){
                  // in LoginViewController.m
                  if ([self.delegate respondsToSelector:@selector(loginFailedWithError:)]) {
                      [self.delegate loginFailedWithError:[JSON objectForKey:@"errorCode"]];
                  }
              }
          }
      }] resume];
}

- (void)signUpWithUserCredential:(NSMutableDictionary *)userCredential {
    
    // set user type
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
            NSInteger successIndicator = [[JSON objectForKey:@"result"] integerValue];
            if (successIndicator == 1) {
                if ([self.delegate respondsToSelector:@selector(signUpSucceed)]) {
                    [self.delegate signUpSucceed];
                }
                if ([self.delegate respondsToSelector:@selector(signUpSucceedWithUserCredential:)]) {
                    // add token info which are aquired from data
                    [userCredential setObject:[[JSON objectForKey:@"data"] objectForKey:@"currentJwToken"] forKey:@"token"];
                    [userCredential setObject:[[JSON objectForKey:@"data"] objectForKey:@"id"] forKey:@"id"];
                    [userCredential setObject:[[JSON objectForKey:@"data"] objectForKey:@"userJwTokenKeySeq"] forKey:@"tokenSeq"];
                    [self.delegate signUpSucceedWithUserCredential:userCredential];
                }
            }
            else if (successIndicator == 0) {
                if ([self.delegate respondsToSelector:@selector(signUpFailedWithError:)]) {
                    [self.delegate signUpFailedWithError:[JSON objectForKey:@"errorCode"]];
                }
            }
        }
        
    }] resume];
}

- (void)sendResetRequest:(NSString *)email {

    [self checkEmailAvailability:email];
    
    NSString *urlString = [NSString stringWithFormat:@"%@/account", BASE_URL];
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
              NSInteger successIndicator = [[JSON objectForKey:@"result"] integerValue];
              if (successIndicator == 1) {
                  if ([self.delegate respondsToSelector:@selector(resetEmailSent)]) {
                      [self.delegate resetEmailSent];
                  }
              }
              else if (successIndicator == 0) {
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

- (void)uplaodNewJobInfo:(NSMutableDictionary *)jobInfo {

    // 헤더에 아래 정보 세팅
    NSString *token = jobInfo[@"token"];
    NSInteger idInt = [jobInfo[@"id"] integerValue];
    NSInteger tokenSeq = [jobInfo[@"tokenSeq"] integerValue];
    
    NSString *name = jobInfo[@"name"];
    NSInteger workTimeUnit = [jobInfo[@"workTimeUnit"] integerValue];
    NSInteger alarmBefore = [jobInfo[@"alarmBefore"] integerValue];
    NSString *RGBColor = jobInfo[@"RGBColor"];
    char unpaidBreakFlag = [jobInfo[@"unpaidBreakFlag"] characterAtIndex:0];
    double defaultWage = [jobInfo[@"defaultWage"] doubleValue];
    // optional
    float taxRate = [jobInfo[@"taxRate"] floatValue];
    
    NSString *urlString = [NSString stringWithFormat:@"%@/actor", BASE_URL];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:urlString]];
    [request setHTTPMethod:@"POST"];
    [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"content-type"];
    [request setValue:@"Header String" forHTTPHeaderField:@"Set-Cookie"];

    NSString *postString = [NSString stringWithFormat:@"name=%@&workTimeUnit=%ld&alarmBefore=%ld&bgColor=%@&unpaidBreakFlag=%c&basicWage=%f&taxRate=%f",
                            name, (long)workTimeUnit, (long)alarmBefore, RGBColor, unpaidBreakFlag, defaultWage, taxRate];
    NSData *data = [postString dataUsingEncoding:NSUTF8StringEncoding];
    [request setHTTPBody:data];
    [request setValue:[NSString stringWithFormat:@"%lu", [data length]] forHTTPHeaderField:@"Content-Length"];
    
    [[self.session dataTaskWithRequest:request
                     completionHandler:^(NSData *data, NSURLResponse *response, NSError *error)
      {
          if (error) {
              NSLog(@"Uplaod new job info failed with Error : %@", error);
              if ([self.delegate respondsToSelector:@selector(createJobFailedWithError:)]) {
                  [self.delegate createJobFailedWithError:[NSString stringWithFormat:@"%@", error]];
              }
          }
          else {
              NSLog(@"Uplaod new job success response : %@", response);
              NSDictionary *JSON = [[NSDictionary alloc] initWithDictionary:[NSJSONSerialization JSONObjectWithData:data
                                                                                                            options:NSJSONReadingAllowFragments
                                                                                                              error:nil]];
              NSLog(@"Data : %@", JSON);
              NSInteger successIndicator = [[JSON objectForKey:@"result"] integerValue];
              if (successIndicator == 1) {
                  if ([self.delegate respondsToSelector:@selector(newJobCreatedWithJobInfo:)]) {
                      [self.delegate newJobCreatedWithJobInfo:[NSDictionary dictionaryWithDictionary:JSON]];
                  }
              }
              else if (successIndicator == 0) {
                  if ([self.delegate respondsToSelector:@selector(createJobFailedWithError:)]) {
                      [self.delegate createJobFailedWithError:[JSON objectForKey:@"errorCode"]];
                  }
              }
          }
      }] resume];
}

@end


