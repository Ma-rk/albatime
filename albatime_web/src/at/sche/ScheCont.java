package at.sche;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.sche.interfaces.IScheBiz;

public class ScheCont {
	private static final Logger lgr = LoggerFactory.getLogger(ScheCont.class);
	@Autowired
	IScheBiz scheBiz;

	public void setScheBiz(IScheBiz scheBiz) {
		this.scheBiz = scheBiz;
	}
}
