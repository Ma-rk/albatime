//
//  JobSettingViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 23..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "JobSettingViewController.h"
#import "NetworkHandler.h"
#import "Definitions.h"
#import "CalcModel.h"


@interface JobSettingViewController () <UIPickerViewDelegate, UIPickerViewDataSource, UITextFieldDelegate, NetworkHandlerDelegate, CalcModelDelegate>

// Below 4 textFields are disabled long-press action by setting subclass ActionDisabledUITextField in IB
@property (weak, nonatomic) IBOutlet UITextField *timeUnitTextField;
@property (weak, nonatomic) IBOutlet UITextField *alarmMinsTextField;
@property (weak, nonatomic) IBOutlet UITextField *taxTextField;
@property (weak, nonatomic) IBOutlet UITextField *defaultWageTextField;

@property (weak, nonatomic) IBOutlet UITextField *jobTitleTextField;
@property (weak, nonatomic) IBOutlet UIButton *jobColorButton;
@property (weak, nonatomic) IBOutlet UIPickerView *timeUnitPickerView;
@property (weak, nonatomic) IBOutlet UIPickerView *alarmPickerView;
@property (weak, nonatomic) IBOutlet UIToolbar *timeUnitToolBar;
@property (weak, nonatomic) IBOutlet UIToolbar *alarmMinsToolBar;
@property (weak, nonatomic) IBOutlet UISwitch *alarmSwitch;
@property (weak, nonatomic) IBOutlet UISegmentedControl *hasUnpaidBTSwitch;

@property (strong, nonatomic) NSString *RGBColor;
@property (strong, nonatomic) NSArray *timeUnits;
@property (strong, nonatomic) NSArray *alarmMins;
@property (strong, nonatomic) CalcModel *calcModel;

@property BOOL isAlarmOn;

@end

@implementation JobSettingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.calcModel = [CalcModel new];
    self.calcModel.delegate = self;
    self.calcModel.networkHandler.delegate = self;
    
    // default time unit data counsource
    self.timeUnits = @[@1, @5, @10, @15, @20, @30, @60];
    self.alarmMins = @[@5, @10, @15, @20, @25, @30, @35, @40, @45, @50, @55, @60, @70, @80, @90, @100, @110, @120];
    
    [self setViewElements];
}

- (void)setViewElements {
    // iOS8 API change : should be removed from superView before added into the textField inputView, otherwise crashes!
    [self.timeUnitPickerView removeFromSuperview];
    [self.alarmPickerView removeFromSuperview];
    [self.timeUnitToolBar removeFromSuperview];
    [self.alarmMinsToolBar removeFromSuperview];
    
    // set tags
    self.timeUnitPickerView.tag = TIME_UNIT;
    self.alarmPickerView.tag = ALARM_MINS;
    
    [self.jobTitleTextField setReturnKeyType:UIReturnKeyDone];
    self.timeUnitTextField.inputView = self.timeUnitPickerView;
    self.timeUnitTextField.inputAccessoryView = self.timeUnitToolBar;
    self.alarmMinsTextField.inputView = self.alarmPickerView;
    self.alarmMinsTextField.inputAccessoryView = self.alarmMinsToolBar;
    
    // set delegates
    self.alarmPickerView.delegate = self;
    self.alarmPickerView.dataSource = self;
    self.timeUnitPickerView.delegate = self;
    self.timeUnitPickerView.dataSource = self;
    self.jobTitleTextField.delegate = self;
    
    // set number pad for some textFields
    self.taxTextField.keyboardType = UIKeyboardTypeDecimalPad;
    self.defaultWageTextField.keyboardType = UIKeyboardTypeDecimalPad;
    
    // set alarm switch state off by default
    [self.alarmSwitch setOn:NO];
    
    // set unpaid break time option NO
    self.hasUnpaidBTSwitch.selectedSegmentIndex = 1;

    // set default card color to White
    self.RGBColor = @"FFFFFF";
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)alarmSwitchChanged:(id)sender {
    if ([self.alarmSwitch isOn])
        self.isAlarmOn = YES;
    else
        self.isAlarmOn = NO;
}

