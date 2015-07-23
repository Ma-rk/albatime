//
//  NetworkHandler.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 2..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "NetworkHandler.h"
#import "AFNetworking.h"
#import "Definitions.h"

@implementation NetworkHandler

- (instancetype)init
{
    self = [super init];
    if (self) {
        // generate NSURLSession here
        
    }
    return self;
}

    // login request
- (void)userAuthenticationWithEmail:(NSString *)email andPassword:(NSString *)password {

//        if ([self.delegate respondsToSelector:@selector(loginSucceed)]) {
//            [self.delegate loginSucceed];
//        }
//        if ([self.delegate respondsToSelector:@selector(loginSucceedWithEmail:andPassword:)]) {
//            [self.delegate loginSucceedWithEmail:email andPassword:password];
//        }
//    }
//    // else if fail
//    else {
//        if ([self.delegate respondsToSelector:@selector(loginFailed)]) {
//            [self.delegate loginFailed];
//        }
//    }
}

- (void)signUpWithUserCredential:(NSMutableDictionary *)userCredential {

    NSString *urlString = [NSString stringWithFormat:@"%@/account", BASE_URL];
    
    NSString *type = [NSString new];
    if ([userCredential[@"identity"] isEqualToString:@"I'm an employee"]) {
        type = USR_TYP_01;
    }
    else if ([userCredential[@"identity"] isEqualToString:@"I'm an employer"]) {
        type = USR_TYP_02;
    }
    NSMutableDictionary *params = [NSMutableDictionary new];
    [params setObject:userCredential[@"email"] forKey:@"mail"];
    [params setObject:userCredential[@"password"] forKey:@"pw"];
    [params setObject:userCredential[@"username"] forKey:@"nick"];
    [params setObject:type forKey:@"type"];
    
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:urlString]];
    [request setHTTPMethod:@"POST"];
    [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"content-type"];
    NSString *postString = [NSString stringWithFormat:@"email=%@&pw=%@&nick=%@&type=%@", userCredential[@"email"], userCredential[@"password"], userCredential[@"username"], type];
    NSData *data = [postString dataUsingEncoding:NSUTF8StringEncoding];
    [request setHTTPBody:data];
    [request setValue:[NSString stringWithFormat:@"%lu", [data length]] forHTTPHeaderField:@"Content-Length"];
    //[NSURLConnection connectionWithRequest:request delegate:self];
    
    NSOperationQueue *queue = [NSOperationQueue new];
    [NSURLConnection sendAsynchronousRequest:request
                                       queue:queue
                           completionHandler:^(NSURLResponse *response, NSData *data, NSError *connectionError)
     {
         if ([data length] > 0 && connectionError == nil) {
             [[NSNotificationCenter defaultCenter] postNotificationName:@"refreshData"
                                                                 object:nil
                                                               userInfo:nil];
             NSLog(@"Success");
             NSLog(@"response : %@", response);
         }
         else {
             NSLog(@"Failed");
             NSLog(@"response : %@", response);
         }
     }];
    
//    NSMutableURLRequest *request = [[AFHTTPRequestSerializer serializer] multipartFormRequestWithMethod:@"POST"
//                                                                                              URLString:urlString
//                                                                                             parameters:params
//                                                                              constructingBodyWithBlock:^(id<AFMultipartFormData> formData)
//    {
//        [formData appendPartWithFormData:[params[@"mail"] dataUsingEncoding:NSUTF8StringEncoding]
//                                    name:@"mail"];
//        [formData appendPartWithFormData:[params[@"pw"] dataUsingEncoding:NSUTF8StringEncoding]
//                                    name:@"pw"];
//        [formData appendPartWithFormData:[params[@"nick"] dataUsingEncoding:NSUTF8StringEncoding]
//                                    name:@"nick"];
//        [formData appendPartWithFormData:[params[@"type"] dataUsingEncoding:NSUTF8StringEncoding]
//                                    name:@"type"];
//    } error:nil];
//    
//    AFHTTPSessionManager *manager = [[AFHTTPSessionManager alloc] initWithSessionConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]];
//    NSProgress *progress = nil;
    
    // hack to allow 'text/plain' content-type to work
//    NSMutableSet *contentTypes = [NSMutableSet setWithSet:manager.responseSerializer.acceptableContentTypes];
//    [contentTypes addObject:@"text/html"];
//    manager.responseSerializer.acceptableContentTypes = contentTypes;
    
