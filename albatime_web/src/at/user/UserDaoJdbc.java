package at.user;

import javax.sql.DataSource;

import at.model.UserEty;
import at.user.interfaces.IUserDao;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoJdbc implements IUserDao {
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
	public void add(UserEty user) {
		this.jdbcTemplate.update(
				"insert into tb_usr(usr_email, usr_pw, usr_nick, usr_birth, usr_stus) values (?,?,?,?,?)",
				user.getEmail(), user.getPw(), user.getNick(), user.getBirth(), user.getStus());
	}
}
