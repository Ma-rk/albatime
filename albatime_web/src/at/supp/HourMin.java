package at.supp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class HourMin {

	@Min(0)
	@Max(23)
	int hour;

	@Min(0)
	@Max(59)
	int min;

	public HourMin(String hm) {
		this.hour = Integer.parseInt(hm.split(":")[0]);
		this.min = Integer.parseInt(hm.split(":")[1]);
	}

	public String getTimeString() {
		return String.valueOf(hour) + ":" + String.valueOf(min);
	}
}
