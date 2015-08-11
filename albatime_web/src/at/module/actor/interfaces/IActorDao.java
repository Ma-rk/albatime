package at.module.actor.interfaces;

import java.util.List;

import at.model.ActorEty;

public interface IActorDao {
	void insertActorDao(ActorEty actor);

	List<ActorEty> retireveActorListDao(ActorEty actor);

	void updateActorDao(ActorEty actor);

	void deleteActorDao(ActorEty actor);
}
