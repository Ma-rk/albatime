package at.account.interfaces;

import javax.sql.DataSource;

public interface IAccountDao {
	void setDataSource(DataSource dataSource);
}
