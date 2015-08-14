package at.module.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.com.CC;
import at.module.token.interfaces.ITokenBiz;

@RestController
public class TokenCont {
	@Autowired
	ITokenBiz tokenBiz;

	@RequestMapping(value = CC.API_TOKEN, method = RequestMethod.GET)
	public String retrieveToken(@CookieValue(CC.USER_ID_IN_COOKIE) long userId) {
		return null;
	}

	@RequestMapping(value = CC.API_TOKEN, method = RequestMethod.DELETE)
	public String expireJwTokens(@CookieValue(CC.USER_ID_IN_COOKIE) long userId,
			@CookieValue("USER_TOKEN_SEQ_IN_COOKIE") long jwTokenSeq) {
		return null;
	}
}
