package at.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.account.AccountBizImpl;
import at.com.interfaces.IComDao;
import at.model.UserEty;
import at.supp.CC;
import at.supp.JwtMgr;
import at.user.interfaces.IUserBiz;
import at.user.interfaces.IUserDao;

public class UserBizImpl implements IUserBiz {
	private static final Logger lgr = LoggerFactory.getLogger(UserBizImpl.class);

	public static final int MIN_LOGCOUT_FOR_SILVER = 50;
	public static final int MIN_RECCOMMEND_FOR_GOLD = 30;

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
	public void add(UserEty user) {

		this.userDao.add(user);
	}

	public void upgradeLevelOfEveryUser() {
		lgr.info("upgradeLevelOfEveryUser()====>");

		lgr.info("upgradeLevelOfEveryUser()<====");
	}

	public UserEty login(UserEty user) {
		lgr.debug(CC.GETTING_INTO_4 + "loginImpl");

		UserEty userInfo = this.userDao.getUserInfoByEmailAndPw(user.getEmail(), user.getPw());
		if (userInfo == null)
			return null;

		String jwTokenKey = JwtMgr.generateJwTokenKey();

		String jwToken = JwtMgr.createJsonWebToken(userInfo.getId(), CC.DEFAULT_SESSION_DURATION_DAYS, jwTokenKey);

		int usertJwTokenResult = this.userDao.insertJwTokenKey(userInfo.getId(), jwTokenKey);

		long jwTokenKeySeq = comDao.getLastInsertId();

		userInfo.setCurrentJwToken(jwToken);
		userInfo.setUserTkSeq(jwTokenKeySeq);
		lgr.debug(CC.GETTING_OUT_4 + "loginImpl");
		return userInfo;
	}

	public String retrieveJwTokenKey(long tkSeqUsr, long userId){
		return this.userDao.retrieveJwTokenKey(tkSeqUsr, userId);
	}
	/*
	 * supporting methods
	 */
	private boolean isQualifiedToUpgradeUserLevel(UserEty user) {

		return false;
	}

	private void sendUpgradeEmail(UserEty user) {

	}

	protected void upgradeLevelOfOneUser(UserEty user) {
		lgr.info("upgradeLevelOfOneUser(UserEntity)======>");

		lgr.info("upgradeLevelOfOneUser(UserEntity)<======");
	}
}
