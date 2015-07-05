package at.user.interfaces;

import java.util.List;
import java.util.Map;

import at.model.UserEty;

public interface IUserBiz {

	void add(UserEty user);

	UserEty login(UserEty user);

	void upgradeLevelOfEveryUser();
}
