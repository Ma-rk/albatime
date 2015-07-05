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
@property (weak, nonatomic) IBOutlet UIButton *cancelButton;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *activityIndicator;
@property (weak, nonatomic) IBOutlet UILabel *resetRequestResult;

@property (weak, nonatomic) NSString *placeHolderTextForEmail;
@property (strong, nonatomic) LoginModel *loginModel;
@property (strong, nonatomic) NetworkHandler *networkHandler;


@end

@implementation PswdFindViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.loginModel = [(AppDelegate *)[[UIApplication sharedApplication] delegate] loginModel];
    [self observeDisconnectedNotification];
    
    // set view elements
    [self setTextFieldOption];
}

- (void)setTextFieldOption {
    self.activityIndicator.hidden = YES;
    self.invalidEmailWarning.hidden = YES;
    self.resetRequestResult.hidden = YES;
    
    self.placeHolderTextForEmail = @"E-main address";
    self.emailTextField.placeholder = self.placeHolderTextForEmail;
    self.emailTextField.delegate = self;
    [self.emailTextField setReturnKeyType:UIReturnKeyDone];
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
        
        [self.loginModel sendResetRequest:email];
    }
}

- (BOOL)validateEmail:(NSString *)candidate {
    if ([self.loginModel validateEmail:candidate]) {
        if ([self.loginModel checkEmailAvailability:candidate]) {
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

- (IBAction)cancelButtonTapped:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [self.emailTextField endEditing:YES];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
    self.resetRequestResult.text = @"E-mail has been sent successfully";
    self.resetRequestResult.hidden = NO;
    
    self.activityIndicator.hidesWhenStopped = YES;
    [self.activityIndicator stopAnimating];
    [self.view setUserInteractionEnabled:YES];
}

- (void)resetEmailNotSent {
    self.resetRequestResult.text = @"Sending e-amil failed";
    self.resetRequestResult.hidden = NO;
    
    self.activityIndicator.hidesWhenStopped = YES;
    [self.activityIndicator stopAnimating];
    [self.view setUserInteractionEnabled:YES];
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
