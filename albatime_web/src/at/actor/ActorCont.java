package at.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public @ResponseBody String createActor(@CookieValue("userIdInCookie") long userId,
			@RequestParam("actorType") String type, @RequestParam("actorName") String name,
			@RequestParam("actorPhone1") String phone1, @RequestParam("actorPhone2") String phone2,
			@RequestParam("actorAddr1") String addr1, @RequestParam("actorAddr2") String addr2,
			@RequestParam("actorAddr3") String addr3) {
		lgr.debug(CC.GETTING_INTO_2 + "createActor");
		lgr.debug("actorUserId: " + userId);
		lgr.debug("actorType: " + type);
		lgr.debug("actorName: " + name);
		lgr.debug("actorPhone1: " + phone1);
		lgr.debug("actorPhone2: " + phone2);
		lgr.debug("actorAddr1: " + addr1);
		lgr.debug("actorAddr2: " + addr2);
		lgr.debug("actorAddr3: " + addr3);

		ActorEty actor = new ActorEty(userId, type, name, phone1, phone2, addr1, addr2, addr3);

		int inserActorResult = actorBiz.insertActor(actor);

		lgr.debug(CC.GETTING_OUT_2 + "createActor");
		return CC.gson.toJson(inserActorResult);
	}
}
