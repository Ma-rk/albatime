package at.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import at.account.interfaces.IAccountBiz;
import at.account.interfaces.IAccountDao;
import at.com.interfaces.IComDao;
import at.model.TokenKeyEty;
import at.model.UserEty;
import at.supp.CC;
import at.supp.JwtMgr;
import at.user.interfaces.IUserDao;

public class AccountBizImpl implements IAccountBiz {
	private static final Logger lgr = LoggerFactory.getLogger(AccountBizImpl.class);

	/*
	 * DI codes
	 */
	private IAccountDao accountDao;

	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	private IUserDao jwTokenDao;

	public void setUserDao(IUserDao jwTokenDao) {
		this.jwTokenDao = jwTokenDao;
	}

	private IComDao comDao;

	public void setComDao(IComDao comDao) {
		this.comDao = comDao;
	}

	/*
	 * functional methods
	 */
	public int getEmailCountBiz(UserEty user) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		int emailCount = this.accountDao.getEmailCountDao(user);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return emailCount;
	}

	public int registerUserBiz(UserEty user) throws DuplicateKeyException {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		user.setAsNormalStus();
		int registerUserResult = this.accountDao.registerUserDao(user);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return registerUserResult;
	}

	public UserEty login(UserEty user) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());

		user.setAsNormalStus();
		UserEty userInfo = this.accountDao.getUserInfoByEmailAndPw(user);
		if (userInfo == null)
			return null;

		String jwTokenKey = JwtMgr.generateJwTokenKey();

		String jwToken = JwtMgr.createJsonWebToken(userInfo.getId(), CC.SESSION_DURATION_DAYS_FOR_JWT, jwTokenKey);
		userInfo.setCurrentJwToken(jwToken);

		int insertJwTokenResult = this.jwTokenDao.insertJwTokenKey(new TokenKeyEty(userInfo.getId(), jwTokenKey));
		if (insertJwTokenResult == 1) {
			userInfo.setUserJwTokenKeySeq(comDao.getLastInsertId());
		}
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return userInfo;
	}
}
