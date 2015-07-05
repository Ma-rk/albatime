package at.user;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import at.model.UserEty;
import at.supp.CC;
import at.user.interfaces.IUserBiz;

public class UserBizTx implements IUserBiz {
	private static final Logger logger = LoggerFactory.getLogger(UserBizTx.class);

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
	public void add(UserEty user) {
		this.userBiz.add(user);
	}
	public UserEty login(UserEty user) {
		logger.info(CC.GETTING_INTO_4+"loginTx");
		
		UserEty loginUser;
		
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		logger.info("transaction info: begin on [{}]", this.toString());
		try {
			///////////////////////////////////////
			loginUser = this.userBiz.login(user);
			///////////////////////////////////////
			this.transactionManager.commit(status);
			logger.info("transaction info: commit done.");
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			logger.info("transaction info: rollback done.");
			throw e;
		} finally {
			logger.info(CC.GETTING_OUT_4+"loginTX");
		}
		return loginUser;
	}

	public String retrieveJwTokenKey(long tkSeqUsr, long userId){
		return this.userBiz.retrieveJwTokenKey(tkSeqUsr, userId);
	}
	
	public void upgradeLevelOfEveryUser() {
		logger.info(CC.GETTING_INTO_4+"upgradeLevelOfEveryUser()");
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		logger.info("transaction info: begin on [{}]", this.toString());
		try {
			this.userBiz.upgradeLevelOfEveryUser();
			this.transactionManager.commit(status);
			logger.info("transaction info: commit done.");
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			logger.info("transaction info: rollback done.");
			throw e;
		} finally {
			logger.info(CC.GETTING_OUT_4+"upgradeLevelOfEveryUser()");
		}
	}
}
