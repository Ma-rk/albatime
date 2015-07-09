package at.actor;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;

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

	private PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionMansger) {
		this.transactionManager = transactionMansger;
	}

	/*
	 * functional methods
	 */

	public int insertActor(ActorEty actor) {
		return actorBiz.insertActor(actor);
	}

	public List<ActorEty> retireveActorList(long userId) {
		return actorBiz.retireveActorList(userId);
	}
}
