package at.module.actor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import at.model.ActorEty;
import at.module.actor.interfaces.IActorBiz;
import at.module.actor.interfaces.IActorDao;

public class ActorBizImpl implements IActorBiz {

	@Autowired
	private IActorDao actorDao;

	public void insertActorBiz(ActorEty actor) {
		actor.setAsNormalStus();
		actorDao.insertActorDao(actor);
	}

	public List<ActorEty> retireveActorListBiz(ActorEty actor) {
		actor.setAsNormalStus();
		return actorDao.retireveActorListDao(actor);
	}

	public void updateActorBiz(ActorEty actor) {
		actorDao.updateActorDao(actor);
	}

	public void deleteActorBiz(ActorEty actor) {
		actor.setAsDeletedStus();
		actorDao.deleteActorDao(actor);
	}
}
