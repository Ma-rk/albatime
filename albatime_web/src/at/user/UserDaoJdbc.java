package at.user;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.account.AccountDaoJdbc;
import at.model.UserEty;
import at.supp.CC;
import at.supp.interfaces.ISqlService;
import at.user.interfaces.IUserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoJdbc implements IUserDao {
	private static final Logger lgr = LoggerFactory.getLogger(UserDaoJdbc.class);
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
	public void add(UserEty user) {
		this.jdbcTemplate.update(
				"insert into tb_usr(usr_email, usr_pw, usr_nick, usr_birth, usr_stus) values (?,?,?,?,?)",
				user.getEmail(), user.getPw(), user.getNick(), user.getBirth(), user.getStus());
	}
	public List<Map<String, Object>> checkUserExistance(UserEty user) {
		lgr.debug(CC.GETTING_OUT_6 + "====>login");
		return this.jdbcTemplate.queryForList(this.sqls.getSql("accountLogin"), user.getEmail(), user.getPw());
	}
}
