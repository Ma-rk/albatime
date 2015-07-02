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
@property (weak, nonatomic) IBOutlet UILabel *statusLabel;
@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UIButton *searchButton;
@property (weak, nonatomic) IBOutlet UIButton *sendButton;
@property (weak, nonatomic) IBOutlet UIButton *cancelButton;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *activityIndicator;

@property (strong, nonatomic) NetworkHandler *networkHandler;

@end

@implementation PswdFindViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.emailTextField.delegate = self;
    self.activityIndicator.hidden = YES;
    self.networkHandler = [NetworkHandler new];
}

- (IBAction)searchButtonTapped:(id)sender {
    // 서버에 이메일 보내서 있는지 확인
}

- (IBAction)sendButtonTapped:(id)sender {
    // 해당 이메일에 비번 재설정 요청메일 보내도록 서버에 요청
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
