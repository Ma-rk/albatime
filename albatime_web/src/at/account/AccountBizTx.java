package at.account;

import org.springframework.transaction.PlatformTransactionManager;

import at.account.interfaces.IAccountBiz;
import at.model.UserEty;

public class AccountBizTx implements IAccountBiz {
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
	public int registerUserBiz(UserEty user) {
		return this.accountBiz.registerUserBiz(user);
	}

	public int getEmailCountBiz(String email) {
		return this.accountBiz.getEmailCountBiz(email);
	}
}
