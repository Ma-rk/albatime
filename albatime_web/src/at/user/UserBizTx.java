package at.user;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.model.UserEty;
import at.supp.CC;
import at.user.interfaces.IUserBiz;

public class UserBizTx implements IUserBiz {
	private static final Logger lgr = LoggerFactory.getLogger(UserBizTx.class);

	/*
	 * DI codes
	 */
	IUserBiz userBiz;

	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

	PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionMansger) {
		this.transactionManager = transactionMansger;
	}

	/*
	 * functional methods
	 */
	public UserEty login(UserEty user) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());

		UserEty loginUser;

		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		lgr.info("transaction info: begin on [{}]", this.toString());
		try {
			/////////////////////////////////////
			loginUser = this.userBiz.login(user);
			/////////////////////////////////////
			this.transactionManager.commit(status);
			lgr.info("transaction info: commit done.");
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			lgr.info("transaction info: rollback done.");
			throw e;
		} finally {
			lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName() + "tx");
		}
		return loginUser;
	}

	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		return this.userBiz.retrieveJwTokenKey(tokenKeyEty);
	}

	public List<Map<String, Object>> retrieveJwTokenList(TokenEty tokenEty) {
		return this.userBiz.retrieveJwTokenList(tokenEty);
	}

	public int expireJwTokens(TokenEty tokenEty) {
		return userBiz.expireJwTokens(tokenEty);
	}
}
