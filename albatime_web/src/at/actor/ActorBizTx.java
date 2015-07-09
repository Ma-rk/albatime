package at.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;

import at.actor.interfaces.IActorBiz;
import at.model.ActorEty;
import at.user.interfaces.IUserBiz;

public class ActorBizTx implements IActorBiz {
	private static final Logger lgr = LoggerFactory.getLogger(ActorBizTx.class);

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
}
