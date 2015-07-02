package at.dao.interfaces;

import javax.sql.DataSource;

import at.model.UserEty;

public interface IUserDao {
	void setDataSource(DataSource dataSource);
	public void add(UserEty user);
}