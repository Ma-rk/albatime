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

	public void insertActorBiz(ActorEty actor) {
		actorBiz.insertActorBiz(actor);
	}

	public List<ActorEty> retireveActorListBiz(ActorEty actor) {
		return actorBiz.retireveActorListBiz(actor);
	}

	public void updateActorBiz(ActorEty actor) {
		actorBiz.updateActorBiz(actor);
	}

	public void deleteActorBiz(ActorEty actor) {
		actorBiz.deleteActorBiz(actor);
	}
}
