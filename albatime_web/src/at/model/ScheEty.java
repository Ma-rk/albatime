package at.model;

import java.sql.Time;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;

import at.supp.CC;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheEty {
	long seq;

	@NotNull
	long actorSeq;

	@Size(min = 1, max = 256)
	String memo;

	@NotNull
	Time timeFrom;

	@NotNull
	Time timeTo;

	@Min(0)
	@Max(120)
	int unpaidbreakMin;

	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	String stus;

	DateTime created;
	DateTime edited;

	public void setAsNormalStus() {
		this.stus = CC.SCHE_STUS_NORMAL;
	}

	public void setAsUpdatedStus() {
		this.stus = CC.SCHE_STUS_EDITED;
	}
}
