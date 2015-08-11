package at.module.account.interfaces;

import org.springframework.dao.DuplicateKeyException;

import at.model.UserEty;

public interface IAccountBiz {
	int getEmailCountBiz(UserEty user);

	void registerUserBiz(UserEty user) throws DuplicateKeyException;

	UserEty login(UserEty user);
}
