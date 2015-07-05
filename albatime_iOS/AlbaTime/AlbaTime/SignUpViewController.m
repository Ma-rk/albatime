//
//  SignUpViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 2..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "SignUpViewController.h"
#import "Definitions.h"
#import "AppDelegate.h"
#import "LoginModel.h"
#import "NetworkHandler.h"

// 각각의 값 입력시 오류나면 해당 항목 텍스트필드에 붉은색 라운드처리

@interface SignUpViewController () <UITextFieldDelegate, LoginModelDelegate, NetworkHandlerDelegate>

@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UITextField *pswdTextField;
@property (weak, nonatomic) IBOutlet UITextField *identityTextField;
@property (weak, nonatomic) IBOutlet UITextField *usernameTextField;
@property (weak, nonatomic) IBOutlet UIButton *submitButton;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *activityIndicator;
@property (weak, nonatomic) IBOutlet UIButton *cancelButton;
@property (weak, nonatomic) IBOutlet UIButton *identityButton;
@property (weak, nonatomic) IBOutlet UILabel *invalidEmailWarning;

@property (weak, nonatomic) NSString *placeHolderTextForEmail;
@property (weak, nonatomic) NSString *placeHolderTextForPswd;
@property (weak, nonatomic) NSString *placeHolderTextForIdentity;
@property (weak, nonatomic) NSString *placeHolderTextForUsername;
@property (strong, nonatomic) LoginModel *loginModel;
@property (strong, nonatomic) NetworkHandler *networkHandler;

@property BOOL userGranted;

@end

@implementation SignUpViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.loginModel = [(AppDelegate *)[[UIApplication sharedApplication] delegate] loginModel];
    self.loginModel.delegate = self;
    self.networkHandler.delegate = self;
    
    // set view elements
    [self setTextFieldOption];
    self.activityIndicator.hidden = YES;
    self.invalidEmailWarning.hidden = YES;
    
    [self observeDisconnectedNotification];
}

- (void)setTextFieldOption {
    self.emailTextField.delegate = self;
    self.emailTextField.tag = FIELD_ONE_TAG;
    self.pswdTextField.delegate = self;
    self.usernameTextField.delegate = self;
    self.pswdTextField.secureTextEntry = YES;
    self.identityTextField.userInteractionEnabled = NO;
    
    [self.emailTextField setReturnKeyType:UIReturnKeyDone];
    [self.pswdTextField setReturnKeyType:UIReturnKeyDone];
    [self.usernameTextField setReturnKeyType:UIReturnKeyDone];
    
    // 나중에 지역 언어로 변경
    self.placeHolderTextForEmail = @"E-main address";
    self.placeHolderTextForPswd = @"Password";
    self.placeHolderTextForIdentity = @"Who are you?";
    self.placeHolderTextForUsername = @"Username";
    
    self.emailTextField.placeholder = self.placeHolderTextForEmail;
    self.pswdTextField.placeholder = self.placeHolderTextForPswd;
    self.identityTextField.placeholder = self.placeHolderTextForIdentity;
    self.usernameTextField.placeholder = self.placeHolderTextForUsername;
}

- (IBAction)submitButtonTapped:(id)sender {
    if ([self validateUserInput]) {
        NSMutableDictionary *userCredential = [NSMutableDictionary new];
        [userCredential setObject:self.emailTextField.text forKey:@"email"];
        [userCredential setObject:self.pswdTextField.text forKey:@"password"];
        [userCredential setObject:self.identityTextField.text forKey:@"identity"];
        [userCredential setObject:self.usernameTextField.text forKey:@"username"];
        
        self.activityIndicator.hidden = NO;
        [self.activityIndicator startAnimating];
        [self.view setUserInteractionEnabled:NO];

        [self.loginModel signUpWithUserCredential:userCredential];
    }
}

- (BOOL)validateUserInput {
    if (self.emailTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please enter your e-mail";
        [self showAlertViewTitle:title withMessage:message];
        return NO;
    } else if (self.pswdTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please enter your password";
        [self showAlertViewTitle:title withMessage:message];
        return NO;
    } else if (self.identityTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please tell us who you are";
        [self showAlertViewTitle:title withMessage:message];
        return NO;
    } else if (self.usernameTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please enter your username";
        [self showAlertViewTitle:title withMessage:message];
        return NO;
    } else if ([self validateEmail:self.emailTextField.text]){
        return YES;
    }
    return NO;
}

