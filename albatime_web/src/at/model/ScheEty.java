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

import org.joda.time.DateTime;

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
	private int mins;

	@Min(0)
	@Max(120)
	@Getter
	@Setter
	private int unpaidbreakMin;

	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	@Getter
	private String stus;

	@Getter
	@Column(insertable = false, updatable = false)
	private DateTime created;

	@Getter
	@Column(insertable = false, updatable = false)
	private DateTime edited;

	public void setAsNormalStus() {
		this.stus = CC.SCHE_STUS_NORMAL;
	}

	public void setAsUpdatedStus() {
		this.stus = CC.SCHE_STUS_EDITED;
	}

	public void setAsEditedStus() {
		this.stus = CC.SCHE_STUS_EDITED;
	}

	/*
	 * getters and setters
	 */
	public void setMins() {
		this.mins = (hourTo * 60 + minTo) - (hourFrom * 60 + minFrom);
	}

	public void setHourFrom(int hourFrom) {
		setMins();
		this.hourFrom = hourFrom;
	}

	public void setMinFrom(int minFrom) {
		setMins();
		this.hourFrom = minFrom;
	}

	public void setHourTo(int hourTo) {
		setMins();
		this.hourTo = hourTo;
	}

	public void setMinTo(int minTo) {
		setMins();
		this.hourTo = minTo;
	}
}
