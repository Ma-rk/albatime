package at.module.token.interfaces;

import java.util.List;
import java.util.Map;

import at.model.TokenEty;
import at.model.TokenKeyEty;

public interface ITokenBiz {
	String retrieveJwTokenKey(TokenKeyEty tokenKeyEty);

	List<Map<String, Object>> retrieveJwTokenList(TokenEty tokenEty);

	int expireJwTokens(TokenEty tokenEty);
}