package at.actor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import at.actor.interfaces.IActorDao;
import at.model.ActorEty;
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
	public int insertActorDao(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int insertActorResult = this.jdbcTemplate.update(this.sqls.getSql("actorCreateActor"), actor.getUserId(),
				actor.getName(), actor.getMemo(), actor.getPeriodFrom(), actor.getPeriodTo(), actor.getWorkTimeUnit(),
				actor.getAlarmBefore(), actor.getUnpaidbreakFlag(), actor.getTaxRate(), actor.getBasicWage(),
				actor.getBgColor(), actor.getPhone1(), actor.getAddr1(), actor.getStus());
		lgr.debug("created [{}] actor(s).", String.valueOf(insertActorResult));
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return insertActorResult;
	}

	public List<ActorEty> retireveActorListDao(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		RowMapper<ActorEty> rowMapper = new RowMapper<ActorEty>() {
			public ActorEty mapRow(ResultSet rs, int rowNum) {
				try {
					return new ActorEty(rs.getLong("ac_seq"), rs.getLong("ac_usr_id"), rs.getString("ac_name"),
							rs.getString("ac_memo"), rs.getDate("ac_period_from"), rs.getDate("ac_period_to"),
							rs.getInt("ac_worktime_unit"), rs.getInt("ac_alarm_before"),
							rs.getString("ac_unpaidbreak_flag"), rs.getFloat("ac_tax_rate"),
							rs.getFloat("ac_basic_wage"), rs.getString("ac_color_bg"), rs.getString("ac_phone1"),
							rs.getString("ac_addr1_1"), rs.getString("ac_stus"), rs.getDate("ac_created"),
							rs.getDate("ac_edited"));
				} catch (SQLException e) {
					throw new BeanInstantiationException(ActorEty.class, e.getMessage(), e);
				}
			}
		};
		List<ActorEty> actorList = this.jdbcTemplate.query(this.sqls.getSql("actorRetrieveActors"), rowMapper,
				actor.getUserId(), actor.getStus());
		lgr.debug("retrieved [{}] rows.", String.valueOf(actorList.size()));
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return actorList;
	}

	public int updateActorDao(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_6 + "updateActorDao");
		int updateActorResult = this.jdbcTemplate.update(this.sqls.getSql("actorUpdateActor"), actor.getName(),
				actor.getMemo(), actor.getPeriodFrom(), actor.getPeriodTo(), actor.getWorkTimeUnit(),
				actor.getAlarmBefore(), actor.getUnpaidbreakFlag(), actor.getTaxRate(), actor.getBasicWage(),
				actor.getBgColor(), actor.getPhone1(), actor.getAddr1(), actor.getSeq(), actor.getUserId());
		lgr.debug("updateActorDao result: [{}]", updateActorResult);
		lgr.debug(CC.GETTING_OUT_6 + "updateActorDao");
		return updateActorResult;
	}

	public int deleteActorDao(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_6 + "deleteActorDao");
		lgr.debug("deleting User [{}]'s Actor [{}]", actor.getUserId(), actor.getSeq());
		int deleteActorResult = this.jdbcTemplate.update(this.sqls.getSql("actorDeleteActor"), actor.getStus(),
				actor.getSeq(), actor.getUserId());
		lgr.debug("deleteActorDao result: [{}]", deleteActorResult);
		lgr.debug(CC.GETTING_OUT_6 + "deleteActorDao");
		return deleteActorResult;
	}

	public int cleanTbActorDao() {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int truncateActorResult = this.jdbcTemplate.update(this.sqls.getSql("actorDeleteAllActors"));
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), truncateActorResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return truncateActorResult;
	}
}
