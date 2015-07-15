package at.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.account.interfaces.IAccountBiz;
import at.model.UserEty;
import at.supp.CC;

@Controller
public class AccountCont {
	private static final Logger lgr = LoggerFactory.getLogger(AccountCont.class);

	@Autowired
	IAccountBiz accountBiz;

	public void setAccountBiz(IAccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	@RequestMapping(value = CC.API_ACCOUNT, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody String registerUserCont(@RequestParam("email") String email, @RequestParam("pw") String pw,
			@RequestParam("nick") String nick, @RequestParam("gender") String gender,
			@RequestParam("birth") String birth, @RequestParam("type") String type) {
		lgr.debug(CC.GETTING_INTO_2 + "registerUserCont");

		lgr.debug("email: " + email);
		lgr.debug("pw: " + pw);
		lgr.debug("nick: " + nick);
		lgr.debug("gender: " + gender);
		lgr.debug("birth: " + birth);
		lgr.debug("type: " + type);

		UserEty userEty = new UserEty(email, pw, nick, gender, birth, type);
		int registerResult = accountBiz.registerUserBiz(userEty);

		lgr.debug("registerResult: " + registerResult);
		lgr.debug(CC.GETTING_OUT_2 + "registerUserCont");

		return CC.gson.toJson(Integer.toString(registerResult));
	}

	@RequestMapping(value = CC.API_ACCOUNT, produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody String checkEmailExistanceCont(@RequestParam("email") String email) {
		lgr.debug(CC.GETTING_INTO_2 + "checkEmailExistanceCont");

		lgr.debug("email: " + email);

		int emailCount = accountBiz.getEmailCountBiz(email);

		lgr.debug("emailCount: " + emailCount);
		lgr.debug(CC.GETTING_OUT_2 + "checkEmailExistanceCont");

		return CC.gson.toJson(Integer.toString(emailCount));
	}
}
