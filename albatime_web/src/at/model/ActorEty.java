package at.model;

import at.supp.CC;

public class ActorEty {
	private long seq;
	private long userId;
	private String type;
	private String name;
	private String phone1;
	private String phone2;
	private String addr1;
	private String addr2;
	private String addr3;
	private String stus;
	private String created;
	private String edited;

	public ActorEty(long userId, String type, String name, String phone1, String phone2, String addr1, String addr2,
			String addr3) {
		this.userId = userId;
		this.type = type;
		this.name = name;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.addr3 = addr3;
	}

	public ActorEty(long seq, long userId, String type, String name, String phone1, String phone2, String addr1,
			String addr2, String addr3, String stus, String created, String edited) {
		this.seq = seq;
		this.userId = userId;
		this.type = type;
		this.name = name;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.addr3 = addr3;
		this.stus = stus;
		this.created = created;
		this.edited = edited;
	}

	public void setStusAsNormal() {
		this.stus = CC.ACTOR_STUS_NORMAL;
	}

	public void setStusAsDeleted() {
		this.stus = CC.ACTOR_STUS_DELETED;
	}

	public long getSeq() {
		return seq;
	}

	public long getUserId() {
		return userId;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getPhone1() {
		return phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public String getAddr1() {
		return addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public String getAddr3() {
		return addr3;
	}

	public String getStus() {
		return stus;
	}

	public String getCreated() {
		return created;
	}

	public String getEdited() {
		return edited;
	}

	public String toString() {
		return "\nActorEty [seq=" + seq + ", userId=" + userId + ", type=" + type + ", name=" + name + ", phone1="
				+ phone1 + ", phone2=" + phone2 + ", addr1=" + addr1 + ", addr2=" + addr2 + ", addr3=" + addr3
				+ ", stus=" + stus + ", created=" + created + ", edited=" + edited + "]";
	}

}
