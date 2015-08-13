package at.module.account.interfaces;

import org.springframework.dao.DuplicateKeyException;

import at.model.UserEty;

public interface IAccountDao {
	int getEmailCountDao(UserEty user);

	void registerUserDao(UserEty user) throws DuplicateKeyException;

	UserEty getUserInfoByEmailAndPw(UserEty user);
}
