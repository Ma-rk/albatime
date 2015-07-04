package at.account;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import at.account.interfaces.IAccountBiz;
import at.model.UserEty;
import at.user.interfaces.IUserBiz;

public class AccountBizTx implements IAccountBiz {
	private static final Logger lgr = LoggerFactory.getLogger(AccountBizTx.class);

	/*
	 * DI codes
	 */
	IAccountBiz accountBiz;

	public void setAccountBiz(IAccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionMansger) {
		this.transactionManager = transactionMansger;
	}

	/*
	 * functional methods
	 */
	public List<Map<String, Object>> login(UserEty user) {
		return this.accountBiz.login(user);
	}

	public void upgradeLevelOfEveryUser() {
		lgr.info("upgradeLevelOfEveryUser()==>");
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		lgr.info("transaction info: begin on [{}]", this.toString());
		try {
//			this.accountBiz.upgradeLevelOfEveryUser();
			this.transactionManager.commit(status);
			lgr.info("transaction info: commit");
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			lgr.info("transaction info: rollback");
			throw e;
		} finally {
			lgr.info("upgradeLevelOfEveryUser()<==");
		}
	}
}