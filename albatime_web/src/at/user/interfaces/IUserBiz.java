package at.user.interfaces;

import java.util.List;
import java.util.Map;

import at.model.UserEty;

public interface IUserBiz {

	void add(UserEty user);

	List<Map<String, Object>> login(UserEty user);

	void upgradeLevelOfEveryUser();
}
