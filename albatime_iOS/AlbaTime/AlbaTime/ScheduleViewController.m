//
//  ScheduleViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 25..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "ScheduleViewController.h"
#import "CalcModel.h"

@interface ScheduleViewController () <CalcModelDelegate>

@property (weak, nonatomic) IBOutlet UIImageView *arrowImageView;
@property (weak, nonatomic) IBOutlet UITableView *timeCardTableView;
@property (strong, nonatomic) CalcModel *calcModel;

@end

@implementation ScheduleViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.calcModel = [CalcModel new];
    self.calcModel.delegate = self;
    
    [self setViewElements];
}

- (void)setViewElements {
    [self.arrowImageView setUserInteractionEnabled:YES];
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self
                                                                          action:@selector(gotoPreviousView)];
    [tap setNumberOfTapsRequired:1];
    [self.arrowImageView addGestureRecognizer:tap];
}

- (void)gotoPreviousView {
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)viewWillAppear:(BOOL)animated {
    self.navigationController.navigationBar.hidden = YES;
}


- (IBAction)logoutButtonTapped:(id)sender {
    NSString *title = @"Log out";
    NSString *message = @"You sure??";
    UIAlertController *alertController = [UIAlertController
                                          alertControllerWithTitle:title
                                          message:message
                                          preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *okAction = [UIAlertAction
                               actionWithTitle:NSLocalizedString(@"LogOut", @"LogOut action")
                               style:UIAlertActionStyleDefault
                               handler:^(UIAlertAction *action)
                               {
                                   [self.calcModel.defaults setBool:NO forKey:@"autoLogin"];
                                   [self.navigationController popToRootViewControllerAnimated:YES];
                               }];
    UIAlertAction *cancelAction = [UIAlertAction
                                   actionWithTitle:NSLocalizedString(@"Cancel", @"Cancel action")
                                   style:UIAlertActionStyleDefault
                                   handler:^(UIAlertAction *action)
                                   {
                                       NSLog(@"logout canceled");
                                   }];
    [alertController addAction:okAction];
    [alertController addAction:cancelAction];
    [self presentViewController:alertController animated:YES completion:nil];
}


- (IBAction)deleteAccButtonTapped:(id)sender {
    NSString *title = @"Delete Account";
    NSString *message = @"You sure??";
    UIAlertController *alertController = [UIAlertController
                                          alertControllerWithTitle:title
                                          message:message
                                          preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *okAction = [UIAlertAction
                               actionWithTitle:NSLocalizedString(@"Delete", @"Delete action")
                               style:UIAlertActionStyleDefault
                               handler:^(UIAlertAction *action)
                               {
                                   // 여기서 DB 데이터 지우자
                                   // 계정을 지웠는데 루트뷰에서 이메일이 자동으로 채워져 있는 문제 해결
                                   // remove all userInfo
                                   [self.calcModel removePassword];
                                   [self.calcModel removeUserDefaults];
                                   [self.navigationController popToRootViewControllerAnimated:YES];
                               }];
    UIAlertAction *cancelAction = [UIAlertAction
                                   actionWithTitle:NSLocalizedString(@"Cancel", @"Cancel action")
                                   style:UIAlertActionStyleDefault
                                   handler:^(UIAlertAction *action)
                                   {
                                       NSLog(@"Delete account canceled");
                                   }];
    [alertController addAction:okAction];
    [alertController addAction:cancelAction];
    [self presentViewController:alertController animated:YES completion:nil];
}


- (IBAction)timeCardButtonTapped:(id)sender {
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - CalcModel delegate methods
- (void)removePswdSucceed {
    NSLog(@"Removing password success");
}

- (void)removePswdFailedWithError:(NSString *)error {
    NSString *title = @"Removing password failed";
    [self showAlertViewTitle:title withMessage:error];
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

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
