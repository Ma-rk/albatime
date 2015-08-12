package at.module.token;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.module.token.interfaces.ITokenBiz;

public class TokenBizTx implements ITokenBiz {

	@Autowired
	ITokenBiz userBiz;
	@Autowired
	PlatformTransactionManager transactionManager;

	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		return this.userBiz.retrieveJwTokenKey(tokenKeyEty);
	}

	public List<Map<String, Object>> retrieveJwTokenList(TokenEty tokenEty) {
		return this.userBiz.retrieveJwTokenList(tokenEty);
	}

	public int expireJwTokens(TokenEty tokenEty) {
		return userBiz.expireJwTokens(tokenEty);
	}
}
