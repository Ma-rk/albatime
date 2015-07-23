//
//  JobSettingViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 23..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "JobSettingViewController.h"

@interface JobSettingViewController ()

@property (weak, nonatomic) IBOutlet UITextField *jobTitleTextField;
@property (weak, nonatomic) IBOutlet UITextField *timeUnitTextField;
@property (weak, nonatomic) IBOutlet UITextField *taxTextField;
@property (weak, nonatomic) IBOutlet UIButton *jobColorButton;
@property (weak, nonatomic) IBOutlet UITextField *alarmMinsTextField;
@property (weak, nonatomic) IBOutlet UITextField *DefaultWageTextField;


@end

@implementation JobSettingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)alarmButtonChanged:(id)sender {
}
- (IBAction)breakTimeValueChanged:(id)sender {
}
- (IBAction)wageSystemChanged:(id)sender {
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
