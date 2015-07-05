//
//  LoginViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 1..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "LoginViewController.h"
#import "Definitions.h"
#import "SignUpViewController.h"
#import "WageViewController.h"
#import "PswdFindViewController.h"
#import "AppDelegate.h"
#import "LoginModel.h"
#import "NetworkHandler.h"

@interface LoginViewController () <UITextFieldDelegate, LoginModelDelegate, NetworkHandlerDelegate>

@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UITextField *pswdTextField;
@property (weak, nonatomic) IBOutlet UISwitch *autoLoginSwitch;
@property (weak, nonatomic) IBOutlet UIButton *loginButton;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *activityIndicator;
@property (weak, nonatomic) IBOutlet UIButton *signUpButton;
@property (weak, nonatomic) IBOutlet UIButton *pswdFindButton;

@property (weak, nonatomic) NSString *placeHolderTextForEmail;
@property (weak, nonatomic) NSString *placeHolderTextForPswd;
@property (strong, nonatomic) LoginModel *loginModel;
@property (strong, nonatomic) NetworkHandler *networkHandler;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.loginModel = [(AppDelegate *)[[UIApplication sharedApplication] delegate] loginModel];
    self.loginModel.delegate = self;
    self.networkHandler.delegate = self;

    [self.loginModel loadUserDefaults];
    [self observeDisconnectedNotification];
    
    [self setViewElements];
    
    // auto login process
    if (self.loginModel.email) {
        self.emailTextField.text = self.loginModel.email;
        
        if (self.loginModel.autoLogin) {
            [self.loginModel tryAutoLogin];
        }
    }
}

- (void)setViewElements {
    self.emailTextField.delegate = self;
    self.pswdTextField.delegate = self;
    self.pswdTextField.secureTextEntry = YES;
    
    [self.emailTextField setReturnKeyType:UIReturnKeyDone];
    [self.pswdTextField setReturnKeyType:UIReturnKeyDone];
    
    // 나중에 지역 언어로 변경
    self.placeHolderTextForEmail = @"E-main address";
    self.placeHolderTextForPswd = @"Password";
    
    self.emailTextField.placeholder = self.placeHolderTextForEmail;
    self.pswdTextField.placeholder = self.placeHolderTextForPswd;
    
    self.activityIndicator.hidden = YES;
}

- (IBAction)loginButtonTapped:(id)sender {
    if ([self validateUserInput]) {
        [self.loginModel userAuthenticationWithEmail:self.emailTextField.text
                                         andPassword:self.pswdTextField.text];
        self.activityIndicator.hidden = NO;
        [self.view setUserInteractionEnabled:NO];
        [self.activityIndicator startAnimating];
    }
}

- (BOOL)validateUserInput {
    if (self.emailTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please enter your e-mail";
        [self showAlertViewTitle:title withMessage:message];
        return NO;
    }
    else if (self.pswdTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please enter your password";
        [self showAlertViewTitle:title withMessage:message];
        return NO;
    }
    else {
        if ([self.loginModel validateEmail:self.emailTextField.text]) {
            return YES;
        }
        else {
            NSString *title = @"Invalid e-mail";
            NSString *message = @"Your e-mail is NOT valid, please check again";
            [self showAlertViewTitle:title withMessage:message];
            return NO;
        }
    }
    return NO;
}

- (IBAction)autoLoginChanged:(id)sender {
    if ([self.autoLoginSwitch isOn])
        self.loginModel.autoLogin = YES;
    else
        self.loginModel.autoLogin = NO;
}

- (IBAction)signUpButtonTapped:(id)sender {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    SignUpViewController *suvc = [storyboard instantiateViewControllerWithIdentifier:@"SignUpViewController"];
    [self presentViewController:suvc animated:YES completion:nil];
}

- (IBAction)pswdFindButtonTapped:(id)sender {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    PswdFindViewController *pfvc = [storyboard instantiateViewControllerWithIdentifier:@"PswdFindViewController"];
    [self presentViewController:pfvc animated:YES completion:nil];
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [self.emailTextField endEditing:YES];
    [self.pswdTextField endEditing:YES];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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

#pragma mark - UITextField Delegate Methods

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}

#pragma mark - LoginModel Delegate Methods

- (void)savePswdSucceed {
    NSLog(@"Save password to keyCahin succeed");
}

- (void)savePswdFailed {
    NSString *title = @"Warning";
    NSString *message = @"Failed to save password in keychain";
    [self showAlertViewTitle:title withMessage:message];
}

- (void)findPswdSucceed {
    // found password so process auto login
    self.activityIndicator.hidden = NO;
    [self.view setUserInteractionEnabled:NO];
    [self.activityIndicator startAnimating];
    
    // set dummy text in password texfield for security reason
    self.pswdTextField.text = @"Ub!$o5%p";
}

- (void)findPswdFailed {
    NSLog(@"Find password from keychain failed");
}

#pragma mark - NetworkHandler Delegate Methods

- (void)loginSucceed {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    WageViewController *wvc = [storyboard instantiateViewControllerWithIdentifier:@"WageViewController"];
    [self.activityIndicator stopAnimating];
    [self presentViewController:wvc animated:YES completion:nil];
}

- (void)loginFailed {
    NSString *title = @"Login failed";
    NSString *message = @"E-mail or password is NOT valid, please check again";
    [self showAlertViewTitle:title withMessage:message];
    [self.view setUserInteractionEnabled:YES];
    [self.activityIndicator stopAnimating];
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
