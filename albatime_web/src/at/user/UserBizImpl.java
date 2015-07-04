package at.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.account.AccountBizImpl;
import at.model.UserEty;
import at.supp.CC;
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
	public List<Map<String, Object>> login(UserEty user) {
		lgr.debug(CC.GETTING_INTO_2 + "login");
		List<Map<String, Object>> userInfoMap = this.userDao.checkUserExistance(user);

		if (userInfoMap == null)
			return null;

		// Map<String, Object> aaa = new HashMap<String, Object>();
		// aaa.put("", value)

		userInfoMap.add(new HashMap<String, Object>());

		lgr.debug(CC.GETTING_OUT_2 + "login");
		return userInfoMap;
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
