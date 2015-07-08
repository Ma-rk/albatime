package at.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import at.model.TokenKeyEty;
import at.model.UserEty;
import at.supp.CC;
import at.supp.interfaces.ISqlService;
import at.user.interfaces.IUserDao;

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
		lgr.debug(CC.GETTING_OUT_6 + "checkUserExistance");
		return this.jdbcTemplate.queryForObject(this.sqls.getSql("accountLogin"), rowMapper, userEmail, userPw);
	}

	public int insertJwTokenKey(TokenKeyEty tokenKeyEty) {
		lgr.debug(CC.GETTING_INTO_6 + "insertJwTokenKey");
		int insertJwTokenResult = this.jdbcTemplate.update(this.sqls.getSql("login_InsertJwTokenKey"), tokenKeyEty.getUserId(),
				tokenKeyEty.getKey(), tokenKeyEty.getStus());
		lgr.debug(CC.GETTING_OUT_6 + "insertJwTokenKey");
		return insertJwTokenResult;
	}

	public String retrieveJwTokenKey(long tkSeq, long userId) {
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
		String jwTokenKey = this.jdbcTemplate.queryForObject(this.sqls.getSql("autologin_retrieveJwTokenKey"),
				rowMapper, tkSeq, userId);
		lgr.debug(CC.GETTING_OUT_6 + "retrieveJwTokenKey");
		return jwTokenKey;
	}
}
