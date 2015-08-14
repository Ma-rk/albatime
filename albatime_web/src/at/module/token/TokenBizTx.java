package at.module.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

import at.model.TokenKeyEty;
import at.module.token.interfaces.ITokenBiz;

public class TokenBizTx implements ITokenBiz {

	@Autowired
	ITokenBiz tokenBizImpl;
	@Autowired
	PlatformTransactionManager transactionManager;

	public long insertJwTokenKey(TokenKeyEty tokenKeyEty) {
		return this.tokenBizImpl.insertJwTokenKey(tokenKeyEty);
	}


	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		return this.tokenBizImpl.retrieveJwTokenKey(tokenKeyEty);
	}}
