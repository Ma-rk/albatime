package at.sche;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.com.CommUtil;
import at.model.ResultEty;
import at.model.ScheEty;
import at.sche.interfaces.IScheBiz;
import at.supp.CC;

@RestController
@RequestMapping(value = CC.API_SCHE)
public class ScheCont {
	private static final Logger lgr = LoggerFactory.getLogger(ScheCont.class);
	@Autowired
	IScheBiz scheBiz;

	public void setScheBiz(IScheBiz scheBiz) {
		this.scheBiz = scheBiz;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String insertScheCont(@Valid ScheEty sche, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_ACTOR_CREATE_FAIL));
		}
		lgr.debug(sche.toString());

		int insertScheResult = scheBiz.createScheBiz(sche);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(insertScheResult));
	}
}
