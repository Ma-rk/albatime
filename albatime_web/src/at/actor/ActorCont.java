package at.actor;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.actor.interfaces.IActorBiz;
import at.com.CommUtil;
import at.model.ActorEty;
import at.model.ResultEty;
import at.supp.CC;

@RestController
public class ActorCont {
	private static final Logger lgr = LoggerFactory.getLogger(ActorCont.class);
	@Autowired
	IActorBiz actorBiz;

	public void setActorBiz(IActorBiz actorBiz) {
		this.actorBiz = actorBiz;
	}

	@RequestMapping(value = CC.API_ACTOR, produces = "application/json", method = RequestMethod.POST)
	public String insertActorCont(@CookieValue(CC.USER_ID_IN_COOKIE) long userId, @Valid ActorEty actor,
			BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_ACTOR_CREATE_FAIL));
		}
		actor.setUserId(userId);
		lgr.debug(actor.toString());

		int inserActorResult = actorBiz.insertActorBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(inserActorResult));
	}

	@RequestMapping(value = CC.API_ACTOR, method = RequestMethod.GET)
	public String retrieveActorListCont(@CookieValue(CC.USER_ID_IN_COOKIE) long userId) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		lgr.debug("retrieving actors of user [{}]", userId);
		ActorEty actor = new ActorEty();
		actor.setUserId(userId);

		List<ActorEty> actorList = actorBiz.retireveActorListBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(actorList));
	}

	@RequestMapping(value = CC.API_ACTOR, produces = "application/json", method = RequestMethod.PUT)
	public String updateActorCont(@CookieValue(CC.USER_ID_IN_COOKIE) long userId, @Valid ActorEty actor,
			BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_ACTOR_UPDATE_FAIL));
		}
		actor.setUserId(userId);
		lgr.debug(actor.toString());

		int updateActorResult = actorBiz.updateActorBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(updateActorResult));
	}

	@RequestMapping(value = CC.API_ACTOR, produces = "application/json", method = RequestMethod.DELETE)
	public String deleteActorCont(@CookieValue(CC.USER_ID_IN_COOKIE) long userId, @Valid ActorEty actor,
			BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(new ResultEty(false, CC.ERROR_ACTOR_DELETE_FAIL));
		}
		actor.setUserId(userId);

		int updateActorResult = actorBiz.deleteActorBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(new ResultEty(updateActorResult));
	}
}
