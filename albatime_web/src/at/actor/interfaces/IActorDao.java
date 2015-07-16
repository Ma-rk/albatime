package at.actor.interfaces;

import java.util.List;

import javax.sql.DataSource;

import at.model.ActorEty;

public interface IActorDao {
	void setDataSource(DataSource dataSource);

	int insertActor(ActorEty actor);

	List<ActorEty> retireveActorListDao(long userId);

	int updateActorDao(ActorEty actor);

	int deleteActorDao(ActorEty actor);
}
