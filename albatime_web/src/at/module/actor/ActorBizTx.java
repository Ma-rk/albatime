package at.module.actor;

import java.util.List;

import at.model.ActorEty;
import at.module.actor.interfaces.IActorBiz;

public class ActorBizTx implements IActorBiz {
	/*
	 * DI codes
	 */
	private IActorBiz actorBiz;

	public void setActorBiz(IActorBiz actorBiz) {
		this.actorBiz = actorBiz;
	}

	/*
	 * functional methods
	 */

	public int insertActorBiz(ActorEty actor) {
		return actorBiz.insertActorBiz(actor);
	}

	public List<ActorEty> retireveActorListBiz(ActorEty actor) {
		return actorBiz.retireveActorListBiz(actor);
	}

	public int updateActorBiz(ActorEty actor) {
		return actorBiz.updateActorBiz(actor);
	}

	public void deleteActorBiz(ActorEty actor) {
		actorBiz.deleteActorBiz(actor);
	}
}
