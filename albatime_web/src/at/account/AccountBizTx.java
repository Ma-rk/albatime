package at.account;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.PlatformTransactionManager;

import at.account.interfaces.IAccountBiz;
import at.model.UserEty;

public class AccountBizTx implements IAccountBiz {
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
	public int registerUserBiz(UserEty user) throws DuplicateKeyException {
		return this.accountBiz.registerUserBiz(user);
	}

	public int getEmailCountBiz(UserEty user) {
		return this.accountBiz.getEmailCountBiz(user);
	}
}
