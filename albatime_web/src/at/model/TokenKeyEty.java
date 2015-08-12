package at.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import at.com.CC;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
public class TokenKeyEty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seq;
	private long userId;
	private String jwTokenKey;
	private String stus;
	private String created;

	public TokenKeyEty(long userId, String jwTokenKey) {
		this.userId = userId;
		this.jwTokenKey = jwTokenKey;
	}

	public TokenKeyEty(long tokenSeq, long userId) {
		this.seq = tokenSeq;
		this.userId = userId;
	}

	public void setAsNormalStus() {
		this.stus = CC.TOKEN_STUS_NORMAL;
	}

	public void setAsExpiredStus() {
		this.stus = CC.TOKEN_STUS_EXPIRED;
	}
}
