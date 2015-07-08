package at.user.interfaces;

import javax.sql.DataSource;

import at.model.TokenKeyEty;
import at.model.UserEty;

public interface IUserDao {
	void setDataSource(DataSource dataSource);

	public UserEty getUserInfoByEmailAndPw(String userEmail, String userPw);
	
	int insertJwTokenKey(TokenKeyEty tokenKeyEty);

	String retrieveJwTokenKey(long tkSeqUsr, long userId);
}
