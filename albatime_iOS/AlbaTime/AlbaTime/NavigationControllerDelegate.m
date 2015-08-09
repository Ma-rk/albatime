//
//  NavigationControllerDelegate.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 8. 9..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "NavigationControllerDelegate.h"
#import "TransitionAnimator.h"

@interface NavigationControllerDelegate ()

@property (strong, nonatomic) UINavigationController *navigationController;
@property (strong, nonatomic) TransitionAnimator *animator;
@property (strong, nonatomic) UIPercentDrivenInteractiveTransition * interactionController;

@end

@implementation NavigationControllerDelegate

- (void)awakeFromNib
{

    UIPanGestureRecognizer* panRecognizer = [[UIPanGestureRecognizer alloc] initWithTarget:self action:@selector(pan:)];
    [self.navigationController.view addGestureRecognizer:panRecognizer];
    
    self.animator = [TransitionAnimator new];
}

- (void)pan:(UIPanGestureRecognizer*)recognizer
{
    UIView* view = self.navigationController.view;
    if (recognizer.state == UIGestureRecognizerStateBegan) {
        CGPoint location = [recognizer locationInView:view];
        if (location.x <  CGRectGetMidX(view.bounds) && self.navigationController.viewControllers.count > 1) { // left half
            self.interactionController = [UIPercentDrivenInteractiveTransition new];
            [self.navigationController popViewControllerAnimated:YES];
        }
    } else if (recognizer.state == UIGestureRecognizerStateChanged) {
        CGPoint translation = [recognizer translationInView:view];
        CGFloat d = fabs(translation.x / CGRectGetWidth(view.bounds));
        [self.interactionController updateInteractiveTransition:d];
    } else if (recognizer.state == UIGestureRecognizerStateEnded) {
        if ([recognizer velocityInView:view].x > 0) {
            [self.interactionController finishInteractiveTransition];
        } else {
            [self.interactionController cancelInteractiveTransition];
        }
        self.interactionController = nil;
    }
}

- (id<UIViewControllerAnimatedTransitioning>)navigationController:(UINavigationController *)navigationController animationControllerForOperation:(UINavigationControllerOperation)operation fromViewController:(UIViewController *)fromVC toViewController:(UIViewController *)toVC
{
    if (operation == UINavigationControllerOperationPop) {
        return self.animator;
    }
    return nil;
}

- (id<UIViewControllerInteractiveTransitioning>)navigationController:(UINavigationController *)navigationController interactionControllerForAnimationController:(id<UIViewControllerAnimatedTransitioning>)animationController
{
    return self.interactionController;
}

@end
