package at.module.account;

import java.util.Map;

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

import at.com.CC;
import at.com.CommUtil;
import at.model.UserEty;
import at.module.account.interfaces.IAccountBiz;
import at.supp.ResultFac;

@RestController
@RequestMapping(value = CC.API_ACCOUNT)
public class AccountCont {
	private static final Logger lgr = LoggerFactory.getLogger(AccountCont.class);

	@Autowired
	private IAccountBiz accountBiz;

	@RequestMapping(method = RequestMethod.GET)
	public String checkEmailExistanceCont(@Valid UserEty user, BindingResult result) {
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_ACCOUNT_EMAILCHECK_FAIL));
		}
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		lgr.debug("email: " + user.getEmail());

		int emailCount = accountBiz.getEmailCountBiz(user);

		lgr.debug("emailCount: " + emailCount);
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(ResultFac.rf(emailCount));
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registerUserCont(@Valid UserEty userEty, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_ACCOUNT_REGISTER_FAIL));
		}
		lgr.debug(userEty.toString());

		try {
			accountBiz.registerUserBiz(userEty);
		} catch (DuplicateKeyException e) {
			lgr.debug("IntegrityConstraintViolationException: " + e.getMessage());
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_ACCOUNT_REGISTER_FAIL));
		}
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(ResultFac.rf());
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String login(HttpServletResponse response, @Valid UserEty user, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_ACCOUNT_LOGIN_FAIL));
		}
		lgr.debug(user.toString());

		Map<String, Object> resultObje;
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
			resultObje = ResultFac.rf(userInfo);
		} else {
			resultObje = ResultFac.rf(false, CC.ERROR_ACCOUNT_LOGIN_FAIL);
		}
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(resultObje);
	}
}
