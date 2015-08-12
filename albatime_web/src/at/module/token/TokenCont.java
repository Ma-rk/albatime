package at.module.token;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.com.CC;
import at.model.ResultEty;
import at.model.TokenEty;
import at.module.token.interfaces.ITokenBiz;

@RestController
public class TokenCont {
	private static final Logger lgr = LoggerFactory.getLogger(TokenCont.class);
	@Autowired
	ITokenBiz tokenBiz;

	@RequestMapping(value = CC.API_TOKEN, method = RequestMethod.GET)
	public String retrieveToken(@CookieValue(CC.USER_ID_IN_COOKIE) long userId) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		lgr.debug("retrieving tokens for user " + userId);
		TokenEty tokenEty = new TokenEty(userId);

		List<Map<String, Object>> jwTokenList = tokenBiz.retrieveJwTokenList(tokenEty);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(jwTokenList));
	}

	@RequestMapping(value = CC.API_TOKEN, method = RequestMethod.DELETE)
	public String expireJwTokens(@CookieValue(CC.USER_ID_IN_COOKIE) long userId,
			@CookieValue("USER_TOKEN_SEQ_IN_COOKIE") long jwTokenSeq) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		lgr.debug("expiring tokens for user " + userId);
		TokenEty tokenEty = new TokenEty(userId, jwTokenSeq);

		int expireTokenResult = tokenBiz.expireJwTokens(tokenEty);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(expireTokenResult));
	}
}
