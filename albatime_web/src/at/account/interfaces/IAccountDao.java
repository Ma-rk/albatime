package at.account.interfaces;

import javax.sql.DataSource;

import at.model.UserEty;

public interface IAccountDao {
	void setDataSource(DataSource dataSource);
	
	int registerUserDao(UserEty user);
}
