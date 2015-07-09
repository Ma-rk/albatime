package at.actor;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import at.actor.interfaces.IActorDao;
import at.model.ActorEty;
import at.model.TokenKeyEty;
import at.model.UserEty;
import at.supp.CC;
import at.supp.interfaces.ISqlService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ActorDaoJdbc implements IActorDao {
	private static final Logger lgr = LoggerFactory.getLogger(ActorDaoJdbc.class);
	/*
	 * DI codes
	 */
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private ISqlService sqls;

	public void setSqls(ISqlService sqls) {
		this.sqls = sqls;
	}

	/*
	 * functional methods
	 */
	public int insertActor(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_6 + "createActor");
		int insertActorResult = this.jdbcTemplate.update(this.sqls.getSql("actorCreateActor"), actor.getUserId(),
				actor.getName(), actor.getPhone1(), actor.getPhone2(), actor.getAddr1(), actor.getAddr2(),
				actor.getAddr3(), actor.getStus());
		lgr.debug(CC.GETTING_OUT_6 + "createActor");
		return insertActorResult;
	}
}
