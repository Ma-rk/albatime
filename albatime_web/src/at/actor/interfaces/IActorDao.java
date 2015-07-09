package at.actor.interfaces;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.model.ActorEty;

public interface IActorDao {
	void setDataSource(DataSource dataSource);

	int insertActor(ActorEty actor);

	List<ActorEty> retireveActorList(long userId);

}
