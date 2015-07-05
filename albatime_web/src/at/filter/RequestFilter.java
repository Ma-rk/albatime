package at.filter;

import java.io.IOException;
import java.security.SignatureException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.model.TokenEty;
import at.supp.CC;
import at.supp.CookieMgr;
import at.supp.JwtMgr;
import at.user.UserCont;

@WebFilter(urlPatterns = "/*")
public class RequestFilter implements Filter {
	private static final Logger lgr = LoggerFactory.getLogger(RequestFilter.class);
	private static final String DEFAULT_ENCODING = "UTF-8";

	// @Autowired
	// private JwtMgr jwtMgr;
	//
	// public void setJwtMgr(JwtMgr jwtMgr) {
	// this.jwtMgr = jwtMgr;
	// }

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
			lgr.debug("logging in. not checking http header.");
			chain.doFilter(request, response);
			return;
		}

		lgr.debug("normal request. checking http header.");
		lgr.debug("has jwtoken?");

		// step 1. find jwt
		String jwToken = CookieMgr.getCookie(httpRequest.getCookies(), CC.JWT_TOKEN);
		if (jwToken == null) {
			// go to login
		}

		// step 2. read cookie
		String userIdFromCookie = CookieMgr.getCookie(httpRequest.getCookies(), CC.USER_ID_IN_COOKIE);
		String userTokenSeqInCookie = CookieMgr.getCookie(httpRequest.getCookies(), CC.USER_TOKEN_SEQ_IN_COOKIE);
		
		// step 3. get jwt key
		String jwTokenKey = "6ku4k4ihka0tps95u7f2rkcr03";

		// step 4. verify token
		TokenEty tokenEty = JwtMgr.verifyToken(jwToken, jwTokenKey);
		if (tokenEty == null) {
			// go to login
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			httpResponse.sendRedirect("html/login.html");
			return;
		}
		if (tokenEty.getUserId() != -1l) {
			// go to login
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			httpResponse.sendRedirect("html/login.html");
			return;
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void destroy() {}
}