- (IBAction)doneButtonTapped:(id)sender {
    if ([self validateUserInput]) {
        NSMutableDictionary *jobInfo = [NSMutableDictionary new];
        [jobInfo setObject:self.jobTitleTextField.text
                    forKey:@"name"];
        [jobInfo setObject:self.timeUnitTextField.text
                    forKey:@"workTimeUnit"];
        [jobInfo setObject:self.defaultWageTextField.text
                    forKey:@"defaultWage"];
        [jobInfo setObject:self.RGBColor
                    forKey:@"RGBColor"];
        if (self.taxTextField.text)
            [jobInfo setObject:self.taxTextField.text
                        forKey:@"taxRate"];
        if (self.isAlarmOn && self.alarmMinsTextField.text)
            [jobInfo setObject:self.alarmMinsTextField.text
                        forKey:@"alarmBefore"];
        if (self.hasUnpaidBTSwitch.selectedSegmentIndex == UNPIAD_BREAK_TIME_TRUE)
            [jobInfo setObject:@"y"
                        forKey:@"unpaidBreakFlag"];
        else if (self.hasUnpaidBTSwitch.selectedSegmentIndex == UNPIAD_BREAK_TIME_FALSE)
            [jobInfo setObject:@"n"
                        forKey:@"unpaidBreakFlag"];
        
        [self.calcModel.networkHandler uplaodNewJobInfo:jobInfo];
    }
}

- (BOOL)validateUserInput {
    if (self.jobTitleTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please enter your job name";
        [self showAlertViewTitle:title
                         message:message];
        return NO;
    } else if (self.timeUnitTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please enter your time unit";
        [self showAlertViewTitle:title
                         message:message];
        return NO;
    } else if (self.defaultWageTextField.text.length == 0) {
        NSString *title = @"Incomplete form";
        NSString *message = @"Please enter your default wage per hour";
        [self showAlertViewTitle:title
                         message:message];
        return NO;
    }
    
    // calcModeld에서 숫자 자릿수 양식 체크해야 함
    
    return YES;
}

- (IBAction)jobColorButtonTapped:(id)sender {
    NSString *title = @"Sorry";
    NSString *message = @"This function is not ready yet";
    [self showAlertViewTitle:title
                     message:message];
}

- (IBAction)wageItemNumChanged:(id)sender {
    NSString *title = @"Sorry";
    NSString *message = @"This function is not ready yet";
    [self showAlertViewTitle:title
                     message:message];
}

- (IBAction)timeUnitToolBarDoneTapped:(id)sender {
    NSInteger row = [self.timeUnitPickerView selectedRowInComponent:0];
    self.timeUnitTextField.text = [self.timeUnits[row] stringValue];
    [self.timeUnitTextField endEditing:YES];
}

- (IBAction)alarmToolBarDoneTapped:(id)sender {
    NSInteger row = [self.alarmPickerView selectedRowInComponent:0];
    self.alarmMinsTextField.text = [self.alarmMins[row] stringValue];
    [self.alarmMinsTextField endEditing:YES];
    [self.alarmSwitch setOn:YES
                   animated:YES];
    self.isAlarmOn = YES;
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [self.jobTitleTextField endEditing:YES];
    [self.timeUnitTextField endEditing:YES];
    [self.taxTextField endEditing:YES];
    [self.alarmMinsTextField endEditing:YES];
    [self.defaultWageTextField endEditing:YES];
}

#pragma mark - UITextField Delegate methods

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    return YES;
}

#pragma mark - UIPickerView delegate methods

-(NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView{
    return 1;
}

-(NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component{
    if (pickerView.tag == TIME_UNIT) {
        return self.timeUnits.count;
    }
    else if (pickerView.tag == ALARM_MINS) {
        return self.alarmMins.count;
    }
    return 0;
}

-(NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component{
    if (pickerView.tag == TIME_UNIT) {
        return [[self.timeUnits objectAtIndex:row] stringValue];
    }
    else if (pickerView.tag == ALARM_MINS) {
        return [[self.alarmMins objectAtIndex:row] stringValue];
    }
    return @"UIPickerViewError";
}

-(void)pickerView:(nonnull UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
    if (pickerView.tag == TIME_UNIT) {
        self.timeUnitTextField.text = [self.timeUnits[row] stringValue];
    }
    else if (pickerView.tag == ALARM_MINS) {
        self.alarmMinsTextField.text = [self.alarmMins[row] stringValue];
        [self.alarmSwitch setOn:YES
                       animated:YES];
        self.isAlarmOn = YES;
    }
}

#pragma mark - NetworkHandler Delegate Methods

- (void)newJobCreatedWithJobInfo:(NSDictionary *)jobInfo {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self performSegueWithIdentifier:@"FromJobSettingToWageViewSegue" sender:self];
    });
}

- (void)createJobFailedWithError:(NSString *)error {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSString *title = @"Creating a new job has failed";
        [self showAlertViewTitle:title
                         message:error];
    });
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
