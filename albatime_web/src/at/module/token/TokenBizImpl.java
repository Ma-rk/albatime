package at.module.token;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.com.CC;
import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.module.token.interfaces.ITokenBiz;
import at.module.token.interfaces.ITokenDao;

public class TokenBizImpl implements ITokenBiz {
	private static final Logger lgr = LoggerFactory.getLogger(TokenBizImpl.class);

	@Autowired
	private ITokenDao tokenDao;

	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		tokenKeyEty.setAsNormalStus();
		String jwTokenKey = this.tokenDao.retrieveJwTokenKey(tokenKeyEty);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return jwTokenKey;
	}

	public List<Map<String, Object>> retrieveJwTokenList(TokenEty tokenEty) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		tokenEty.setAsNormalStus();
		List<Map<String, Object>> jwTokenList = this.tokenDao.retrieveJwTokenList(tokenEty);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return jwTokenList;
	}

	public int expireJwTokens(TokenEty tokenEty) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		tokenEty.setAsExpiredStus();
		int expireJwTokenResult = tokenDao.expireJwTokens(tokenEty);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return expireJwTokenResult;
	}
}
