package at.account.interfaces;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;

import at.model.UserEty;

public interface IAccountDao {
	void setDataSource(DataSource dataSource);

	int registerUserDao(UserEty user) throws DuplicateKeyException;

	int getEmailCountDao(UserEty user);
}
