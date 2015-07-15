package at.actor.interfaces;

import java.util.List;

import at.model.ActorEty;

public interface IActorBiz {

	int insertActorBiz(ActorEty actor);

	List<ActorEty> retireveActorListBiz(long userId);

}
