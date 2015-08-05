package at.actor.interfaces;

import java.util.List;

import javax.sql.DataSource;

import at.model.ActorEty;

public interface IActorDao {
	void setDataSource(DataSource dataSource);

	int insertActorDao(ActorEty actor);

	List<ActorEty> retireveActorListDao(ActorEty actor);

	int updateActorDao(ActorEty actor);

	void deleteActorDao(ActorEty actor);

	int cleanTbActorDao();
}
