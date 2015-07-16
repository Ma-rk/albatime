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
	private long userJwTokenKeySeq;

	public UserEty() {}

	public UserEty(String email, String pw) {
		this.email = email;
		this.pw = pw;
	}

	public UserEty(long id, String email, String nick, String gender, String birth, String type, String stus) {
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

	/*
	 * getters and setters
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStus() {
		return stus;
	}

	public void setStus(String stus) {
		this.stus = stus;
	}

	public String getSignIn() {
		return signIn;
	}

	public void setSignIn(String signIn) {
		this.signIn = signIn;
	}

	public String getCurrentJwToken() {
		return currentJwToken;
	}

	public void setCurrentJwToken(String currentJwToken) {
		this.currentJwToken = currentJwToken;
	}

	public long getUserJwTokenKeySeq() {
		return userJwTokenKeySeq;
	}

	public void setUserJwTokenKeySeq(long userJwTokenKeySeq) {
		this.userJwTokenKeySeq = userJwTokenKeySeq;
	}

	public String toString() {
		return "UserEty [id=" + id + ", email=" + email + ", pw=" + pw + ", nick=" + nick + ", gender=" + gender
				+ ", birth=" + birth + ", type=" + type + ", stus=" + stus + ", signIn=" + signIn + ", userTkSeq="
				+ userJwTokenKeySeq + ", currentJwToken=" + currentJwToken + "]";
	}
}
