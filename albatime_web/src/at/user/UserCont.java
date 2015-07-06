package at.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.model.UserEty;
import at.supp.CC;
import at.user.interfaces.IUserBiz;

//import at.supp.RsaJwkSupplier;

@Controller
public class UserCont {
	private static final Logger lgr = LoggerFactory.getLogger(UserCont.class);
	@Autowired
	IUserBiz userBiz;

	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

	@RequestMapping(value = "/api/login", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody String login(HttpServletResponse response, @RequestParam("email") String email,
			@RequestParam("pw") String pw) {
		lgr.debug(CC.GETTING_INTO_2 + "login");
		lgr.debug("email: " + email);
		lgr.debug("pw: " + pw);

		UserEty user = userBiz.login(new UserEty(email, pw));

		Cookie[] cookies = { new Cookie(CC.JWT_TOKEN, user.getCurrentJwToken()),
				new Cookie(CC.USER_ID_IN_COOKIE, String.valueOf(user.getId())),
				new Cookie(CC.USER_TOKEN_SEQ_IN_COOKIE, String.valueOf(user.getUserJwTokenKeySeq())) };

		for (Cookie cookie : cookies) {
			cookie.setPath("/");
			response.addCookie(cookie);
		}

		lgr.debug(CC.GETTING_OUT_2 + "login");
		return CC.gson.toJson(user);
	}

	@RequestMapping(value = "/api/login2", method = RequestMethod.GET)
	public @ResponseBody UserEty interceptorTest() {
		lgr.debug(CC.GETTING_INTO_2 + "interceptorTest");
		lgr.debug(CC.GETTING_OUT_2 + "interceptorTest");
		return null;
	}

	@RequestMapping(value = "/api/login3", method = RequestMethod.GET)
	public @ResponseBody UserEty interceptorTest2() {
		lgr.debug(CC.GETTING_INTO_2 + "interceptorTest");
		lgr.debug(CC.GETTING_OUT_2 + "interceptorTest");
		return null;
	}
}
