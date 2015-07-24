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
    else if (![self.loginModel validateEmail:email]){
        NSString *title = @"Invalid email!";
        NSString *message = @"Invalid email, please check again";
        [self showAlertViewTitle:title withMessage:message];
    }
    else {
        [self showIndicator];
        [self.networkHandler sendResetRequest:email];
    }
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
        self.resetRequestResult.hidden = NO;
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

- (void)emailCheckResult:(NSInteger)result {
    dispatch_sync(dispatch_get_main_queue(), ^{
        if (result == 0) {
            NSString *title = @"Email Not Found!";
            NSString *message = @"Your email has not found in our server";
            [self showAlertViewTitle:title withMessage:message];
        }
        else if (result >= 2){
            NSString *title = @"Find Email Error";
            NSString *message = @"Error occurred when finding your email";
            [self showAlertViewTitle:title withMessage:message];
        }
    });
}

- (void)resetEmailSent {
    dispatch_sync(dispatch_get_main_queue(), ^{
        [self hideIndicator];
        self.resetRequestResult.text = @"E-mail has been sent successfully";
        self.resetRequestResult.hidden = NO;
    });
}

- (void)resetEmailNotSent {
    dispatch_sync(dispatch_get_main_queue(), ^{
        [self hideIndicator];
        self.resetRequestResult.text = @"E-mail NOT sent";
        self.resetRequestResult.hidden = NO;
    });
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
