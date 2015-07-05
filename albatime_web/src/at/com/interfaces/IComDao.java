package at.com.interfaces;

import javax.sql.DataSource;

public interface IComDao {
	void setDataSource(DataSource dataSource);

	long getLastInsertId();
}
