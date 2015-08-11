package at.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
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
public class ActorEty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seq;

	private long userId;

	@NotNull
	@Size(min = 1, max = 32)
	private String name;

	@Size(min = 1, max = 256)
	private String memo;

	@Temporal(TemporalType.DATE)
	private Date periodFrom;
	@Temporal(TemporalType.DATE)
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
	@Digits(integer = 7, fraction = 2)
	private float defaultWage;

	@Min(0)
	@Max(100)
	@Digits(integer = 3, fraction = 3)
	private float taxRate;

	private String bgColor;
	private String phone1;
	private String addr1;

	@Pattern(regexp = "[A-Z]{3}[_][A-Z]{3}[_][0-9]{2}")
	private String stus;

	@Column(insertable = false, updatable = false)
	private Date created;

	@Column(insertable = false, updatable = false)
	private Date edited;

	public void setAsNormalStus() {
		this.stus = CC.ACTOR_STUS_NORMAL;
	}

	public void setAsDeletedStus() {
		this.stus = CC.ACTOR_STUS_DELETED;
	}
}
