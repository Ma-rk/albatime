package at.account.interfaces;

import java.util.List;
import java.util.Map;

import at.model.UserEty;

public interface IAccountBiz {

	List<Map<String, Object>> login(UserEty user);
}