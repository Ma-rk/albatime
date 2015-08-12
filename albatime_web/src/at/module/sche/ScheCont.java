package at.module.sche;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.com.CC;
import at.com.CommUtil;
import at.model.ScheEty;
import at.module.sche.interfaces.IScheBiz;
import at.supp.ResultFac;

@RestController
@RequestMapping(value = CC.API_SCHE)
public class ScheCont {
	private static final Logger lgr = LoggerFactory.getLogger(ScheCont.class);

	@Autowired
	IScheBiz scheBiz;

	@RequestMapping(method = RequestMethod.POST)
	public String insertScheCont(@Valid ScheEty sche, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_SCHEDULE_CREATE_FAIL));
		}
		lgr.debug(sche.toString());

		scheBiz.insertScheBiz(sche);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(ResultFac.rf());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String retrieveScheListCont(@Valid ScheEty sche, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_SCHEDULE_RETRIEVE_FAIL));
		}
		lgr.debug(sche.toString());

		List<ScheEty> scheList = scheBiz.retireveScheListBiz(sche);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(ResultFac.rf(scheList));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String updateScheCont(@Valid ScheEty sche, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_SCHEDULE_UPDATE_FAIL));
		}
		lgr.debug(sche.toString());

		scheBiz.updateScheBiz(sche);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(ResultFac.rf());
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public String deleteScheCont(@Valid ScheEty sche, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_SCHEDULE_DELETE_FAIL));
		}
		lgr.debug(sche.toString());

		scheBiz.deleteScheBiz(sche);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(ResultFac.rf());
	}
}
