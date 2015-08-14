package at.module.token;

import org.springframework.beans.factory.annotation.Autowired;

import at.model.TokenKeyEty;
import at.module.token.interfaces.ITokenBiz;
import at.module.token.interfaces.ITokenDao;

public class TokenBizImpl implements ITokenBiz {

	@Autowired
	private ITokenDao tokenDao;

	public long insertJwTokenKey(TokenKeyEty tokenKeyEty) {
		tokenKeyEty.setAsNormalStus();
		long newJwTokenKeySeq = this.tokenDao.insertJwTokenKey(tokenKeyEty);
		return newJwTokenKeySeq;
	}

	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		tokenKeyEty.setAsNormalStus();
		String jwTokenKey = this.tokenDao.retrieveJwTokenKey(tokenKeyEty);
		return jwTokenKey;
	}
}
