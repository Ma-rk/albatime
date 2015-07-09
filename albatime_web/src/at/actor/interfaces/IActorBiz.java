package at.actor.interfaces;

import java.util.List;

import at.model.ActorEty;

public interface IActorBiz {

	int insertActor(ActorEty actor);

	List<ActorEty> retireveActorList(long userId);

}
