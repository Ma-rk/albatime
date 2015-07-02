//
//  SignUpViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 2..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "SignUpViewController.h"
#import "Definitions.h"
#import "NetworkHandler.h"
#import "SSKeychain.h"

// 각각의 값 입력시 오류나면 해당 항목 텍스트필드에 붉은색 라운드처리

@interface SignUpViewController () <UITextFieldDelegate>

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
@property (strong, nonatomic) NetworkHandler *networkHandler;

@property BOOL userGranted;

@end

@implementation SignUpViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setTextFieldOption];
    self.activityIndicator.hidden = YES;
    self.invalidEmailWarning.hidden = YES;
    self.networkHandler = [NetworkHandler new];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(disconnectionAlert:)
                                                 name:@"networkDisconnected"
                                               object:nil];
}

- (void)setTextFieldOption {
    self.emailTextField.delegate = self;
    self.pswdTextField.delegate = self;
    self.usernameTextField.delegate = self;
    self.emailTextField.tag = FIELD_ONE_TAG;
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

        [self uploadUserCredential:userCredential];
        
        if (self.userGranted) {
            [self savePassword];
            [self signUpSucceed];
        }
        else {
            // 등록 실패 원인 알람
            [self.view setUserInteractionEnabled:YES];
            [self.activityIndicator stopAnimating];
        }
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
    NSString *emailRegex =
    @"(?:[a-z0-9!#$%\\&'*+/=?\\^_`{|}~-]+(?:\\.[a-z0-9!#$%\\&'*+/=?\\^_`{|}"
    @"~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\"
    @"x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-"
    @"z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5"
    @"]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-"
    @"9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21"
    @"-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES[c] %@", emailRegex];
    
    if ([emailTest evaluateWithObject:candidate]) {
        if ([self.networkHandler checkEmailAvailability:candidate]) {
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

- (void)uploadUserCredential:(NSMutableDictionary *)userCredential {
    self.activityIndicator.hidden = NO;
    [self.activityIndicator startAnimating];
    [self.view setUserInteractionEnabled:NO];
    
    [self.networkHandler uploadUserCredential:userCredential];
}

- (void)savePassword {
    if (![SSKeychain setPassword:self.pswdTextField.text forService:SERVICE_TITLE account:self.emailTextField.text]) {
        UIAlertController *alertController = [UIAlertController
                                              alertControllerWithTitle:@"Warning"
                                              message:@"Failed to save password in keychain"
                                              preferredStyle:UIAlertControllerStyleAlert];
        UIAlertAction *retryAction = [UIAlertAction
                                      actionWithTitle:NSLocalizedString(@"Retry", @"Retry action")
                                      style:UIAlertActionStyleCancel
                                      handler:^(UIAlertAction *action)
                                      {
                                          [self savePassword];
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
}

- (void)signUpSucceed {
    [self.activityIndicator stopAnimating];
    [self saveUserDefaults];
    [self dismissViewControllerAnimated:YES completion:nil];
    // SignUp 화면을 내려서 로그인 화면에 이메일 자동으로 삽입되어 있는지 확인 / 아님 바로 Wage View 보여주던가
}

- (void)saveUserDefaults {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:self.emailTextField.text forKey:@"email"];
    [defaults synchronize];
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

- (void)disconnectionAlert:(NSNotification *)notification {
    NSString *title = @"WARNING!\nYou have no network connection!";
    NSString *message = @"Connect internet before doing further modification, otherwise you may lose your recent changes";
    [self showAlertViewTitle:title withMessage:message];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - UITextField Delegate Methods

- (void)textFieldDidBeginEditing:(nonnull UITextField *)textField {
    if (textField.tag == 1) {
        self.invalidEmailWarning.hidden = YES;
    }
}

- (void)textFieldDidEndEditing:(nonnull UITextField *)textField {
    if (textField.tag == 1 && textField.text.length > 0) {
        [self fastValidateEmail:textField.text];
    }
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}

- (void)fastValidateEmail:(NSString *)candidate {
    NSString *emailRegex =
    @"(?:[a-z0-9!#$%\\&'*+/=?\\^_`{|}~-]+(?:\\.[a-z0-9!#$%\\&'*+/=?\\^_`{|}"
    @"~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\"
    @"x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-"
    @"z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5"
    @"]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-"
    @"9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21"
    @"-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES[c] %@", emailRegex];
    
    if ([emailTest evaluateWithObject:candidate]) {
        if ([self.networkHandler checkEmailAvailability:candidate]) {
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

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end