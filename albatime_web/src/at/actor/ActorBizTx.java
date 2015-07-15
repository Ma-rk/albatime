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

	public int insertActorBiz(ActorEty actor) {
		return actorBiz.insertActorBiz(actor);
	}

	public List<ActorEty> retireveActorListBiz(long userId) {
		return actorBiz.retireveActorListBiz(userId);
	}

	@Override
	public int updateActorBiz(ActorEty actor) {
		return actorBiz.updateActorBiz(actor);
	}
}
