package at.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.model.TokenKeyEty;
import at.model.UserEty;
import at.supp.CC;
import at.supp.interfaces.ISqlService;
import at.user.interfaces.IUserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
	public UserEty getUserInfoByEmailAndPw(UserEty user) {
		lgr.debug(CC.GETTING_INTO_6 + "getUserInfoByEmailAndPw");
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
		UserEty useResult = null;
		try {
			useResult = this.jdbcTemplate.queryForObject(this.sqls.getSql("accountLogin"), rowMapper, user.getEmail(),
					user.getPw());
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		lgr.debug("user: " + user);
		lgr.debug(CC.GETTING_OUT_6 + "getUserInfoByEmailAndPw");
		return useResult;
	}

	public int insertJwTokenKey(TokenKeyEty tokenKeyEty) {
		lgr.debug(CC.GETTING_INTO_6 + "insertJwTokenKey");
		int insertJwTokenResult = this.jdbcTemplate.update(this.sqls.getSql("login_InsertJwTokenKey"),
				tokenKeyEty.getUserId(), tokenKeyEty.getKey(), tokenKeyEty.getStus());
		lgr.debug(CC.GETTING_OUT_6 + "insertJwTokenKey");
		return insertJwTokenResult;
	}

	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		lgr.debug(CC.GETTING_INTO_6 + "retrieveJwTokenKey");
		lgr.debug("retireive token key for tkSeq [{}], userId [{}]", tokenKeyEty.getSeq(), tokenKeyEty.getSeqUser());
		RowMapper<String> rowMapper = new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) {
				try {
					return rs.getString("tk_key");
				} catch (SQLException e) {
					throw new BeanInstantiationException(UserEty.class, e.getMessage(), e);
				}
			}
		};
		String jwTokenKey;
		try {
			jwTokenKey = this.jdbcTemplate.queryForObject(this.sqls.getSql("autologin_retrieveJwTokenKey"), rowMapper,
					tokenKeyEty.getSeq(), tokenKeyEty.getSeqUser(), CC.TOKEN_STUS_NORMAL);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			jwTokenKey = null;
		}
		lgr.debug("jwTokenKey: " + jwTokenKey);
		lgr.debug(CC.GETTING_OUT_6 + "retrieveJwTokenKey");
		return jwTokenKey;
	}

	public List<Map<String, Object>> retrieveJwTokenList(long userId) {
		lgr.debug(CC.GETTING_INTO_6 + "retrieveTokenList");
		lgr.debug(CC.GETTING_OUT_6 + "retrieveTokenList");
		return jdbcTemplate.queryForList(this.sqls.getSql("tkRetireveToken"), userId);
	}

	public int expireJwTokens(long userId) {
		lgr.debug(CC.GETTING_INTO_6 + "deleteJwTokens");
		lgr.debug(CC.GETTING_OUT_6 + "deleteJwTokens");
		return jdbcTemplate.update(this.sqls.getSql("tkExpireToken"), userId);
	}
}
