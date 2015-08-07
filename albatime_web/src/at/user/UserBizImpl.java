package at.user;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.com.CC;
import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.user.interfaces.IUserBiz;
import at.user.interfaces.IUserDao;

public class UserBizImpl implements IUserBiz {
	private static final Logger lgr = LoggerFactory.getLogger(UserBizImpl.class);

	/*
	 * DI codes
	 */
	private IUserDao userDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/*
	 * functional methods
	 */
	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		String jwTokenKey = this.userDao.retrieveJwTokenKey(tokenKeyEty);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return jwTokenKey;
	}

	public List<Map<String, Object>> retrieveJwTokenList(TokenEty tokenEty) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		tokenEty.setAsNormalStus();
		List<Map<String, Object>> jwTokenList = this.userDao.retrieveJwTokenList(tokenEty);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return jwTokenList;
	}

	public int expireJwTokens(TokenEty tokenEty) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		tokenEty.setAsExpiredStus();
		int expireJwTokenResult = userDao.expireJwTokens(tokenEty);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return expireJwTokenResult;
	}
}
