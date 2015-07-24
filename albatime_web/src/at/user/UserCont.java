package at.user;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.model.ResultEty;
import at.model.TokenEty;
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
