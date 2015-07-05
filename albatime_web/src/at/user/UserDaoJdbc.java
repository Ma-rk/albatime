package at.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.account.AccountDaoJdbc;
import at.model.UserEty;
import at.supp.CC;
import at.supp.interfaces.ISqlService;
import at.user.interfaces.IUserDao;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

	public UserEty getUserInfoByEmailAndPw(String userEmail, String userPw) {
		lgr.debug(CC.GETTING_INTO_6 + "checkUserExistance");

		RowMapper<UserEty> rowMapper = new RowMapper<UserEty>() {
			public UserEty mapRow(ResultSet rs, int rowNum) {
				try {
					return new UserEty(rs.getLong("usr_id"), rs.getString("usr_email"), rs.getString("usr_nick"),
							rs.getString("usr_gender"), rs.getString("usr_birth"), rs.getString("usr_type"),
							rs.getString("usr_stus"));
				} catch (SQLException e) {
					throw new BeanInstantiationException(UserEty.class, e.getMessage(), e);
				}
			}
		};
		return this.jdbcTemplate.queryForObject(this.sqls.getSql("accountLogin"), rowMapper, userEmail, userPw);
	}

	public int insertJwTokenKey(Long userId, String jwTokenKey) {
		lgr.debug(CC.GETTING_INTO_6 + "insertJwTokenKey");
		int usertJwTokenResult = this.jdbcTemplate.update(this.sqls.getSql("login_InsertJwTokenKey"), userId, jwTokenKey);
		lgr.debug(CC.GETTING_OUT_6 + "insertJwTokenKey");
		return usertJwTokenResult;
	}
	
	public String retrieveJwTokenKey(long tkSeq, long userId){
		lgr.debug(CC.GETTING_INTO_6 + "retrieveJwTokenKey");
		
		RowMapper<String> rowMapper = new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) {
				try {
					return rs.getString("tk_key");
				} catch (SQLException e) {
					throw new BeanInstantiationException(UserEty.class, e.getMessage(), e);
				}
			}
		};
		
		String jwTokenKey = this.jdbcTemplate.queryForObject(this.sqls.getSql("autologin_retrieveJwTokenKey"), rowMapper, tkSeq, userId);
		lgr.debug(CC.GETTING_OUT_6 + "retrieveJwTokenKey");
		return jwTokenKey;
	}
}
