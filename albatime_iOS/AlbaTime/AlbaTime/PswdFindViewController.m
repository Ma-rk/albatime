//
//  PswdFindViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 2..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "PswdFindViewController.h"
#import "AppDelegate.h"
#import "LoginModel.h"
#import "NetworkHandler.h"

@interface PswdFindViewController () <UITextFieldDelegate, LoginModelDelegate, NetworkHandlerDelegate>
@property (weak, nonatomic) IBOutlet UILabel *invalidEmailWarning;
@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UIButton *sendButton;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *activityIndicator;
@property (weak, nonatomic) IBOutlet UILabel *resetRequestResult;

@property (weak, nonatomic) NSString *placeHolderTextForEmail;
@property (strong, nonatomic) LoginModel *loginModel;
@property (strong, nonatomic) NetworkHandler *networkHandler;

@end

@implementation PswdFindViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.networkHandler = [(AppDelegate *)[[UIApplication sharedApplication] delegate] networkHandler];
    self.loginModel = [(AppDelegate *)[[UIApplication sharedApplication] delegate] loginModel];
    [self observeDisconnectedNotification];
    
    [self setViewElements];
}

- (void)setViewElements {
    self.title = @"Find Password";
    
    [self hideIndicator];
    self.invalidEmailWarning.hidden = YES;
    self.resetRequestResult.hidden = YES;
    self.placeHolderTextForEmail = @"E-main address";
    self.emailTextField.placeholder = self.placeHolderTextForEmail;
    self.emailTextField.delegate = self;
    [self.emailTextField setReturnKeyType:UIReturnKeyDone];
}

- (void)viewWillAppear:(BOOL)animated {
    self.navigationController.navigationBar.hidden = NO;
}

- (IBAction)sendButtonTapped:(id)sender {
    NSString *email = self.emailTextField.text;
    if (email.length == 0) {
        NSString *title = @"E-mail is empty!";
        NSString *message = @"Please enter your e-mail";
        [self showAlertViewTitle:title withMessage:message];
    }
    else if (email.length > 0 && [self validateEmail:email]){
        self.activityIndicator.hidden = NO;
        [self.activityIndicator startAnimating];
        [self.view setUserInteractionEnabled:NO];
        
        [self.networkHandler sendResetRequest:email];
    }
}

- (BOOL)validateEmail:(NSString *)candidate {
    if ([self.loginModel validateEmail:candidate]) {
        if ([self.networkHandler checkEmailAvailability:candidate]) {
            NSString *title = @"E-mail NOT found";
            NSString *message = @"Your e-mail is NOT found on our server";
            [self showAlertViewTitle:title withMessage:message];
            return NO;
        }
        else
            return YES;
    }
    else {
        NSString *title = @"Invalid e-mail";
        NSString *message = @"Your e-mail is NOT valid, please check again";
        [self showAlertViewTitle:title withMessage:message];
        return NO;
    }
    return NO;
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [self.emailTextField endEditing:YES];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)showIndicator {
    [self.view setUserInteractionEnabled:NO];
    self.activityIndicator.hidden = NO;
    [self.activityIndicator startAnimating];
}

- (void)hideIndicator {
    [self.view setUserInteractionEnabled:YES];
    [self.activityIndicator stopAnimating];
    self.activityIndicator.hidden = YES;
}

#pragma mark - UITextField Delegate Methods

- (void)textFieldDidBeginEditing:(nonnull UITextField *)textField {
        self.invalidEmailWarning.hidden = YES;
}

- (void)textFieldDidEndEditing:(nonnull UITextField *)textField {
    if (textField.text.length > 0) {
        [self instantValidateEmail:textField.text];
    }
}

- (void)instantValidateEmail:(NSString *)candidate {
    if (![self.loginModel validateEmail:candidate]) {
        self.invalidEmailWarning.text = @"Invalid e-mail, please check again";
        self.invalidEmailWarning.hidden = NO;
    }
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    return YES;
}

#pragma mark - Set internet disconnection notification

- (void)observeDisconnectedNotification {
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(disconnectionAlert:)
                                                 name:@"networkDisconnected"
                                               object:nil];
}

- (void)disconnectionAlert:(NSNotification *)notification {
    NSString *title = @"WARNING!\nYou have no network connection!";
    NSString *message = @"Connect internet before doing further modification, otherwise you may lose your recent changes";
    [self showAlertViewTitle:title withMessage:message];
}

- (void)showAlertViewTitle:(NSString *)title withMessage:(NSString *)message {
    UIAlertController *alertController = [UIAlertController
                                          alertControllerWithTitle:title
                                          message:message
                                          preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *okAction = [UIAlertAction
                               actionWithTitle:NSLocalizedString(@"OK", @"OK action")
                               style:UIAlertActionStyleDefault
                               handler:^(UIAlertAction *action)
                               {
                                   NSLog(@"ok button tapped");
                               }];
    [alertController addAction:okAction];
    [self presentViewController:alertController animated:YES completion:nil];
}

#pragma mark - NetworkHandler Delegate Methods

- (void)resetEmailSent {
    [self hideIndicator];
    self.resetRequestResult.text = @"E-mail has been sent successfully";
    self.resetRequestResult.hidden = NO;
}

- (void)resetEmailNotSent {
    [self hideIndicator];
    
    UIAlertController *alertController = [UIAlertController
                                          alertControllerWithTitle:@"E-mail NOT sent"
                                          message:@"Failed to send reset request e-mail"
                                          preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *retryAction = [UIAlertAction
                                  actionWithTitle:NSLocalizedString(@"Retry", @"Retry action")
                                  style:UIAlertActionStyleCancel
                                  handler:^(UIAlertAction *action)
                                  {
                                      NSString *email = self.emailTextField.text;
                                      [self.networkHandler sendResetRequest:email];
                                  }];
    
    UIAlertAction *okAction = [UIAlertAction
                               actionWithTitle:NSLocalizedString(@"OK", @"OK action")
                               style:UIAlertActionStyleDefault
                               handler:^(UIAlertAction *action)
                               {
                                   NSLog(@"OK tapped");
                               }];
    [alertController addAction:retryAction];
    [alertController addAction:okAction];
    [self presentViewController:alertController animated:YES completion:nil];
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
