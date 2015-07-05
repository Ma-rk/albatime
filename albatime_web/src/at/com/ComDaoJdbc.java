package at.com;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import at.com.interfaces.IComDao;
import at.supp.CC;
import at.supp.interfaces.ISqlService;

public class ComDaoJdbc implements IComDao {
	private static final Logger lgr = LoggerFactory.getLogger(ComDaoJdbc.class);
	/*
	 * DI codes
	 */
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private ISqlService sqls;

	public void setSqls(ISqlService sqls) {
		this.sqls = sqls;
	}

	/*
	 * functional methods
	 */
	@SuppressWarnings("deprecation")
	public long getLastInsertId() {
		lgr.debug(CC.GETTING_INTO_6 + "getLastInsertId");
		long lastInsertId = this.jdbcTemplate.queryForLong(this.sqls.getSql("getLastId"));
		lgr.debug(CC.GETTING_OUT_6 + "getLastInsertId");
		return lastInsertId;
	}
}
