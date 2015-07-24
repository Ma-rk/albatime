package at.account.interfaces;

import org.springframework.dao.DuplicateKeyException;

import at.model.UserEty;

public interface IAccountBiz {
	int registerUserBiz(UserEty user) throws DuplicateKeyException;

	int getEmailCountBiz(UserEty user);
}
