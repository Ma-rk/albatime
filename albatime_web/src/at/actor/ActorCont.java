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
import at.supp.CC;

@RestController
public class ActorCont {
	private static final Logger lgr = LoggerFactory.getLogger(ActorCont.class);
	@Autowired
	IActorBiz actorBiz;

	public void setActorBiz(IActorBiz actorBiz) {
		this.actorBiz = actorBiz;
	}

	@RequestMapping(value = "/api/actor", produces = "application/json", method = RequestMethod.POST)
	public String createActorCont(@CookieValue("userIdInCookie") long userId, @Valid ActorEty actor,
			BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return "0";
		}
		actor.setUserId(userId);
		lgr.debug(actor.toString());

		int inserActorResult = actorBiz.insertActorBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(inserActorResult);
	}

	@RequestMapping(value = "/api/actor", produces = "application/json", method = RequestMethod.GET)
	public String retrieveActorListCont(@CookieValue("userIdInCookie") long userId, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return "0";
		}
		lgr.debug("retrieving actors of user [{}]", userId);
		ActorEty actor = new ActorEty();
		actor.setUserId(userId);

		List<ActorEty> actorList = actorBiz.retireveActorListBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(actorList);
	}

	@RequestMapping(value = "/api/actor", produces = "application/json", method = RequestMethod.PUT)
	public String updateActorCont(@CookieValue("userIdInCookie") long userId, @Valid ActorEty actor,
			BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return "0";
		}
		actor.setUserId(userId);
		lgr.debug(actor.toString());

		int updateActorResult = actorBiz.updateActorBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(updateActorResult);
	}

	@RequestMapping(value = "/api/actor", produces = "application/json", method = RequestMethod.DELETE)
	public String deleteActorCont(ActorEty actor, @CookieValue("userIdInCookie") long userId, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return "0";
		}
		actor.setUserId(userId);

		int updateActorResult = actorBiz.deleteActorBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(updateActorResult);
	}
}
