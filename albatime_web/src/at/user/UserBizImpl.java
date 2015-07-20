package at.user;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.com.interfaces.IComDao;
import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.model.UserEty;
import at.supp.CC;
import at.supp.JwtMgr;
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

	private IComDao comDao;

	public void setComDao(IComDao comDao) {
		this.comDao = comDao;
	}

	/*
	 * functional methods
	 */
	public UserEty login(UserEty user) {
		lgr.debug(CC.GETTING_INTO_4 + "loginImpl");

		UserEty userInfo = this.userDao.getUserInfoByEmailAndPw(user);
		if (userInfo == null)
			return null;

		String jwTokenKey = JwtMgr.generateJwTokenKey();

		String jwToken = JwtMgr.createJsonWebToken(userInfo.getId(), CC.DEFAULT_SESSION_DURATION_DAYS, jwTokenKey);
		userInfo.setCurrentJwToken(jwToken);

		int insertJwTokenResult = this.userDao.insertJwTokenKey(new TokenKeyEty(userInfo.getId(), jwTokenKey));
		if (insertJwTokenResult == 1) {
			long jwTokenKeySeq = comDao.getLastInsertId();
			userInfo.setUserJwTokenKeySeq(jwTokenKeySeq);
		}
		lgr.debug(CC.GETTING_OUT_4 + "loginImpl");
		return userInfo;
	}

	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		return this.userDao.retrieveJwTokenKey(tokenKeyEty);
	}

	public List<Map<String, Object>> retrieveJwTokenList(TokenEty tokenEty) {
		tokenEty.setAsNormalStus();
		return this.userDao.retrieveJwTokenList(tokenEty);
	}

	public int expireJwTokens(TokenEty tokenEty) {
		tokenEty.setAsExpiredStus();
		return userDao.expireJwTokens(tokenEty);
	}

	/*
	 * supporting methods
	 */
}
