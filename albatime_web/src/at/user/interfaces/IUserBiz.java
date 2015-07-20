package at.user.interfaces;

import java.util.List;
import java.util.Map;

import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.model.UserEty;

public interface IUserBiz {
	UserEty login(UserEty user);

	String retrieveJwTokenKey(TokenKeyEty tokenKeyEty);

	List<Map<String, Object>> retrieveJwTokenList(TokenEty tokenEty);

	int expireJwTokens(long userId);
}
