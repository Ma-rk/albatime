package at.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.model.VisitLogEty;
import at.supp.MTC;

@WebFilter(urlPatterns = "/*")
public class RequestFilter implements Filter {
	private static final Logger lgr = LoggerFactory.getLogger(RequestFilter.class);
	private static final String DEFAULT_ENCODING = "UTF-8";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(DEFAULT_ENCODING);
		response.setCharacterEncoding(DEFAULT_ENCODING);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void destroy() {}

	public void insertVisitorLogMock(VisitLogEty visitLog) {
		lgr.debug(visitLog.toString());
		MTC.visitLogMk.add(visitLog);
	}
}
