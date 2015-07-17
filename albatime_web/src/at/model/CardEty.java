package at.model;

import at.supp.CC;

public class CardEty {
	private long seq;
	private long actorSeq;
	private String name;
	private String memo;
	private String timeFrom;
	private String timeTo;
	private int unpaidbreakMin;
	private String stus;
	private String created;
	private String edited;

	public CardEty() {}

	public CardEty(long seq, long actorSeq, String name, String memo, String timeFrom, String timeTo,
			int unpaidbreakMin, String stus, String created, String edited) {
		this.seq = seq;
		this.actorSeq = actorSeq;
		this.name = name;
		this.memo = memo;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.unpaidbreakMin = unpaidbreakMin;
		this.stus = stus;
		this.created = created;
		this.edited = edited;
	}

	public void setAsNormalStus() {
		this.stus = CC.CARD_STUS_NORMAL;
	}

	public void setAsEditedStus() {
		this.stus = CC.CARD_STUS_EDITED;
	}

	public void setAsDeletedStus() {
		this.stus = CC.CARD_STUS_DELETED;
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

	public long getActorSeq() {
		return actorSeq;
	}

	public void setActorSeq(long actorSeq) {
		this.actorSeq = actorSeq;
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

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public int getUnpaidbreakMin() {
		return unpaidbreakMin;
	}

	public void setUnpaidbreakMin(int unpaidbreakMin) {
		this.unpaidbreakMin = unpaidbreakMin;
	}

	public String getStus() {
		return stus;
	}

	public void setStus(String stus) {
		this.stus = stus;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getEdited() {
		return edited;
	}

	public void setEdited(String edited) {
		this.edited = edited;
	}

	public String toString() {
		return "\nCardEty [seq=" + seq + ", actorSeq=" + actorSeq + ", name=" + name + ", memo=" + memo + ", timeFrom="
				+ timeFrom + ", timeTo=" + timeTo + ", unpaidbreakMin=" + unpaidbreakMin + ", stus=" + stus
				+ ", created=" + created + ", edited=" + edited + "]";
	}
}
