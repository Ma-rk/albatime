package at.com;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import at.com.interfaces.IComDao;
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
	public long getLastInsertId() {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		long lastInsertId = this.jdbcTemplate.queryForObject(this.sqls.getSql("getLastId"), Long.class);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return lastInsertId;
	}
}
