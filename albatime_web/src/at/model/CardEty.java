package at.model;

import java.sql.Time;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import at.com.CC;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardEty {
	private long seq;

	@NotNull
	private long actorSeq;

	@NotNull
	private String name;

	private String memo;

	@NotNull
	private Time timeFrom;

	@NotNull
	private Time timeTo;

	@Min(0)
	@Max(120)
	private int unpaidbreakMin;

	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	private String stus;

	private String created;
	private String edited;

	public void setAsNormalStus() {
		this.stus = CC.CARD_STUS_NORMAL;
	}

	public void setAsEditedStus() {
		this.stus = CC.CARD_STUS_EDITED;
	}

	public void setAsDeletedStus() {
		this.stus = CC.CARD_STUS_DELETED;
	}
}
