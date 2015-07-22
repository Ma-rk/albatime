package at.model;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import at.supp.CC;

public class ActorEty {

	private long seq;
	private long userId;

	@NotNull
	@Size(min = 1, max = 32)
	private String name;

	@Size(min = 1, max = 256)
	private String memo;

	private Date periodFrom;
	private Date periodTo;

	@DecimalMin(value = "1")
	@DecimalMax(value = "60")
	private int workTimeUnit;

	@Min(0)
	@Max(120)
	private int alarmBefore;

	@NotNull
	@Size(min = 1, max = 1)
	@Pattern(regexp = "y|n")
	private String unpaidbreakFlag;

	@Min(0)
	@Max(100)
	@Digits(integer = 3, fraction = 3)
	private float taxRate;

	@Min(0)
	@Digits(integer = 7, fraction = 2)
	private float basicWage;

	private String bgColor;
	private String phone1;
	private String phone2;
	private String addr1;
	private String addr2;
	private String addr3;
	private String stus;
	private Date created;
	private Date edited;

	public ActorEty() {}

	public ActorEty(long seq, long userId, String name, String memo, Date periodFrom, Date periodTo, int workTimeUnit,
			int alarmBefore, String unpaidbreakFlag, float taxRate, float basicWage, String bgColor, String phone1,
			String phone2, String addr1, String addr2, String addr3, String stus, Date created, Date edited) {
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

	public void setAsNormalStus() {
		this.stus = CC.ACTOR_STUS_NORMAL;
	}

	public void setAsDeactivatedStus() {
		this.stus = CC.ACTOR_STUS_DELETED;
	}

	/*
	 * getters and setters
	 */
	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Date getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Date periodTo) {
		this.periodTo = periodTo;
	}

	public int getWorkTimeUnit() {
		return workTimeUnit;
	}

	public void setWorkTimeUnit(int workTimeUnit) {
		this.workTimeUnit = workTimeUnit;
	}

	public int getAlarmBefore() {
		return alarmBefore;
	}

	public void setAlarmBefore(int alarmBefore) {
		this.alarmBefore = alarmBefore;
	}

	public String getUnpaidbreakFlag() {
		return unpaidbreakFlag;
	}

	public void setUnpaidbreakFlag(String unpaidbreakFlag) {
		this.unpaidbreakFlag = unpaidbreakFlag;
	}

	public float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}

	public float getBasicWage() {
		return basicWage;
	}

	public void setBasicWage(float basicWage) {
		this.basicWage = basicWage;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getAddr3() {
		return addr3;
	}

	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}

	public String getStus() {
		return stus;
	}

	public void setStus(String stus) {
		this.stus = stus;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getEdited() {
		return edited;
	}

	public void setEdited(Date edited) {
		this.edited = edited;
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
