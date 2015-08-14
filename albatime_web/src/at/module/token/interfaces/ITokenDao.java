package at.module.token.interfaces;

import at.model.TokenKeyEty;

public interface ITokenDao {
	long insertJwTokenKey(TokenKeyEty tokenKeyEty);

	String retrieveJwTokenKey(TokenKeyEty tokenKeyEty);
}
