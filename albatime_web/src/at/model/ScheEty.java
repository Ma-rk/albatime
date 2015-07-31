package at.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;

import at.supp.CC;
import at.supp.HourMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScheEty {
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

	@NotNull
	private HourMin timeFrom;

	@NotNull
	private HourMin timeTo;

	@NotNull
	private HourMin workedHours;

	@Min(0)
	@Max(120)
	@Getter
	@Setter
	private int unpaidbreakMin;

	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	@Getter
	private String stus;

	@Getter
	private DateTime created;

	@Getter
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

	public void setTerm() {
		int hourFrom = timeFrom.getHour();
		int hourTo = timeTo.getHour();
		int min = timeTo.getMin() - timeFrom.getMin();
		if (min < 0) {
			hourTo--;
			min += 60;
		}
		int hour = hourTo - hourFrom;
		if (hour < 0)
			hour += 24;
		this.workedHours = new HourMin(hour, min);
	}

	/*
	 * getters and setters
	 */
	public void setTimeFrom(String hm) {
		this.timeFrom = new HourMin(hm);
	}

	public void setTimeTo(String hm) {
		this.timeTo = new HourMin(hm);
	}

	public String getTimeFrom() {
		return String.valueOf(timeFrom.getHour()) + ":" + String.valueOf(timeFrom.getMin());
	}

	public String getTimeTo() {
		return String.valueOf(timeTo.getHour()) + ":" + String.valueOf(timeTo.getMin());
	}

	public String getWorkedHours() {
		return String.valueOf(workedHours.getHour()) + ":" + String.valueOf(workedHours.getMin());
	}
}
