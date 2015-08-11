package at.module.actor.interfaces;

import java.util.List;

import at.model.ActorEty;

public interface IActorBiz {

	void insertActorBiz(ActorEty actor);

	List<ActorEty> retireveActorListBiz(ActorEty actor);

	void updateActorBiz(ActorEty actor);

	void deleteActorBiz(ActorEty actor);
}
