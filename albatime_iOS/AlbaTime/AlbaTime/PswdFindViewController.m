//
//  PswdFindViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 2..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "PswdFindViewController.h"
#import "NetworkHandler.h"

@interface PswdFindViewController () <UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UILabel *checkEmailResult;
@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UIButton *sendButton;
@property (weak, nonatomic) IBOutlet UIButton *cancelButton;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *activityIndicator;

@property (strong, nonatomic) NetworkHandler *networkHandler;
@property (weak, nonatomic) NSString *placeHolderTextForEmail;

@end

@implementation PswdFindViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setTextFieldOption];
    self.activityIndicator.hidden = YES;
    self.checkEmailResult.hidden = YES;
    self.networkHandler = [NetworkHandler new];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(showSendResult:)
                                                 name:@"resetRequestSent"
                                               object:nil];
}

- (void)setTextFieldOption {
    self.placeHolderTextForEmail = @"E-main address";
    self.emailTextField.placeholder = self.placeHolderTextForEmail;
    self.emailTextField.delegate = self;
    [self.emailTextField setReturnKeyType:UIReturnKeyDone];
}

- (void)showSendResult:(NSNotification *)notification {
    NSInteger result = [[notification.userInfo objectForKey:@"result"] integerValue];
    
    if (result == 0) {
        self.checkEmailResult.text = @"E-mail has been sent successfully";
        self.checkEmailResult.textColor = [UIColor blueColor];
        self.checkEmailResult.hidden = NO;
        self.activityIndicator.hidesWhenStopped = YES;
        [self.activityIndicator stopAnimating];
        [self.view setUserInteractionEnabled:YES];
    }
    else if (result == 1) {
        self.checkEmailResult.text = @"Sending e-amil failed";
        self.checkEmailResult.hidden = NO;
        self.activityIndicator.hidesWhenStopped = YES;
        [self.activityIndicator stopAnimating];
        [self.view setUserInteractionEnabled:YES];
    }
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

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [self.emailTextField endEditing:YES];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - UITextField Delegate Methods

- (void)textFieldDidBeginEditing:(nonnull UITextField *)textField {
        self.checkEmailResult.hidden = YES;
}

- (void)textFieldDidEndEditing:(nonnull UITextField *)textField {
    if (textField.text.length > 0) {
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
            self.checkEmailResult.text = @"This e-mail NOT found";
            self.checkEmailResult.hidden = NO;
        }
        else {
            self.checkEmailResult.text = @"E-mail found\nWould you send a reset request to this e-mail?";
            self.checkEmailResult.textColor = [UIColor blueColor];
            self.checkEmailResult.hidden = NO;
        }
    }
    else {
        self.checkEmailResult.text = @"Invalid e-mail, please check again";
        self.checkEmailResult.hidden = NO;
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
