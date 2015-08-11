package at.module.actor.interfaces;

import java.util.List;

import at.model.ActorEty;

public interface IActorDao {
	int insertActorDao(ActorEty actor);

	List<ActorEty> retireveActorListDao(ActorEty actor);

	int updateActorDao(ActorEty actor);

	void deleteActorDao(ActorEty actor);
}
