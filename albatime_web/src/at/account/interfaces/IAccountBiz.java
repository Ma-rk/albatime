package at.account.interfaces;

import at.model.UserEty;

public interface IAccountBiz {
	int registerUserBiz(UserEty user);

	int getEmailCountBiz(UserEty user);
}
