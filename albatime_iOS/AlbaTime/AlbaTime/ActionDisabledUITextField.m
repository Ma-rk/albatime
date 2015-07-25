//
//  PasteDisabledUITextField.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 25..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "ActionDisabledUITextField.h"

@implementation ActionDisabledUITextField

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/


// Disable long-press action to protect from unexpected invalid user input
- (BOOL)canPerformAction:(SEL)action withSender:(id)sender
{
    return false;
}

@end
