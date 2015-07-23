package at.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class VisitLogEty {
	private String visitorIp;
	private String requestedPage;
	private String requestMethod;
	private String userAgent;
	private DateTime requestedTime;

	public VisitLogEty(String visitorIp, String requestedPage, String requestMethod, String userAgent) {
		this.visitorIp = visitorIp;
		this.requestedPage = requestedPage;
		this.requestMethod = requestMethod;
		this.userAgent = userAgent;
		this.requestedTime = new DateTime();
	}

	public String getVisitorIp() {
		return visitorIp;
	}

	public String getRequestedPage() {
		return requestedPage;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public DateTime getRequestedTime() {
		return requestedTime;
	}

	@Override
	public String toString() {
		return "\nVisitLogEty [visitorIp=" + visitorIp + ", requestedPage=" + requestedPage + ", requestMethod="
				+ requestMethod + ", userAgent=" + userAgent + ", requestedTime="
				+ requestedTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd Z kk:mm:ss:SS")) + "]";
	}
}
