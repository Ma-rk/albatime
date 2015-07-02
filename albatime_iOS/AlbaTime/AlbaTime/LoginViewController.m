//
//  LoginViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 1..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "LoginViewController.h"
#import "Definitions.h"
#import "SSKeychain.h"
#import "NetworkHandler.h"
#import "WageViewController.h"
#import "SignUpViewController.h"
#import "WageViewController.h"
#import "PswdFindViewController.h"

@interface LoginViewController () <UITextFieldDelegate>

@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UITextField *pswdTextField;
@property (weak, nonatomic) IBOutlet UISwitch *autoLoginSwitch;
@property (weak, nonatomic) IBOutlet UIButton *loginButton;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *activityIndicator;
@property (weak, nonatomic) IBOutlet UIButton *signUpButton;
@property (weak, nonatomic) IBOutlet UIButton *pswdFindButton;

@property (weak, nonatomic) NSString *placeHolderTextForEmail;
@property (weak, nonatomic) NSString *placeHolderTextForPswd;
@property (nonatomic, strong) NSString *email;
@property (nonatomic, strong) NetworkHandler *networkHandler;

@property BOOL autoLogin;
@property BOOL userGranted;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self loadUserDefaults];
    [self setTextFieldOption];
    self.activityIndicator.hidden = YES;
    self.networkHandler = [NetworkHandler new];
    
    if (self.email) {
        self.emailTextField.text = self.email;
        
        if (self.autoLogin) {
            NSString *password = [SSKeychain passwordForService:SERVICE_TITLE account:self.emailTextField.text];

            if (password.length > 0) {
                [self userAuthenticationWithEmail:self.email andPassword:password];
                
                // set password textfield with garbage data for security reason - autologin only
                self.pswdTextField.text = @"Ub!$o5%p";
                if (self.userGranted) {
                    [self loginSucceed];
                }
                else {
                    // 로그인 실패 원인 알람, 비번이 틀렸을 경우에는 비번 칸 비우고 플레이스홀더 세팅, 이메일은 틀렸어도 그냥 둠
                }
            }
        }
    }
}

- (BOOL)validateEmail: (NSString *)candidate {
    NSString *emailRegex =
    @"(?:[a-z0-9!#$%\\&'*+/=?\\^_`{|}~-]+(?:\\.[a-z0-9!#$%\\&'*+/=?\\^_`{|}"
    @"~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\"
    @"x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-"
    @"z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5"
    @"]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-"
    @"9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21"
    @"-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES[c] %@", emailRegex];
    
    return [emailTest evaluateWithObject:candidate];
}

- (void)setTextFieldOption {
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
}

- (IBAction)loginButtonTapped:(id)sender {
    if ([self validateUserInput]) {
        [self userAuthenticationWithEmail:self.emailTextField.text andPassword:self.pswdTextField.text];
        
        if (self.userGranted) {
            [self savePassword];
            [self loginSucceed];
        }
        else {
            // 로그인 실패 원인 알람, 비번이 틀렸을 경우에는 비번 칸 비우고 플레이스홀더 세팅, 이메일은 틀렸어도 그냥 둠
            NSString *title = @"Login failed";
            NSString *message = @"E-mail or password is NOT valid, please check again";
            [self showAlertViewTitle:title withMessage:message];
            [self.view setUserInteractionEnabled:YES];
            [self.activityIndicator stopAnimating];
        }
    }
}

- (void)userAuthenticationWithEmail:(NSString *)email andPassword:(NSString *)password {
    self.activityIndicator.hidden = NO;
    [self.view setUserInteractionEnabled:NO];
    [self.activityIndicator startAnimating];
    // 서버로 이메일/비번 전송
}

- (void)loginSucceed {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    WageViewController *wvc = [storyboard instantiateViewControllerWithIdentifier:@"WageViewController"];
    [self.activityIndicator stopAnimating];
    [self saveUserDefaults];
    [self presentViewController:wvc animated:YES completion:nil];
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
    } else {
        if ([self validateEmail:self.emailTextField.text]) {
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

- (IBAction)autoLoginChanged:(id)sender {
    if ([self.autoLoginSwitch isOn])
        self.autoLogin = YES;
    else
        self.autoLogin = NO;
}

- (void)loadUserDefaults {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    self.autoLogin = [defaults objectForKey:@"autoLogin"];
    self.email = [defaults objectForKey:@"email"];
}

- (void)saveUserDefaults {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setBool:self.autoLogin forKey:@"autoLogin"];
    [defaults setObject:self.email forKey:@"email"];
    [defaults synchronize];
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

#pragma mark - UITextField Delegate Methods

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
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
