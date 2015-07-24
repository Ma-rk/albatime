package at.account.interfaces;

import org.springframework.dao.DuplicateKeyException;

import at.model.UserEty;

public interface IAccountBiz {
	int getEmailCountBiz(UserEty user);

	int registerUserBiz(UserEty user) throws DuplicateKeyException;

	UserEty login(UserEty user);
}
