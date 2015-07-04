package at.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.account.AccountCont;
import at.model.UserEty;
import at.supp.CC;
import at.user.interfaces.IUserBiz;

//import at.supp.RsaJwkSupplier;

@Controller
public class UserCont {
	private static final Logger lgr = LoggerFactory.getLogger(UserCont.class);
	// @Autowired
	// RsaJwkSupplier rsaJwkSupplier;
	//
	// public void setRsaJwkSupplier(RsaJwkSupplier rsaJwkSupplier) {
	// this.rsaJwkSupplier = rsaJwkSupplier;
	// }
	@Autowired
	IUserBiz userBiz;

	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

	@RequestMapping(value = "/api-login", method = RequestMethod.POST)
	public List<Map<String, Object>> login(@RequestParam("email") String email, @RequestParam("pw") String pw) {
		lgr.debug(CC.GETTING_INTO_2 + "login");
		lgr.debug("email: " + email);
		lgr.debug("pw: " + pw);

		List<Map<String, Object>> map = userBiz.login(new UserEty(email, pw));

		lgr.debug(CC.GETTING_OUT_2 + "login");
		return map;
	}
	@RequestMapping(value = "/view-user", method = RequestMethod.POST)
	public @ResponseBody String userRegister(
			@RequestParam(value = "userName", defaultValue = "default") String userName) {
		System.out.println("[" + userName + "] looked at me!!!");
		return userName;
	}

	@RequestMapping(value = "/user2", method = RequestMethod.POST)
	public @ResponseBody String userRegister2(HttpServletRequest request) {
		String guildName = (String) request.getAttribute("guildName");
		System.out.println("[" + guildName + "] He looked at me!!!");
		return "log in log in log in log in log in log in ";
	}

	@RequestMapping(value = "/api-lin", method = RequestMethod.POST)
	public UserEty test(@RequestParam("guildName") String guildName) {
		System.out.println("[" + guildName.toString() + "] He looked at me!!!");
		return new UserEty("aaaaaaaaa@a.net");
	}

	@RequestMapping(value = "/aaa", method = RequestMethod.GET)
	public @ResponseBody String test2(@CookieValue(value = "jwtoken", defaultValue = "nvl") String cooval) {
		System.out.println("He looked at me!!!/aaa");
		System.out.println("cooval: " + cooval);
		return "aaaaaaaaaaaaaaaaaaaaaaaaaaa\n\n";
	}

	@RequestMapping(value = "/ccc", method = RequestMethod.GET)
	public @ResponseBody String test3(HttpServletResponse response) {
		System.out.println("/ccc");
		response.addCookie(new Cookie("COOKIENAME", "The cookie's value"));
		response.addCookie(new Cookie("jwtoken",
				"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJZb3VyQ29tcGFueU9yQXBwTmFtZUhlcmUiLCJhdWQiOiJOb3RSZWFsbHlJbXBvcnRhbnQiLCJpYXQiOjE0MzU4OTkxNDYsImV4cCI6MTQzNjMzMTE0NiwiaW5mbyI6eyJ1c2VySWQiOiJhYWEiLCJ1c2VyQmlydGgiOiJhYWEiLCJ1c2VyR2VuZGVyIjoiYWFhIn19.6icXstD5e1_Q8wA4EnbpfrEvel03Csp4pzHEhahEeSk"));
		return "hello world\n\n";
	}

	@RequestMapping(value = "/ddd", method = RequestMethod.GET)
	public @ResponseBody String test4() {
		System.out.println("/ddd");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "Max-Age=3600");
		return "dddddddddddddddd\n\n";
	}

	@RequestMapping(value = "/iii", method = RequestMethod.GET)
	public @ResponseBody String test5() {
		System.out.println("/iii");
		this.userBiz.add(new UserEty("aa@a.net"));
		return "iiiiiiiiiiiiiiiiiiii\n\n";
	}

	@RequestMapping(value = "/ttt", method = RequestMethod.GET)
	public @ResponseBody String test6(@RequestParam("jwtoken") String jwt) {
		System.out.println("/ttt");
		return jwt;
	}
	
	
}
