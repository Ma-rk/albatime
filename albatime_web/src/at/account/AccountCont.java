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
import at.supp.CC;

@Controller
public class AccountCont {
	private static final Logger lgr = LoggerFactory.getLogger(AccountCont.class);

	@Autowired
	IAccountBiz accountBiz;

	public void setAccountBiz(IAccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}


}
