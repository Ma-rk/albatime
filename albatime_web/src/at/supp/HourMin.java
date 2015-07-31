package at.supp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.Getter;

@Data
public class HourMin {

	@Min(0)
	@Max(23)
	@Getter
	private int hour;

	@Min(0)
	@Max(59)
	@Getter
	private int min;

	public HourMin(String hm) {
		this.hour = Integer.parseInt(hm.split(":")[0]);
		this.min = Integer.parseInt(hm.split(":")[1]);
	}

	public HourMin(int hour, int min) {
		this.hour = hour;
		this.min = min;
	}

	public String getTimeString() {
		return String.valueOf(hour) + ":" + String.valueOf(min);
	}
}
