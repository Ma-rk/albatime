package at.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.com.CC;
import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.model.UserEty;
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
	public int insertJwTokenKey(TokenKeyEty tokenKeyEty) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int insertJwTokenResult = this.jdbcTemplate.update(this.sqls.getSql("login_InsertJwTokenKey"),
				tokenKeyEty.getUserId(), tokenKeyEty.getKey(), tokenKeyEty.getStus());
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return insertJwTokenResult;
	}

	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
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
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return jwTokenKey;
	}

	public List<Map<String, Object>> retrieveJwTokenList(TokenEty tokenEty) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		List<Map<String, Object>> jwTokenList = jdbcTemplate.queryForList(this.sqls.getSql("tkRetireveToken"),
				tokenEty.getUserId(), tokenEty.getStus());
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return jwTokenList;
	}

	public int expireJwTokens(TokenEty tokenEty) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int expireJwTokenResult = jdbcTemplate.update(this.sqls.getSql("tkExpireToken"), tokenEty.getStus(),
				tokenEty.getUserId(), tokenEty.getJwTokenKeySeq(), tokenEty.getStus());
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return expireJwTokenResult;
	}
}
