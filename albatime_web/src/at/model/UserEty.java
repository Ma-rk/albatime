package at.model;

import at.supp.CC;

public class UserEty {
	private long id;
	private String email;
	private String pw;
	private String nick;
	private String gender;
	private String birth;
	private String type;
	private String stus;
	private String signIn;
	private String currentJwToken;

	public UserEty(String email) {
		this.email = email;
	}

	public UserEty(String email, String pw) {
		this.email = email;
		this.pw = pw;
	}

	public UserEty(String email, String pw, String nick, String gender, String birth, String type) {
		this.email = email;
		this.pw = pw;
		this.nick = nick;
		this.gender = gender;
		this.birth = birth;
		this.type = type;;
	}

	// public UserEty(long id, String email, String pw, String nick, String
	// gender, String birth, String signIn,
	// String stus) {
	// this.id = id;
	// this.email = email;
	// this.pw = pw;
	// this.nick = nick;
	// this.gender = gender;
	// this.birth = birth;
	// this.signIn = signIn;
	// this.stus = stus;
	// }

	public UserEty(long id, String email, String pw, String nick, String gender, String birth, String type,
			String stus) {
		this.id = id;
		this.email = email;
		this.pw = pw;
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

	/*
	 * getters and setters
	 */
	public String getCurrentJwToken() {
		return currentJwToken;
	}

	public void setCurrentJwToken(String currentJwToken) {
		this.currentJwToken = currentJwToken;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPw() {
		return pw;
	}

	public String getNick() {
		return nick;
	}

	public String getGender() {
		return gender;
	}

	public String getBirth() {
		return birth;
	}

	public String getType() {
		return type;
	}

	public String getStus() {
		return stus;
	}

	public String getSignIn() {
		return signIn;
	}

	@Override
	public String toString() {
		return "UserEty [id=" + id + ", email=" + email + ", pw=" + pw + ", nick=" + nick + ", gender=" + gender
				+ ", birth=" + birth + ", type=" + type + ", stus=" + stus + ", signIn=" + signIn + ", currentJwToken="
				+ currentJwToken + "]";
	}

}
