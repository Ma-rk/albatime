package at.model;

import org.joda.time.DateTime;

public class UserEty {
	private long id;
	private String email;
	private String pw;
	private String nick;
	private String gender;
	private String birth;
	private DateTime signIn;
	private String stus;

	public UserEty(String email) {
		this.email = email;
	}

	public UserEty(String email, String pw) {
		this.email = email;
		this.pw = pw;
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

	public DateTime getDateTime() {
		return signIn;
	}

	public String getStus() {
		return stus;
	}

	public String toString() {
		return "\nUserEty [id=" + id + ", email=" + email + ", pw=" + pw + ", nick=" + nick + ", gender=" + gender
				+ ", birth=" + birth + ", DateTime=" + signIn + "]";
	}
}
