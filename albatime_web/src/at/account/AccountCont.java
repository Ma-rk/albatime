package at.account;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.account.interfaces.IAccountBiz;
import at.com.CommUtil;
import at.model.ResultEty;
import at.model.UserEty;
import at.supp.CC;

@RestController
@RequestMapping(value = CC.API_ACCOUNT)
public class AccountCont {
	private static final Logger lgr = LoggerFactory.getLogger(AccountCont.class);

	@Autowired
	private IAccountBiz accountBiz;

	public void setAccountBiz(IAccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String checkEmailExistanceCont(@Valid UserEty user, BindingResult result) {
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_ACCOUNT_EMAILCHECK_FAIL));
		}
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		lgr.debug("email: " + user.getEmail());

		int emailCount = accountBiz.getEmailCountBiz(user);

		lgr.debug("emailCount: " + emailCount);
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(emailCount));
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registerUserCont(@Valid UserEty userEty, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_ACCOUNT_REGISTER_FAIL));
		}
		lgr.debug(userEty.toString());

		int registerResult;
		try {
			registerResult = accountBiz.registerUserBiz(userEty);
		} catch (DuplicateKeyException e) {
			lgr.debug("IntegrityConstraintViolationException: " + e.getMessage());
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_ACCOUNT_REGISTER_FAIL));
		}
		lgr.debug("registerResult: " + registerResult);
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(registerResult));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String login(HttpServletResponse response, @Valid UserEty user, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_USER_LOGIN_FAIL));
		}
		lgr.debug(user.toString());

		ResultEty resultEty;
		UserEty userInfo = accountBiz.login(user);

		if (userInfo != null) {
			Cookie[] cookies = { new Cookie(CC.JW_TOKEN, userInfo.getCurrentJwToken()),
					new Cookie(CC.USER_ID_IN_COOKIE, String.valueOf(userInfo.getId())),
					new Cookie(CC.USER_TOKEN_SEQ_IN_COOKIE, String.valueOf(userInfo.getUserJwTokenKeySeq())) };
			for (Cookie cookie : cookies) {
				cookie.setPath("/");
				cookie.setMaxAge(CC.SECONDS_FOR_COOKIE_DURATION);
				response.addCookie(cookie);
			}
			resultEty = new ResultEty(userInfo);
		} else {
			resultEty = new ResultEty(false, CC.ERROR_USER_LOGIN_FAIL);
		}
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(resultEty);
	}
}
