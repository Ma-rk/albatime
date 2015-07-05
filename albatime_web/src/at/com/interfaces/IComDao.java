package at.com.interfaces;

import javax.sql.DataSource;

import at.model.UserEty;

public interface IComDao {
	void setDataSource(DataSource dataSource);
	
	long getLastInsertId();
}
