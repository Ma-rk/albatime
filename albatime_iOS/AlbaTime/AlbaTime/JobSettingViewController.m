//
//  JobSettingViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 23..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "JobSettingViewController.h"
#import "NetworkHandler.h"
#import "AppDelegate.h"
#import "Definitions.h"

@interface JobSettingViewController () <UIPickerViewDelegate, UIPickerViewDataSource, UITextFieldDelegate>

@property (weak, nonatomic) IBOutlet UITextField *jobTitleTextField;
@property (weak, nonatomic) IBOutlet UITextField *timeUnitTextField;
@property (weak, nonatomic) IBOutlet UITextField *taxTextField;
@property (weak, nonatomic) IBOutlet UIButton *jobColorButton;
@property (weak, nonatomic) IBOutlet UITextField *alarmMinsTextField;
@property (weak, nonatomic) IBOutlet UITextField *DefaultWageTextField;
@property (strong, nonatomic) NetworkHandler *networkHandler;
@property (weak, nonatomic) IBOutlet UIPickerView *timeUnitPickerView;
@property (weak, nonatomic) IBOutlet UIPickerView *alarmPickerView;
@property (strong, nonatomic) NSArray *timeUnits;
@property NSInteger startNum;

@end

@implementation JobSettingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.networkHandler = [(AppDelegate *)[[UIApplication sharedApplication] delegate] networkHandler];
    
    
    self.timeUnits = @[@1, @5, @10, @15, @20, @30, @60];
    
    self.startNum = 1;
    
}

- (void)setViewElements {
    
    self.timeUnitTextField.inputView = self.timeUnitPickerView;
    self.alarmMinsTextField.inputView = self.alarmPickerView;
    
    // set delegates
    self.alarmPickerView.delegate = self;
    self.alarmPickerView.dataSource = self;
    self.timeUnitPickerView.delegate = self;
    self.timeUnitPickerView.dataSource = self;
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
- (IBAction)doneButtonTapped:(id)sender {
}

#pragma mark - UITextField Delegate methods

-(BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}


#pragma mark - UIPickerView delegate methods

-(NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView{
    return 1;
}

-(NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component{
    if (pickerView.tag == TIME_UNIT_PICKERVIEW_TAG) {
        return self.timeUnits.count;
    }
    else if (pickerView.tag == ALARM_PICKERVIEW_TAG) {
        return 120;
    }
    return 0;
}

-(NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component{
    if (pickerView.tag == TIME_UNIT_PICKERVIEW_TAG) {
        return [[self.timeUnits objectAtIndex:row] stringValue];
    }
    else if (pickerView.tag == ALARM_PICKERVIEW_TAG) {
        NSInteger mins = self.startNum + row - 1;
        return [NSString stringWithFormat:@"%ld", mins];
    }
    return @"UIPickerViewError";
}

-(void)pickerView:(nonnull UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
    if (pickerView.tag == TIME_UNIT_PICKERVIEW_TAG) {
        self.timeUnitTextField.text = [self.timeUnits[row] stringValue];
    }
    else if (pickerView.tag == ALARM_PICKERVIEW_TAG) {
        NSInteger mins = self.startNum + row - 1;
        self.alarmMinsTextField.text = [NSString stringWithFormat:@"%ld", mins];
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
