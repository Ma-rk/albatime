package at.module.account.interfaces;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;

import at.model.UserEty;

public interface IAccountDao {
	void setDataSource(DataSource dataSource);

	int getEmailCountDao(UserEty user);

	void registerUserDao(UserEty user) throws DuplicateKeyException;

	UserEty getUserInfoByEmailAndPw(UserEty user);

	int cleanTbAccountDao();
}
