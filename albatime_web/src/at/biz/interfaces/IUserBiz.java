package at.biz.interfaces;

import at.model.UserEty;

public interface IUserBiz {

	void add(UserEty user);

	void upgradeLevelOfEveryUser();
}