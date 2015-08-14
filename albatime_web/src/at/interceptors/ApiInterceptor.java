package at.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import at.com.CC;
import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.module.token.interfaces.ITokenBiz;
import at.supp.CookieMgr;
import at.supp.JwtMgr;

public class ApiInterceptor implements HandlerInterceptor {
	private static final Logger lgr = LoggerFactory.getLogger(ApiInterceptor.class);

	@Autowired
	private ITokenBiz tokenBiz;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());

		if (request.getRequestURI().toString().equals(CC.API_ACCOUNT) && request.getMethod().equals("POST")) {
			lgr.debug("registering. not checking http header.");
			return true;
		} else if (request.getRequestURI().equals(CC.API_ACCOUNT) && request.getMethod().equals("GET")) {
			lgr.debug("request for checking email existance. not checking http header.");
			return true;
		} else if (request.getRequestURI().equals(CC.PAGE_LOGIN) && request.getMethod().equals("GET")) {
			lgr.debug("login form requested. not checking http header.");
			return true;
		} else if (request.getRequestURI().equals(CC.API_ACCOUNT) && request.getMethod().equals("PUT")) {
			lgr.debug("logging in. not checking http header.");
			return true;
		}

		// step 1. find jwt from cookey
		String jwToken = CookieMgr.getCookie(request.getCookies(), CC.JW_TOKEN);
		if (jwToken == null) {
			lgr.debug("cookie has no jwToken. redirect to login.html");
			response.sendRedirect(CC.PAGE_LOGIN);
			return false;
		}

		// step 2. read userTokenSeq from cookie
		String userTokenSeqInCookie = CookieMgr.getCookie(request.getCookies(), CC.USER_TOKEN_SEQ_IN_COOKIE);
		if (userTokenSeqInCookie == null || userTokenSeqInCookie.isEmpty()) {
			lgr.debug("cookie has no token seq. redirect to login.html");
			response.sendRedirect(CC.PAGE_LOGIN);
			return false;
		}

		// step 3. read useuserIdFromCookierTokenSeq from cookie
		String userIdFromCookie = CookieMgr.getCookie(request.getCookies(), CC.USER_ID_IN_COOKIE);
		if (userIdFromCookie == null || userIdFromCookie.isEmpty()) {
			lgr.debug("cookie has no userId. redirect to login.html");
			response.sendRedirect(CC.PAGE_LOGIN);
			return false;
		}

		// step 4. get jwt key from database table
		TokenKeyEty tokenKeyEty;
		try {
			tokenKeyEty = new TokenKeyEty(Long.parseLong(userTokenSeqInCookie), Long.parseLong(userIdFromCookie));
		} catch (NumberFormatException e) {
			lgr.debug(
					"NumberFormatException occured. cookie has wrong value: user id [{}], token seq [{}]. redirecting to login.html",
					userIdFromCookie, userTokenSeqInCookie);
			response.sendRedirect(CC.PAGE_LOGIN);
			return false;
		}
		String jwTokenKey = this.tokenBiz.retrieveJwTokenKey(tokenKeyEty);

		if (jwTokenKey == null || jwTokenKey.isEmpty()) {
			lgr.debug("tb_tk_key has no jwTokenKey for the user, cookie key. redirect to login.html");
			response.sendRedirect(CC.PAGE_LOGIN);
			return false;
		}

		// step 4. verify token
		TokenEty tokenEty = JwtMgr.verifyToken(jwToken, jwTokenKey);
		if (tokenEty == null) {
			lgr.debug("the jwToken couldn't be verified. redirect to login.html");
			response.sendRedirect(CC.PAGE_LOGIN);
			return false;
		}

		Long userIdFromTk = tokenEty.getUserId();

		if (!userIdFromTk.equals(Long.parseLong(userIdFromCookie))) {
			lgr.debug("the jwToken has wrong user info. redirect to login.html");
			response.sendRedirect(CC.PAGE_LOGIN);
			return false;
		}

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName() + "\n");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {}
}
