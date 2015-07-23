package at.account;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.account.interfaces.IAccountBiz;
import at.com.CommUtil;
import at.model.UserEty;
import at.supp.CC;

@RestController
public class AccountCont {
	private static final Logger lgr = LoggerFactory.getLogger(AccountCont.class);

	@Autowired
	IAccountBiz accountBiz;

	public void setAccountBiz(IAccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	@RequestMapping(value = CC.API_ACCOUNT, produces = "application/json", method = RequestMethod.POST)
	public String registerUserCont(@Valid UserEty userEty, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return "0";
		}
		lgr.debug(userEty.toString());

		int registerResult = accountBiz.registerUserBiz(userEty);

		lgr.debug("registerResult: " + registerResult);
		lgr.debug(CC.GETTING_OUT_2 + "registerUserCont");
		return CC.gson.toJson(Integer.toString(registerResult));
	}

	@RequestMapping(value = CC.API_ACCOUNT, produces = "application/json", method = RequestMethod.GET)
	public String checkEmailExistanceCont(@RequestParam("email") String email) {
		lgr.debug(CC.GETTING_INTO_2 + "checkEmailExistanceCont");
		lgr.debug("email: " + email);

		int emailCount = accountBiz.getEmailCountBiz(email);

		lgr.debug("emailCount: " + emailCount);
		lgr.debug(CC.GETTING_OUT_2 + "checkEmailExistanceCont");
		return CC.gson.toJson(Integer.toString(emailCount));
	}
}
