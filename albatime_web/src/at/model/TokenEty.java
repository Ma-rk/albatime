package at.model;

import org.joda.time.DateTime;

import at.com.CC;

public class TokenEty {
	private long userId = -1l;
	private long jwTokenKeySeq;
	private DateTime issued;
	private DateTime expires;
	private String stus;

	public TokenEty() {}

	public TokenEty(long userId) {
		this.userId = userId;
	}

	public TokenEty(long userId, long jwTokenKeySeq) {
		this.userId = userId;
		this.jwTokenKeySeq = jwTokenKeySeq;
	}

	public void setAsNormalStus() {
		this.stus = CC.TOKEN_STUS_NORMAL;
	}

	public void setAsExpiredStus() {
		this.stus = CC.TOKEN_STUS_EXPIRED;
	}

	/*
	 * getters and setters
	 */
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

	public String getStus() {
		return stus;
	}

	public void setStus(String stus) {
		this.stus = stus;
	}

	public String toString() {
		return "TokenEty [userId=" + userId + ", jwTokenKeySeq=" + jwTokenKeySeq + ", issued=" + issued + ", expires="
				+ expires + ", stus=" + stus + "]";
	}
}