//    AFCompoundResponseSerializer *responseSerializer = [AFCompoundResponseSerializer serializer];
//    responseSerializer.acceptableContentTypes = [NSSet setWithObject:@"text/html"];
//    
//    manager.responseSerializer = responseSerializer;
//    
//    NSURLSessionUploadTask *uploadTask = [manager uploadTaskWithStreamedRequest:request
//                                                                       progress:&progress
//                                                              completionHandler:^(NSURLResponse *response, id responseObject, NSError *error)
//    {
//        if (error) {
//            NSLog(@"Error: %@", error);
//            [self.delegate signUpFailed];
//        } else {
//            NSLog(@"%@ %@", response, responseObject);
//            [self.delegate signUpSucceed];
//            [self.delegate signUpSucceedWithUserCredential:userCredential];
//        }
//    }];
//    
//    [uploadTask resume];
    
/********************************************FUCKING OBSOLETE CODE****************************************/
    
//    NSString *boundary = @"123456789";
//    NSURL *url = [NSURL URLWithString:urlString];
//    NSMutableURLRequest *urlRequest = [NSMutableURLRequest requestWithURL:url];
//    [urlRequest setCachePolicy:NSURLRequestReloadIgnoringLocalCacheData];
//    [urlRequest setTimeoutInterval:30.0f];
//    [urlRequest setHTTPMethod:@"POST"];
//    NSString *contentType = [NSString stringWithFormat:@"multipart/form-data; boundary=%@",boundary];
//    [urlRequest setValue:contentType forHTTPHeaderField:@"Content-Type"];
//    
//    NSMutableData *body = [NSMutableData data];
//    
//    [body appendData:[[NSString stringWithFormat:@"--%@\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];
//    [body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"%@\"\r\n\r\n", @"mail"] dataUsingEncoding:NSUTF8StringEncoding]];
//    [body appendData:[[NSString stringWithFormat:@"%@\r\n", userCredential[@"email"]] dataUsingEncoding:NSUTF8StringEncoding]];
//    
//    [body appendData:[[NSString stringWithFormat:@"--%@\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];
//    [body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"%@\"\r\n\r\n", @"pw"] dataUsingEncoding:NSUTF8StringEncoding]];
//    [body appendData:[[NSString stringWithFormat:@"%@\r\n", userCredential[@"password"]] dataUsingEncoding:NSUTF8StringEncoding]];
//    
//    [body appendData:[[NSString stringWithFormat:@"--%@\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];
//    [body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"%@\"\r\n\r\n", @"type"] dataUsingEncoding:NSUTF8StringEncoding]];
//    [body appendData:[[NSString stringWithFormat:@"%@\r\n", type] dataUsingEncoding:NSUTF8StringEncoding]];
//    
//    [body appendData:[[NSString stringWithFormat:@"--%@\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];
//    [body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"%@\"\r\n\r\n", @"nick"] dataUsingEncoding:NSUTF8StringEncoding]];
//    [body appendData:[[NSString stringWithFormat:@"%@\r\n", userCredential[@"username"]] dataUsingEncoding:NSUTF8StringEncoding]];
//    
//    [body appendData:[[NSString stringWithFormat:@"\r\n"] dataUsingEncoding:NSUTF8StringEncoding]];
//    [body appendData:[[NSString stringWithFormat:@"--%@--\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];
//    
//    [urlRequest setHTTPBody:body];
//    
//    NSString *postLength = [NSString stringWithFormat:@"%lu", (unsigned long)[body length]];
//    [urlRequest setValue:postLength forHTTPHeaderField:@"Content-Length"];
//    
//    NSOperationQueue *queue = [NSOperationQueue new];
//    [NSURLConnection sendAsynchronousRequest:urlRequest
//                                       queue:queue
//                           completionHandler:^(NSURLResponse *response, NSData *data, NSError *connectionError)
//     {
//         if ([data length] > 0 && connectionError == nil) {
//             NSLog(@"Success");
//             NSLog(@"response : %@", response);
//         }
//         else {
//             NSLog(@"Failed");
//             NSLog(@"response : %@", response);
//         }
//     }];
}

- (void)sendResetRequest:(NSString *)email {
    [self performSelector:@selector(resetEmailSent) withObject:nil afterDelay:2.0f];
    
    /*
    if (YES) {
        if ([self.delegate respondsToSelector:@selector(resetEmailSent)]) {
            [self.delegate resetEmailSent];
        }
    }
    // else if fail
    else {
        if ([self.delegate respondsToSelector:@selector(resetEmailNotSent)]) {
            [self.delegate resetEmailNotSent];
        }
    }
     */
}


- (BOOL)checkEmailAvailability:(NSString *)email {
    return YES;
}

@end


