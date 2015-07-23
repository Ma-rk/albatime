package at.user.interfaces;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.model.UserEty;

public interface IUserDao {
	void setDataSource(DataSource dataSource);

	public UserEty getUserInfoByEmailAndPw(UserEty user);

	int insertJwTokenKey(TokenKeyEty tokenKeyEty);

	String retrieveJwTokenKey(TokenKeyEty tokenKeyEty);

	List<Map<String, Object>> retrieveJwTokenList(TokenEty tokenEty);

	int expireJwTokens(TokenEty tokenEty);
}
