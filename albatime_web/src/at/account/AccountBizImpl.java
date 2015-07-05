package at.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.account.interfaces.IAccountBiz;
import at.account.interfaces.IAccountDao;
import at.model.UserEty;
import at.supp.CC;

public class AccountBizImpl implements IAccountBiz {
	private static final Logger lgr = LoggerFactory.getLogger(AccountBizImpl.class);

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
	public int registerUserBiz(UserEty user) {
		lgr.debug(CC.GETTING_INTO_4 + "registerUserBiz");
		user.setAsNormalStus();
		int registerUserResult = this.accountDao.registerUserDao(user);
		lgr.debug(CC.GETTING_OUT_4 + "registerUserBiz");
		return registerUserResult;
	}

	/*
	 * supporting methods
	 */

}