- (BOOL)validateEmail:(NSString *)candidate {
    if ([self.loginModel validateEmail:candidate]) {
        if ([self.loginModel checkEmailAvailability:candidate]) {
            return YES;
        }
        else {
            NSString *title = @"E-mail in use";
            NSString *message = @"Your e-mail is already in use, please try different e-mail";
            [self showAlertViewTitle:title withMessage:message];
            return NO;
        }
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

- (IBAction)identityButtonTapped:(id)sender {
    
    UIAlertController *alertController = [UIAlertController
                                          alertControllerWithTitle:@"Who are you?"
                                          message:@"Please tell us who you are"
                                          preferredStyle:UIAlertControllerStyleActionSheet];
    
    UIAlertAction *employeeAction = [UIAlertAction
                                   actionWithTitle:NSLocalizedString(@"I'm an employee", @"I'm an employee action")
                                   style:UIAlertActionStyleDefault
                                   handler:^(UIAlertAction *action)
                                   {
                                       self.identityTextField.text = @"I'm an employee";
                                   }];
    UIAlertAction *employerAction = [UIAlertAction
                                   actionWithTitle:NSLocalizedString(@"I'm an employer", @"I'm an employer action")
                                   style:UIAlertActionStyleDefault
                                   handler:^(UIAlertAction *action)
                                   {
                                       self.identityTextField.text = @"I'm an employer";
                                   }];
    
    UIAlertAction *cancelAction = [UIAlertAction
                                   actionWithTitle:NSLocalizedString(@"Cancel", @"Canel action")
                                   style:UIAlertActionStyleDestructive
                                   handler:^(UIAlertAction *action)
                                   {
                                       self.identityTextField.text = nil;
                                   }];
    
    [alertController addAction:employeeAction];
    [alertController addAction:employerAction];
    [alertController addAction:cancelAction];
    
    UIPopoverPresentationController *popover = alertController.popoverPresentationController;
    if (popover)
    {
        UIButton *tempButton = (UIButton *)sender;
        popover.sourceView = sender;
        popover.sourceRect = tempButton.bounds;
        popover.permittedArrowDirections = UIPopoverArrowDirectionAny;
    }
    
    [self presentViewController:alertController animated:YES completion:nil];
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [self.emailTextField endEditing:YES];
    [self.pswdTextField endEditing:YES];
    [self.usernameTextField endEditing:YES];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - UITextField Delegate Methods

- (void)textFieldDidBeginEditing:(nonnull UITextField *)textField {
    if (textField.tag == FIELD_ONE_TAG) {
        self.invalidEmailWarning.hidden = YES;
    }
}

- (void)textFieldDidEndEditing:(nonnull UITextField *)textField {
    if (textField.text.length > 0 && textField.tag == FIELD_ONE_TAG) {
        [self instantValidateEmail:textField.text];
    }
}

- (void)instantValidateEmail:(NSString *)candidate {
    if ([self.loginModel validateEmail:candidate]) {
        if ([self.loginModel checkEmailAvailability:candidate]) {
            self.invalidEmailWarning.text = @"This e-mail is good to go!";
            self.invalidEmailWarning.textColor = [UIColor blueColor];
            self.invalidEmailWarning.hidden = NO;
        }
        else {
            self.invalidEmailWarning.text = @"This e-mail is already in use";
            self.invalidEmailWarning.hidden = NO;
        }
    }
    else {
        self.invalidEmailWarning.text = @"Invalid e-mail, please check again";
        self.invalidEmailWarning.hidden = NO;
    }
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
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

#pragma mark - NetworkHandler Delegate Methods

- (void)signUpSucceed {
    [self.activityIndicator stopAnimating];
    [self dismissViewControllerAnimated:YES completion:nil];
    // SignUp 화면을 내려서 로그인 화면에 이메일 자동으로 삽입되어 있는지 확인 / 아님 바로 Wage View 보여주던가
}

- (void)signUpFailed {
    NSString *title = @"SignUp failed";
    NSString *message = @"SignUp failed with unknown reason";
    [self showAlertViewTitle:title withMessage:message];
    [self.activityIndicator stopAnimating];
    [self.view setUserInteractionEnabled:YES];
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

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
