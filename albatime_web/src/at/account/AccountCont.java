package at.account;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import account.AccountTest;
import at.account.interfaces.IAccountBiz;
import at.model.UserEty;

@Controller
public class AccountCont {
	private static final Logger lgr = LoggerFactory.getLogger(AccountCont.class);

	@Autowired
	IAccountBiz accountBiz;

	public void setAccountBiz(IAccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	@RequestMapping(value = "/api-login", method = RequestMethod.POST)
	public List<Map<String, Object>> login(@RequestParam("email") String email, @RequestParam("pw") String pw) {
		lgr.debug("==>login");
		lgr.debug("email: " + email);
		lgr.debug("pw: " + pw);

		List<Map<String, Object>> map = accountBiz.login(new UserEty(email, pw));

		lgr.debug("<==login");
		return map;
	}
}
