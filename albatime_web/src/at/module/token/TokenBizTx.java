package at.module.token;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;

import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.module.token.interfaces.ITokenBiz;

public class TokenBizTx implements ITokenBiz {

	/*
	 * DI codes
	 */
	ITokenBiz userBiz;

	public void setTokenBiz(ITokenBiz userBiz) {
		this.userBiz = userBiz;
	}

	PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionMansger) {
		this.transactionManager = transactionMansger;
	}

	/*
	 * functional methods
	 */
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
