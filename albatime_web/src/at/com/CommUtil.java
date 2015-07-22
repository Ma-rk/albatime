package at.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class CommUtil {
	private static final Logger lgr = LoggerFactory.getLogger(CommUtil.class);

	public static void displayBindingResultErrors(BindingResult result) {
		for (ObjectError error : result.getAllErrors()) {
			lgr.error(error.toString());
		}
	}
}
