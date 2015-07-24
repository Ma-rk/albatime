package at.actor;

import java.util.List;

import at.actor.interfaces.IActorBiz;
import at.actor.interfaces.IActorDao;
import at.model.ActorEty;

public class ActorBizImpl implements IActorBiz {
	/*
	 * DI codes
	 */
	private IActorDao actorDao;

	public void setActorDao(IActorDao actorDao) {
		this.actorDao = actorDao;
	}

	/*
	 * functional methods
	 */
	public int insertActorBiz(ActorEty actor) {
		actor.setAsNormalStus();
		return actorDao.insertActorDao(actor);
	}

	public List<ActorEty> retireveActorListBiz(ActorEty actor) {
		actor.setAsNormalStus();
		return actorDao.retireveActorListDao(actor);
	}

	public int updateActorBiz(ActorEty actor) {
		return actorDao.updateActorDao(actor);
	}

	public int deleteActorBiz(ActorEty actor) {
		actor.setAsDeactivatedStus();
		return actorDao.deleteActorDao(actor);
	}
}
