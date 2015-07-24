package at.account;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.account.interfaces.IAccountBiz;
import at.com.CommUtil;
import at.model.ResultEty;
import at.model.UserEty;
import at.supp.CC;

@RestController
public class AccountCont {
	private static final Logger lgr = LoggerFactory.getLogger(AccountCont.class);

	@Autowired
	private IAccountBiz accountBiz;

	public void setAccountBiz(IAccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	@RequestMapping(value = CC.API_ACCOUNT, produces = "application/json", method = RequestMethod.POST)
	public String registerUserCont(@Valid UserEty userEty, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_ACCOUNT_REGISTER_FAIL));
		}
		lgr.debug(userEty.toString());

		int registerResult;
		try {
			registerResult = accountBiz.registerUserBiz(userEty);
		} catch (DuplicateKeyException e) {
			lgr.debug("IntegrityConstraintViolationException: " + e.getMessage());
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_ACCOUNT_REGISTER_FAIL));
		}
		lgr.debug("registerResult: " + registerResult);
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(registerResult));
	}

	@RequestMapping(value = CC.API_ACCOUNT, produces = "application/json", method = RequestMethod.GET)
	public String checkEmailExistanceCont(@Valid UserEty user, BindingResult result) {
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_ACCOUNT_EMAILCHECK_FAIL));
		}
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		lgr.debug("email: " + user.getEmail());

		int emailCount = accountBiz.getEmailCountBiz(user);

		lgr.debug("emailCount: " + emailCount);
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(emailCount));
	}
}
