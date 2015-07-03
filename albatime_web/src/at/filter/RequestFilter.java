package at.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.model.TokenEty;
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

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(DEFAULT_ENCODING);
		response.setCharacterEncoding(DEFAULT_ENCODING);
		lgr.debug("servlet filtering...");

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String requestedPage = httpRequest.getRequestURL().toString().substring(21, httpRequest.getRequestURL().toString().length());
		lgr.debug("requestedPage: " + requestedPage);
		lgr.debug("full url: " + httpRequest.getRequestURL().toString());

		if (requestedPage.equals("/register")) {
			lgr.debug("registering. not checking http header.");
			chain.doFilter(request, response);
			return;
		} else if (requestedPage.equals("/login")) {
			lgr.debug("logging in. not checking http header.");
			chain.doFilter(request, response);
			return;
		}

		lgr.debug("normal request. checking http header.");
		lgr.debug("has jwtoken?");

		// step 1. find jwt
		String jwtoken = CookieMgr.getCookie(httpRequest.getCookies(), "jwtoken");
		if (jwtoken == null) {
			// go to login
		}

		// step 2. read jwt
		TokenEty tokenEty = JwtMgr.verifyToken(jwtoken);
		if (tokenEty == null) {
			// go to login
		}
		if (tokenEty.getUserId() == null || tokenEty.getUserId().isEmpty()) {
			// go to login
		}

		String jwtoken2 = JwtMgr.createJsonWebToken("aaa", 5l);
		httpRequest.setAttribute("jwt", jwtoken);
		chain.doFilter((ServletRequest) httpRequest, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}
}
