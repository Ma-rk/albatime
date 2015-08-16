package at.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import at.com.CC;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ScheEty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private long seq;

	@NotNull
	@Getter
	@Setter
	private long actorSeq;

	@Size(min = 1, max = 256)
	@Getter
	@Setter
	private String memo;

	@Getter
	@Setter
	@NotNull
	private Date date;

	@Getter
	@NotNull
	private int hourFrom;
	@Getter
	@NotNull
	private int minFrom;

	@Getter
	@NotNull
	private int hourTo;
	@Getter
	@NotNull
	private int minTo;

	@NotNull
	@Getter
	private int mins = 0;

	@Min(0)
	@Max(120)
	@Getter
	@Setter
	private int unpaidbreakMin;

	@Getter
	@Setter
	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	String worked;

	@Getter
	@Setter
	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	String paid;

	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	@Getter
	@Setter
	private String stus;

	@Getter
	@Setter
	@Column(insertable = false, updatable = false)
	private String created;

	@Getter
	@Setter
	@Column(insertable = false, updatable = false)
	private String edited;

	public void setAsNormalStus() {
		this.stus = CC.SCHE_STUS_NORMAL;
	}

	public void setAsDeletedStus() {
		this.stus = CC.SCHE_STUS_DELETED;
	}

	public void setAsWorkedNo() {
		this.worked = CC.SCHE_WORKED_NO;
	}

	public void setAsWorkedYes() {
		this.worked = CC.SCHE_WORKED_YES;
	}

	public void setAsPaidNo() {
		this.paid = CC.SCHE_PAID_NO;
	}

	public void setAsPaidYes() {
		this.paid = CC.SCHE_PAID_YES;
	}

	/*
	 * getters and setters
	 */
	public void setMins() {
		this.mins = (hourTo * 60 + minTo) - (hourFrom * 60 + minFrom);
		if (mins < 0)
			this.mins += 1440;
	}

	public void setHourFrom(int hourFrom) {
		this.hourFrom = hourFrom;
		setMins();
	}

	public void setMinFrom(int minFrom) {
		this.minFrom = minFrom;
		setMins();
	}

	public void setHourTo(int hourTo) {
		this.hourTo = hourTo;
		setMins();
	}

	public void setMinTo(int minTo) {
		this.minTo = minTo;
		setMins();
	}
}
