package at.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import at.account.interfaces.IAccountDao;
import at.com.CC;
import at.model.UserEty;
import at.supp.interfaces.ISqlService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
	public int getEmailCountDao(UserEty user) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int emailCount = this.jdbcTemplate.queryForObject(this.sqls.getSql("accountEmailCount"),
				new Object[] { user.getEmail() }, Integer.class);
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), emailCount);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return emailCount;
	}

	public int registerUserDao(UserEty user) throws DuplicateKeyException {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int registerUserResult = this.jdbcTemplate.update(this.sqls.getSql("accountRegisterAccount"), user.getEmail(),
				user.getPw(), user.getNick(), user.getGender(), user.getBirth(), user.getType(), user.getStus());
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), registerUserResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return registerUserResult;
	}

	public UserEty getUserInfoByEmailAndPw(UserEty user) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		RowMapper<UserEty> rowMapper = new RowMapper<UserEty>() {
			public UserEty mapRow(ResultSet rs, int rowNum) {
				try {
					return new UserEty(rs.getLong("usr_id"), rs.getString("usr_email"), rs.getString("usr_nick"),
							rs.getString("usr_gender"), rs.getDate("usr_birth"), rs.getString("usr_type"),
							rs.getString("usr_stus"), rs.getString("usr_signup_date"));
				} catch (SQLException e) {
					throw new BeanInstantiationException(UserEty.class, e.getMessage(), e);
				}
			}
		};
		UserEty useResult = null;
		try {
			useResult = this.jdbcTemplate.queryForObject(this.sqls.getSql("accountLogin"), rowMapper, user.getEmail(),
					user.getPw(), user.getStus());
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		lgr.debug("user: " + user);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return useResult;
	}

	public int cleanTbAccountDao() {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int cleanTbAccountResult = this.jdbcTemplate.update(this.sqls.getSql("accountCleanTbUser"));
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), cleanTbAccountResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return cleanTbAccountResult;
	}
}
