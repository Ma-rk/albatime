package at.actor;

import java.util.List;

import at.actor.interfaces.IActorBiz;
import at.model.ActorEty;

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

	public int deleteActorBiz(ActorEty actor) {
		return actorBiz.deleteActorBiz(actor);
	}
}
