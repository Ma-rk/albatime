package at.module.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import at.com.CC;
import at.model.UserEty;
import at.module.account.interfaces.IAccountBiz;

public class AccountBizTx implements IAccountBiz {
	private static final Logger lgr = LoggerFactory.getLogger(AccountBizTx.class);
	/*
	 * DI codes
	 */
	private IAccountBiz accountBiz;

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
	public int getEmailCountBiz(UserEty user) {
		return this.accountBiz.getEmailCountBiz(user);
	}

	public int registerUserBiz(UserEty user) throws DuplicateKeyException {
		return this.accountBiz.registerUserBiz(user);
	}

	public UserEty login(UserEty user) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());

		UserEty loginUser;

		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		lgr.info("transaction info: begin on [{}]", this.toString());
		try {
			///////////////////////////////////////
			loginUser = this.accountBiz.login(user);
			///////////////////////////////////////
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

}
