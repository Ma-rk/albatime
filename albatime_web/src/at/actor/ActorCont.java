package at.actor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.actor.interfaces.IActorBiz;
import at.model.ActorEty;
import at.supp.CC;

@Controller
public class ActorCont {
	private static final Logger lgr = LoggerFactory.getLogger(ActorCont.class);
	@Autowired
	IActorBiz actorBiz;

	public void setActorBiz(IActorBiz actorBiz) {
		this.actorBiz = actorBiz;
	}

	@RequestMapping(value = "/api/actor", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody String createActorCont(@CookieValue("userIdInCookie") long userId, ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_2 + "createActor");
		actor.setUserId(userId);
		lgr.debug(actor.toString());

		int inserActorResult = actorBiz.insertActorBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + "createActor");
		return CC.gson.toJson(inserActorResult);
	}

	@RequestMapping(value = "/api/actor", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody String retrieveActorListCont(@CookieValue("userIdInCookie") long userId) {
		lgr.debug(CC.GETTING_INTO_2 + "retrieveActorListCont");
		lgr.debug("retrieving actors of user [{}]", userId);
		ActorEty actor = new ActorEty();
		actor.setUserId(userId);

		List<ActorEty> actorList = actorBiz.retireveActorListBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + "retrieveActorListCont");
		return CC.gson.toJson(actorList);
	}

	@RequestMapping(value = "/api/actor", produces = "application/json", method = RequestMethod.PUT)
	public @ResponseBody String updateActorCont(@CookieValue("userIdInCookie") long userId, ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_2 + "updateActorCont");
		actor.setUserId(userId);
		lgr.debug(actor.toString());

		int updateActorResult = actorBiz.updateActorBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + "updateActorCont");
		return CC.gson.toJson(updateActorResult);
	}

	@RequestMapping(value = "/api/actor", produces = "application/json", method = RequestMethod.DELETE)
	public @ResponseBody String deleteActorCont(ActorEty actor, @CookieValue("userIdInCookie") long userId) {
		lgr.debug(CC.GETTING_INTO_2 + "deleteActorCont");
		actor.setUserId(userId);

		int updateActorResult = actorBiz.deleteActorBiz(actor);

		lgr.debug(CC.GETTING_OUT_2 + "deleteActorCont");
		return CC.gson.toJson(updateActorResult);
	}
}
