package at.account.interfaces;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.model.UserEty;

public interface IAccountDao {
	void setDataSource(DataSource dataSource);

	public List<Map<String, Object>> checkUserExistance(UserEty user);
}
