package at.model;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CardEty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seq;

	@NotNull
	private long actorSeq;

	@NotNull
	@Size(min = 1, max = 32)
	private String name;

	@Size(min = 0, max = 256)
	private String memo;

	@Min(0)
	@Max(23)
	private int hourFrom;
	@Min(0)
	@Max(59)
	private int minFrom;

	@Min(0)
	@Max(23)
	private int hourTo;
	@Min(0)
	@Max(59)
	@NotNull
	private int minTo;

	@Min(0)
	@Max(120)
	private int unpaidbreakMin;

	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	private String stus;

	@Column(insertable = false, updatable = false)
	private String created;
	@Column(insertable = false, updatable = false)
	private String edited;

	public void setAsNormalStus() {
		this.stus = CC.CARD_STUS_NORMAL;
	}

	public void setAsDeletedStus() {
		this.stus = CC.CARD_STUS_DELETED;
	}
}
