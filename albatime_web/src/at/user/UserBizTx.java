package at.user;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import at.model.UserEty;
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
	public List<Map<String, Object>> login(UserEty user) {
		return this.userBiz.login(user);
	}

	public void upgradeLevelOfEveryUser() {
		logger.info("upgradeLevelOfEveryUser()==>");
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		logger.info("transaction info: begin on [{}]", this.toString());
		try {
			this.userBiz.upgradeLevelOfEveryUser();
			this.transactionManager.commit(status);
			logger.info("transaction info: commit");
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			logger.info("transaction info: rollback");
			throw e;
		} finally {
			logger.info("upgradeLevelOfEveryUser()<==");
		}
	}
}
