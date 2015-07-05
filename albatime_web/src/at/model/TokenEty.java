package at.model;

import org.joda.time.DateTime;

public class TokenEty {
	private long userId = -1l;
	private long jwTokenKeySeq;
	private DateTime issued;
	private DateTime expires;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getJwTokenKeySeq() {
		return jwTokenKeySeq;
	}

	public void setJwTokenKeySeq(long jwTokenKeySeq) {
		this.jwTokenKeySeq = jwTokenKeySeq;
	}

	public DateTime getIssued() {
		return issued;
	}

	public void setIssued(DateTime issued) {
		this.issued = issued;
	}

	public DateTime getExpires() {
		return expires;
	}

	public void setExpires(DateTime expires) {
		this.expires = expires;
	}

	public String toString() {
		return "TokenEty [userId=" + userId + ", jwTokenKeySeq=" + jwTokenKeySeq + ", issued=" + issued + ", expires="
				+ expires + "]";
	}
}
