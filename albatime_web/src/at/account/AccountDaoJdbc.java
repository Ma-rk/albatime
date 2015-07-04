package at.account;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.account.interfaces.IAccountDao;
import at.model.UserEty;
import at.supp.CC;
import at.supp.interfaces.ISqlService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class AccountDaoJdbc implements IAccountDao {
	private static final Logger lgr = LoggerFactory.getLogger(AccountDaoJdbc.class);
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
}
