package at.account;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.account.interfaces.IAccountBiz;
import at.account.interfaces.IAccountDao;
import at.model.UserEty;
import at.supp.CC;

public class AccountBizImpl implements IAccountBiz {
	private static final Logger lgr = LoggerFactory.getLogger(AccountBizImpl.class);

	public static final int MIN_LOGCOUT_FOR_SILVER = 50;
	public static final int MIN_RECCOMMEND_FOR_GOLD = 30;

	/*
	 * DI codes
	 */
	private IAccountDao accountDao;

	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	/*
	 * functional methods
	 */

	/*
	 * supporting methods
	 */

}
