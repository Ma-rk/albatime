package at.user.interfaces;

import at.model.UserEty;

public interface IUserBiz {
	UserEty login(UserEty user);

	String retrieveJwTokenKey(long tkSeqUsr, long userId);
}
