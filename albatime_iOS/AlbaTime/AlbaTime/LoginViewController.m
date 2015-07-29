//
//  LoginViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 1..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "LoginViewController.h"
#import "Definitions.h"
#import "AppDelegate.h"
#import "LoginModel.h"
#import "NetworkHandler.h"

@interface LoginViewController () <UITextFieldDelegate, LoginModelDelegate, NetworkHandlerDelegate>

@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UISwitch *autoLoginSwitch;
@property (weak, nonatomic) IBOutlet UIButton *loginButton;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *activityIndicator;
@property (weak, nonatomic) IBOutlet UIButton *signUpButton;
@property (weak, nonatomic) IBOutlet UIButton *pswdFindButton;

// This textField disabled long-press action by setting subclass ActionDisabledUITextField in IB
@property (weak, nonatomic) IBOutlet UITextField *pswdTextField;

@property (weak, nonatomic) NSString *placeHolderTextForEmail;
@property (weak, nonatomic) NSString *placeHolderTextForPswd;
@property (strong, nonatomic) LoginModel *loginModel;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.loginModel = [(AppDelegate *)[[UIApplication sharedApplication] delegate] loginModel];
    self.loginModel.delegate = self;
    self.loginModel.networkHandler.delegate = self;
}

// load userDefaults and set view elements in viewWillAppear otherwise will have problem when logout or delete account
- (void)viewWillAppear:(BOOL)animated {
    [self.loginModel loadUserDefaults];
    self.navigationController.navigationBar.hidden = YES;
}

- (IBAction)loginButtonTapped:(id)sender {
    if (!self.loginModel.hasNetworkConnection) {
        NSString *title = @"No network connection!";
        NSString *message = @"Please connect internet";
        [self showAlertViewTitle:title
                         message:message];
    }
    else if ([self validateUserInput]) {
        [self.loginModel.networkHandler userAuthenticationWithEmail:self.emailTextField.text
                                                           password:self.pswdTextField.text];
        [self showIndicator];
    }
}

- (BOOL)validateUserInput {
    if (self.emailTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please enter your e-mail";
        [self showAlertViewTitle:title
                         message:message];
        return NO;
    }
    else if (self.pswdTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please enter your password";
        [self showAlertViewTitle:title
                         message:message];
        return NO;
    }
    else {
        if ([self.loginModel validateEmail:self.emailTextField.text]) {
            return YES;
        }
        else {
            NSString *title = @"Invalid e-mail";
            NSString *message = @"Your e-mail is NOT valid, please check again";
            [self showAlertViewTitle:title
                             message:message];
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
    [self performSegueWithIdentifier:@"SignUpSegue"
                              sender:sender];
}

- (IBAction)pswdFindButtonTapped:(id)sender {
    [self performSegueWithIdentifier:@"FindPswdSegue"
                              sender:sender];
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [self.emailTextField endEditing:YES];
    [self.pswdTextField endEditing:YES];
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

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    return YES;
}

#pragma mark - LoginModel Delegate Methods

- (void)savePasswordSucceed {
    NSLog(@"Save access token to keyCahin succeed");
}

- (void)savePasswordFailedWithError:(NSString *)error {
    NSString *title = @"Warning";
    [self showAlertViewTitle:title
                     message:error];
}

   // set view elements right after user defaults are loaded
- (void)setViewElementsAfterUserDefaultsLoaded {
    self.emailTextField.delegate = self;
    self.pswdTextField.delegate = self;
    self.pswdTextField.secureTextEntry = YES;
    
    self.emailTextField.keyboardType = UIKeyboardTypeEmailAddress;
    [self.emailTextField setReturnKeyType:UIReturnKeyDone];
    [self.pswdTextField setReturnKeyType:UIReturnKeyDone];
    
    // 나중에 지역 언어로 변경
    self.placeHolderTextForEmail = @"E-mail address";
    self.placeHolderTextForPswd = @"Password";
    
    self.emailTextField.placeholder = self.placeHolderTextForEmail;
    self.pswdTextField.placeholder = self.placeHolderTextForPswd;
    
    // set default autoLogin state NO
    if (self.loginModel.autoLogin)
        [self.autoLoginSwitch setOn:YES];
    else
        [self.autoLoginSwitch setOn:NO];
    
    // auto fill out email textfield if exists
    if (self.loginModel.email)
        self.emailTextField.text = self.loginModel.email;
    else
        self.emailTextField.text = @"";
    
    // empty out password textfield if exists
    if (self.pswdTextField.text) {
        self.pswdTextField.text = @"";
    }
    
    [self hideIndicator];
}

- (void)showAlertViewTitle:(NSString *)title message:(NSString *)message {
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

- (void)loginSucceedWithEmail:(NSString *)email password:(NSString *)password {
    [self.loginModel saveLoginInfoWithEmail:email
                                   password:password];
    dispatch_async(dispatch_get_main_queue(), ^{
        [self hideIndicator];
        [self performSegueWithIdentifier:@"ToWageViewSegue"
                                  sender:self];
    });
}

- (void)loginFailedWithError:(NSString *)error {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self hideIndicator];
        NSString *title = @"Login failed";
        [self showAlertViewTitle:title
                         message:error];
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
