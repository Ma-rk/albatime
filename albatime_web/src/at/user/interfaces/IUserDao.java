package at.user.interfaces;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.model.UserEty;

public interface IUserDao {
	void setDataSource(DataSource dataSource);

	public void add(UserEty user);

	public List<Map<String, Object>> checkUserExistance(UserEty user);
}
