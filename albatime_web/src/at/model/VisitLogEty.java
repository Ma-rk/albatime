package at.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class VisitLogEty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long visitSeq;

	private String visitorIp;
	private String reqPage;
	private String reqMethod;
	private String userAgent;

	@Column(insertable = false, updatable = false)
	private DateTime visitDatetime;

	public VisitLogEty(String visitorIp, String reqPage, String reqMethod, String userAgent) {
		this.visitorIp = visitorIp;
		this.reqPage = reqPage;
		this.reqMethod = reqMethod;
		this.userAgent = userAgent;
		this.visitDatetime = new DateTime();
	}

	public String toString() {
		return "\nVisitLogEty [visitorIp=" + visitorIp + ", requestedPage=" + reqPage + ", requestMethod="
				+ reqMethod + ", userAgent=" + userAgent + ", requestedTime="
				+ visitDatetime.toString(DateTimeFormat.forPattern("yyyy-MM-dd Z kk:mm:ss:SS")) + "]";
	}
}
