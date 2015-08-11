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
	public void insertActorBiz(ActorEty actor) {
		actor.setAsNormalStus();
		actorDao.insertActorDao(actor);
	}

	public List<ActorEty> retireveActorListBiz(ActorEty actor) {
		actor.setAsNormalStus();
		return actorDao.retireveActorListDao(actor);
	}

	public void updateActorBiz(ActorEty actor) {
		actor.setAsNormalStus();
		actorDao.updateActorDao(actor);
	}

	public void deleteActorBiz(ActorEty actor) {
		actor.setAsDeletedStus();
		actorDao.deleteActorDao(actor);
	}
}
