//
//  WageViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 2..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "WageViewController.h"
#import "NetworkHandler.h"
#import "CalcModel.h"

@interface WageViewController () <CalcModelDelegate, NetworkHandlerDelegate>

@property (weak, nonatomic) IBOutlet UIImageView *arrowImageView;
@property (strong, nonatomic) CalcModel *calcModel;

@end

@implementation WageViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.calcModel = [CalcModel new];
    self.calcModel.delegate = self;
    self.calcModel.networkHandler.delegate = self;
    
    [self setViewElements];
}

- (void)setViewElements {
    [self.arrowImageView setUserInteractionEnabled:YES];
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self
                                                                          action:@selector(gotoNextView)];
    [tap setNumberOfTapsRequired:1];
    [self.arrowImageView addGestureRecognizer:tap];
}

- (void)gotoNextView {
    [self performSegueWithIdentifier:@"ScheduleViewSegue"
                              sender:self];
}

- (void)viewWillAppear:(BOOL)animated {
    self.navigationController.navigationBar.hidden = YES;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - calcModel Delegate Methods

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

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
