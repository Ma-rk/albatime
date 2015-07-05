package at.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.model.TokenEty;
import at.supp.CC;
import at.supp.CookieMgr;
import at.supp.JwtMgr;
import at.user.interfaces.IUserDao;

@WebFilter(urlPatterns = "/*")
public class RequestFilter implements Filter {
	private static final Logger lgr = LoggerFactory.getLogger(RequestFilter.class);
	private static final String DEFAULT_ENCODING = "UTF-8";

	@Autowired
	private IUserDao userDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
		if (this.userDao != null)
			lgr.debug("userDao set!!");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(DEFAULT_ENCODING);
		response.setCharacterEncoding(DEFAULT_ENCODING);
		lgr.debug("servlet filtering...");

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String requestedPage = httpRequest.getRequestURL().toString().substring(21,
				httpRequest.getRequestURL().toString().length());
		lgr.debug("requestedPage: " + requestedPage);
		lgr.debug("full url: " + httpRequest.getRequestURL().toString());

		if (requestedPage.equals("/registerUser")) {
			lgr.debug("registering. not checking http header.");
			chain.doFilter(request, response);
			return;
		} else if (requestedPage.equals("/html/login.html")) {
			lgr.debug("login form requested. not checking http header.");
			chain.doFilter(request, response);
			return;
		} else if (requestedPage.equals("/login")) {
			lgr.debug("logging in. not checking http header.");
			chain.doFilter(request, response);
			return;
		}

		lgr.debug("normal request. checking http header.");
		lgr.debug("");
		lgr.debug("has jwtoken?");

		// step 1. find jwt
		String jwToken = CookieMgr.getCookie(httpRequest.getCookies(), CC.JWT_TOKEN);
		if (jwToken == null) {
			lgr.debug("cookie has no jwToken. redirect to login");
			((HttpServletResponse) response).sendRedirect("html/login.html");
			return;
		}

		// step 2. read cookie
		String userTokenSeqInCookie = CookieMgr.getCookie(httpRequest.getCookies(), CC.USER_TOKEN_SEQ_IN_COOKIE);
		if (userTokenSeqInCookie == null || userTokenSeqInCookie.isEmpty()) {
			lgr.debug("cookie has no token seq. redirect to login");
			((HttpServletResponse) response).sendRedirect("html/login.html");
			return;
		}

		String userIdFromCookie = CookieMgr.getCookie(httpRequest.getCookies(), CC.USER_ID_IN_COOKIE);
		if (userIdFromCookie == null || userIdFromCookie.isEmpty()) {
			lgr.debug("cookie has no userId. redirect to login");
			((HttpServletResponse) response).sendRedirect("html/login.html");
			return;
		}

		// step 3. get jwt key
		// String jwTokenKey =
		// this.userDao.retrieveJwTokenKey(Integer.parseInt(userTokenSeqInCookie),
		// Integer.parseInt(userIdFromCookie));
		String jwTokenKey = "pvgk3onr6gjls8n8g10hlcb5lk";
		if (jwTokenKey == null || jwTokenKey.isEmpty()) {
			lgr.debug("tb_token has no jwTokenKey for the user, cookie key. redirect to login");
			return;
		}

		// step 4. verify token
		TokenEty tokenEty = JwtMgr.verifyToken(jwToken, jwTokenKey);
		if (tokenEty == null) {
			lgr.debug("the jwToken couldn't be verified. redirect to login");
			((HttpServletResponse) response).sendRedirect("html/login.html");
			return;
		}
		if (tokenEty.getUserId() == Long.parseLong(userIdFromCookie)) {
			lgr.debug("the jwToken couldn't be verified. redirect to login");
			((HttpServletResponse) response).sendRedirect("html/login.html");
			return;
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void destroy() {}
}
