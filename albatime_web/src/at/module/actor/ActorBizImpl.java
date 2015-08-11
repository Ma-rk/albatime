package at.module.actor;

import java.util.List;

import at.model.ActorEty;
import at.module.actor.interfaces.IActorBiz;
import at.module.actor.interfaces.IActorDao;

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

	public void deleteActorBiz(ActorEty actor) {
		actor.setAsDeactivatedStus();
		actorDao.deleteActorDao(actor);
	}
}
