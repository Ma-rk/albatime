package at.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

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
		lgr.debug("servlet filtering...");

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String visitorIp = getVisitorIp(httpRequest);
		String requestedPage = getRequestedPage(httpRequest);
		String requestMethod = getReauestMethod(httpRequest);
		String userAgent = getUserAgent(httpRequest);
		lgr.debug("full url: " + httpRequest.getRequestURL().toString());

		insertVisitorLogMock(new VisitLogEty(visitorIp, requestedPage, requestMethod, userAgent));
		// try {
		// insertVisitorLog(new VisitLogEty(visitorIp, requestedPage,
		// requestMethod, userAgent));
		// } catch (ClassNotFoundException | SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		chain.doFilter(request, response);
	}

	private String getVisitorIp(HttpServletRequest httpRequest) {
		String visitorIp = httpRequest.getRemoteAddr();
		lgr.debug("visitorIp: " + visitorIp);
		return visitorIp;
	}

	private String getRequestedPage(HttpServletRequest httpRequest) {
		String requestedPage = httpRequest.getRequestURL().toString().substring(21,
				httpRequest.getRequestURL().toString().length());
		lgr.debug("requestedPage: " + requestedPage);
		return requestedPage;
	}

	private String getReauestMethod(HttpServletRequest httpRequest) {
		String requestMethod = httpRequest.getMethod();
		lgr.debug("userAgent: " + requestMethod);
		return requestMethod;
	}

	private String getUserAgent(HttpServletRequest httpRequest) {
		String userAgent = httpRequest.getHeader("user-agent");
		lgr.debug("userAgent: " + userAgent);
		return userAgent;
	}

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void destroy() {}

	public void insertVisitorLogMock(VisitLogEty visitLog) {
		lgr.debug(visitLog.toString());
		MTC.visitLogMk.add(visitLog);
	}

	public void insertVisitorLog(VisitLogEty visitLog) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/at_dev", "mark", "k");
		PreparedStatement pstmt = conn.prepareStatement(
				"insert into tb_visit_log(vl_ip, vl_req_page, vl_req_method, vl_usr_agent) values (?,?,?,?)");

		pstmt.setString(1, visitLog.getVisitorIp());
		pstmt.setString(2, visitLog.getRequestedPage());
		pstmt.setString(3, visitLog.getRequestMethod());
		pstmt.setString(4, visitLog.getUserAgent());

		pstmt.executeUpdate();

		pstmt.close();
		conn.close();
	}

}
