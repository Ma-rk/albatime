package at.model;

import org.joda.time.DateTime;

public class TokenEty {
	private String userId;
	private DateTime issued;
	private DateTime expires;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
		return "\nTokenInfo [userId=" + userId + ", issued=" + issued + ", expires=" + expires + "]";
	}
}
