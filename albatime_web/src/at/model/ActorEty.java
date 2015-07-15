package at.model;

import at.supp.CC;

public class ActorEty {
	private long seq;
	private long userId;
	private String name;
	private String memo;
	private String periodFrom;
	private String periodTo;
	private int workTimeUnit;
	private int alarmBefore;
	private String unpaidbreakFlag;
	private float taxRate;
	private float basicWage;
	private String bgColor;
	private String phone1;
	private String phone2;
	private String addr1;
	private String addr2;
	private String addr3;
	private String stus;
	private String created;
	private String edited;

	public ActorEty(long userId, String name, String memo, String periodFrom, String periodTo, int workTimeUnit,
			int alarmBefore, String unpaidbreakFlag, float taxRate, float basicWage, String bgColor, String phone1,
			String phone2, String addr1, String addr2, String addr3) {
		this.userId = userId;
		this.name = name;
		this.memo = memo;
		this.periodFrom = periodFrom;
		this.periodTo = periodTo;
		this.workTimeUnit = workTimeUnit;
		this.alarmBefore = alarmBefore;
		this.unpaidbreakFlag = unpaidbreakFlag;
		this.taxRate = taxRate;
		this.basicWage = basicWage;
		this.bgColor = bgColor;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.addr3 = addr3;
	}

	public ActorEty(long seq, long userId, String name, String memo, String periodFrom, String periodTo,
			int workTimeUnit, int alarmBefore, String unpaidbreakFlag, float taxRate, float basicWage, String bgColor,
			String phone1, String phone2, String addr1, String addr2, String addr3, String stus, String created, String edited) {
		this.seq = seq;
		this.userId = userId;
		this.name = name;
		this.memo = memo;
		this.periodFrom = periodFrom;
		this.periodTo = periodTo;
		this.workTimeUnit = workTimeUnit;
		this.alarmBefore = alarmBefore;
		this.unpaidbreakFlag = unpaidbreakFlag;
		this.taxRate = taxRate;
		this.basicWage = basicWage;
		this.bgColor = bgColor;
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

	public String getName() {
		return name;
	}

	public String getMemo() {
		return memo;
	}

	public String getPeriodFrom() {
		return periodFrom;
	}

	public String getPeriodTo() {
		return periodTo;
	}

	public int getWorkTimeUnit() {
		return workTimeUnit;
	}

	public int getAlarmBefore() {
		return alarmBefore;
	}

	public String getUnpaidbreakFlag() {
		return unpaidbreakFlag;
	}

	public float getTaxRate() {
		return taxRate;
	}

	public float getBasicWage() {
		return basicWage;
	}

	public String getBgColor() {
		return bgColor;
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

	@Override
	public String toString() {
		return "\nActorEty [seq=" + seq + ", userId=" + userId + ", name=" + name + ", memo=" + memo + ", periodFrom="
				+ periodFrom + ", periodTo=" + periodTo + ", workTimeUnit=" + workTimeUnit + ", alarmBefore="
				+ alarmBefore + ", unpaidbreakFlag=" + unpaidbreakFlag + ", taxRate=" + taxRate + ", basicWage="
				+ basicWage + ", bgColor=" + bgColor + ", phone1=" + phone1 + ", phone2=" + phone2 + ", addr1=" + addr1
				+ ", addr2=" + addr2 + ", addr3=" + addr3 + ", stus=" + stus + ", created=" + created + ", edited="
				+ edited + "]";
	}

}
