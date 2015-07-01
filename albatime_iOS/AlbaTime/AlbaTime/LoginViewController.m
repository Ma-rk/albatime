//
//  LoginViewController.m
//  AlbaTime
//
//  Created by VincentGigandet on 2015. 7. 1..
//  Copyright © 2015년 Vincent Gigandet. All rights reserved.
//

#import "LoginViewController.h"

@interface LoginViewController ()

@property BOOL autoLogin; // NSUserDefault에서 세팅값 받아옴
@property (nonatomic, strong) NSString *email;
@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UITextField *pswdTextField;
@property (weak, nonatomic) IBOutlet UISwitch *autoLoginSwitch;
@property (weak, nonatomic) NSString *placeHolderTextForEmail;
@property (weak, nonatomic) NSString *placeHolderTextForPswd;
@property (weak, nonatomic) IBOutlet UIButton *loginButton;

- (IBAction)loginButtonTapped:(id)sender;
- (IBAction)autoLoginChanged:(id)sender;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    /*
     로그인 뷰 위에 인디케이터 표시
     
     리처빌리티 체크 후 예스면 진행 / 연결불가면 '인터넷에 연결할 수 없습니다. 네트워크 연결을 확인해주세요' + 재시도 버튼 표시
     
     if(autoLogin)
     user default에서 id 불러옴 / 키체인에서 비번 불러옴. 이것들 서버전송 후 인증
     인증 성공시 바로 돈계산 뷰 띄움
     인증 실패시 로그인 뷰 띄움
     
     키체인에 비번 없을 경우 로그인뷰 띄움
     
     else
     로그인뷰 띄우고 빈칸에 id 채움(있을 경우) / 비번은 비워둠
     
     id는 최근에 사용한 값(username or email)으로 채워넣어야 함
     
     로그아웃 누르면 로그인 뷰로 넘어가고 키체인에 비번 지움
     */
    
    [self loadUserDefaults];
    [self setPlaceHolderText];
    
    if (self.email) {
        self.emailTextField.text = self.email;
        
        if (self.autoLogin) {
            /* 키체인에서 비번 불러서 비번 있으면
             1. 서버에 이메일/비번 보내고 인증 진행
             2. 로그인중 인디케이터 띄움(인디케이터 띄워져 있으면 화면터치불가)
             3. 비번 자리에 점으로 8개 정도 띄워놓음
             
             response 받아서 성공이면 페이지 전환
             실패하면
             1. 알람 띄워서 로그인 실패 알림 - OK 누르면 아무것도 안함
             
             키체인에 비번 없으면 아무것도 안함
             */
        }
    }
}

- (void)setPlaceHolderText {
    
    // 나중에 지역 언어로 변경
    self.placeHolderTextForEmail = @"E-main address";
    self.placeHolderTextForPswd = @"Password";
    
    self.emailTextField.placeholder = self.placeHolderTextForEmail;
    self.pswdTextField.placeholder = self.placeHolderTextForPswd;
}

- (IBAction)loginButtonTapped:(id)sender {

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

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
