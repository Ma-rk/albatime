package at.account;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.account.interfaces.IAccountDao;
import at.model.UserEty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import account.AccountTest;

public class AccountDaoJdbc implements IAccountDao {
	private static final Logger lgr = LoggerFactory.getLogger(AccountDaoJdbc.class);
	/*
	 * DI codes
	 */
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/*
	 * functional methods
	 */
	public List<Map<String, Object>> login(UserEty user) {
		lgr.debug("====>login");
		return this.jdbcTemplate.queryForList(
				"select usr_id, usr_email, usr_nick, usr_gender, usr_birth, usr_stus from tb_usr where usr_email = ? and usr_pw = ?",
				user.getEmail(), user.getPw());
	}
}