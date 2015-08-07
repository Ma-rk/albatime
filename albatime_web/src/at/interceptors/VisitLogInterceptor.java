package at.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import at.com.interfaces.IComDao;
import at.model.VisitLogEty;
import at.supp.MTC;

public class VisitLogInterceptor implements HandlerInterceptor {
	private static final Logger lgr = LoggerFactory.getLogger(VisitLogInterceptor.class);

	@Autowired
	private IComDao comDao;

	public void setComDao(IComDao comDao) {
		this.comDao = comDao;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		writeRequestLog(request);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {}

	private void writeRequestLog(HttpServletRequest request) {
		VisitLogEty visitLogEty = new VisitLogEty(request.getRemoteAddr(), request.getRequestURI().toString(),
				request.getMethod(), request.getHeader("user-agent"));
		lgr.debug(visitLogEty.toString());
		comDao.insertVisitLog(visitLogEty);
		MTC.visitLogMock.add(visitLogEty);
	}
}
