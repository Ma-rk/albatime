package at.module.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import at.com.CC;
import at.com.interfaces.IComDao;
import at.model.TokenKeyEty;
import at.model.UserEty;
import at.module.account.interfaces.IAccountBiz;
import at.module.account.interfaces.IAccountDao;
import at.module.token.interfaces.ITokenDao;
import at.supp.JwtMgr;

public class AccountBizImpl implements IAccountBiz {
	private static final Logger lgr = LoggerFactory.getLogger(AccountBizImpl.class);

	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ITokenDao jwTokenDao;
	@Autowired
	private IComDao comDao;

	public int getEmailCountBiz(UserEty user) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		int emailCount = this.accountDao.getEmailCountDao(user);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return emailCount;
	}

	public void registerUserBiz(UserEty user) throws DuplicateKeyException {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		user.setAsNormalStus();
		this.accountDao.registerUserDao(user);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
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

		TokenKeyEty k = new TokenKeyEty(userInfo.getId(), jwTokenKey);
		k.setAsNormalStus();
		int insertJwTokenResult = this.jwTokenDao.insertJwTokenKey(k);
		if (insertJwTokenResult == 1) {
			userInfo.setUserJwTokenKeySeq(comDao.getLastInsertId());
		}
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return userInfo;
	}
}
