package at.supp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SuppCont {
	private static final Logger lgr = LoggerFactory.getLogger(SuppCont.class);
	
	@RequestMapping(value = "/retrieveVisitLog", method = RequestMethod.GET)
	public @ResponseBody String retrieveVisitLog() {
		lgr.debug(CC.GETTING_INTO_2 + "retrieveVisitLog");
		lgr.debug(MTC.visitLogMk.toString());
		lgr.debug(CC.GETTING_OUT_2 + "retrieveVisitLog");
		return MTC.visitLogMk.toString();
	}
}