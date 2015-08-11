package at.com.interfaces;

import javax.sql.DataSource;

import at.model.VisitLogEty;

public interface IComDao {
	void setDataSource(DataSource dataSource);

	long getLastInsertId();

	void insertVisitLog(VisitLogEty visitLogEty);

	int cleanTbAccountDao();

	int cleanTbActorDao();

	int cleanTbCardDao();

	int cleanTbScheDao();
}
