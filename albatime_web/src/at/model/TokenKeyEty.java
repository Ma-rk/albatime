package at.model;

import org.joda.time.DateTime;

import at.supp.CC;

public class TokenKeyEty {
	private long seq;
	private long seqUser;
	private long userId;
	private String key;
	private String deviceType;
	private String stus;
	private DateTime createdDate;

	public TokenKeyEty(long userId, String jwTokenKey) {
		this.userId = userId;
		this.key = jwTokenKey;
		setStusAsNormal();
	}

	public void setStusAsNormal() {
		this.stus = CC.TOKEN_STUS_NORMAL;
	}

	public void setStusAsExpired() {
		this.stus = CC.TOKEN_STUS_EXPIRED;
	}

	/*
	 * getters and setters
	 */
	public long getSeq() {
		return seq;
	}

	public long getSeqUser() {
		return seqUser;
	}

	public long getUserId() {
		return userId;
	}

	public String getKey() {
		return key;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public String getStus() {
		return stus;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public String toString() {
		return "\nTokenKeyEty [seq=" + seq + ", seqUser=" + seqUser + ", userId=" + userId + ", key=" + key
				+ ", deviceType=" + deviceType + ", stus=" + stus + ", createdDate=" + createdDate + "]";
	}

}
