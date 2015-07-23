package at.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.com.CommUtil;
import at.model.ResultEty;
import at.model.TokenEty;
import at.model.UserEty;
import at.supp.CC;
import at.user.interfaces.IUserBiz;

@RestController
public class UserCont {
	private static final Logger lgr = LoggerFactory.getLogger(UserCont.class);
	@Autowired
	IUserBiz userBiz;

	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

	@RequestMapping(value = CC.API_LOGIN, produces = "application/json", method = RequestMethod.POST)
	public String login(HttpServletResponse response, @Valid UserEty user, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_USER_LOGIN_FAIL));
		}
		lgr.debug(user.toString());

		ResultEty resultEty;
		UserEty userInfo = userBiz.login(user);

		if (userInfo != null) {
			Cookie[] cookies = { new Cookie(CC.JWT_TOKEN, userInfo.getCurrentJwToken()),
					new Cookie(CC.USER_ID_IN_COOKIE, String.valueOf(userInfo.getId())),
					new Cookie(CC.USER_TOKEN_SEQ_IN_COOKIE, String.valueOf(userInfo.getUserJwTokenKeySeq())) };
			for (Cookie cookie : cookies) {
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			resultEty = new ResultEty(userInfo);
		} else {
			resultEty = new ResultEty(false, CC.ERROR_USER_LOGIN_FAIL);
		}
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(resultEty);
	}

	@RequestMapping(value = CC.API_TOKEN, method = RequestMethod.GET)
	public String retrieveToken(@CookieValue("USER_ID_IN_COOKIE") long userId) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		lgr.debug("retrieving tokens for user " + userId);
		TokenEty tokenEty = new TokenEty(userId);

		List<Map<String, Object>> jwTokenList = userBiz.retrieveJwTokenList(tokenEty);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(jwTokenList));
	}

	@RequestMapping(value = CC.API_TOKEN, method = RequestMethod.DELETE)
	public String expireJwTokens(@CookieValue("USER_ID_IN_COOKIE") long userId,
			@CookieValue("USER_TOKEN_SEQ_IN_COOKIE") long jwTokenSeq) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		lgr.debug("expiring tokens for user " + userId);
		TokenEty tokenEty = new TokenEty(userId, jwTokenSeq);

		int expireTokenResult = userBiz.expireJwTokens(tokenEty);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(expireTokenResult));
	}
}
