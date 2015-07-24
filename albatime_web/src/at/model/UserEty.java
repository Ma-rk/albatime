package at.model;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import at.supp.CC;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEty {
	private long id;

	@Size(min = 6, max = 64)
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$")
	private String email;

	private String pw;

	@Size(min = 2, max = 32)
	@Pattern(regexp = "([a-zA-Z0-9].*)")
	private String nick;

	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	private String gender;

	private Date birth;

	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	private String type;

	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	private String stus;

	private String signIn;
	private String currentJwToken;
	private long userJwTokenKeySeq;

	public UserEty(String email, String pw) {
		this.email = email;
		this.pw = pw;
	}

	public UserEty(long id, String email, String nick, String gender, Date birth, String type, String stus) {
		this.id = id;
		this.email = email;
		this.nick = nick;
		this.gender = gender;
		this.birth = birth;
		this.type = type;
		this.stus = stus;
	}

	public void setAsNormalStus() {
		this.stus = CC.ACCOUNT_STUS_NORMAL;
	}

	public void setAsDeactivatedStus() {
		this.stus = CC.ACCOUNT_STUS_DEACTIVATED;
	}
}
