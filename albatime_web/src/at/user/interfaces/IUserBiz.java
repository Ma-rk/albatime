package at.user.interfaces;

import java.util.List;
import java.util.Map;

import at.model.UserEty;

public interface IUserBiz {
	UserEty login(UserEty user);

	String retrieveJwTokenKey(long tkSeqUsr, long userId);
	
	List<Map<String, Object>> retrieveJwTokenList(long userId);

	int expireJwTokens(long userId);
}
