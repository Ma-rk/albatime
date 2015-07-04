package at.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.model.UserEty;
import at.user.interfaces.IUserBiz;
import at.user.interfaces.IUserDao;

public class UserBizImpl implements IUserBiz {
	private static final Logger logger = LoggerFactory.getLogger(UserBizImpl.class);

	public static final int MIN_LOGCOUT_FOR_SILVER = 50;
	public static final int MIN_RECCOMMEND_FOR_GOLD = 30;

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
	public void add(UserEty user) {

		this.userDao.add(user);
	}

	public void upgradeLevelOfEveryUser() {
		logger.info("upgradeLevelOfEveryUser()====>");

		logger.info("upgradeLevelOfEveryUser()<====");
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
		logger.info("upgradeLevelOfOneUser(UserEntity)======>");

		logger.info("upgradeLevelOfOneUser(UserEntity)<======");
	}
}
