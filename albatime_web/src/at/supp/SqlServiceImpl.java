package at.supp;

import java.util.Map;

import at.custExc.SqlRetrivalFailuerException;
import at.supp.interfaces.ISqlService;

public class SqlServiceImpl implements ISqlService {
	private Map<String, String> sqlMap;

	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	public String getSql(String key) throws SqlRetrivalFailuerException {
		String sql = sqlMap.get(key);
		if (sql == null)
			throw new SqlRetrivalFailuerException("can not find a SQL for the key [" + key + "]");
		else
			return sql;
	}
}
