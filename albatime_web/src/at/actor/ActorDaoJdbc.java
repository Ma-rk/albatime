package at.actor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import at.actor.interfaces.IActorDao;
import at.model.ActorEty;
import at.supp.CC;
import at.supp.interfaces.ISqlService;

import org.joda.time.DateTime;
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
				actor.getName(), actor.getMemo(), actor.getPeriodFrom(), actor.getPeriodTo(), actor.getWorkTimeUnit(),
				actor.getAlarmBefore(), actor.getUnpaidbreakFlag(), actor.getTaxRate(), actor.getBasicWage(),
				actor.getBgColor(), actor.getPhone1(), actor.getPhone2(), actor.getAddr1(), actor.getAddr2(),
				actor.getAddr3(), actor.getStus());
		lgr.debug(CC.GETTING_OUT_6 + "createActor");
		return insertActorResult;
	}

	@Override
	public List<ActorEty> retireveActorList(long userId) {
		lgr.debug(CC.GETTING_INTO_6 + "retireveActorList");
		RowMapper<ActorEty> rowMapper = new RowMapper<ActorEty>() {
			public ActorEty mapRow(ResultSet rs, int rowNum) {
				try {
					return new ActorEty(rs.getLong("ac_seq"), rs.getLong("ac_usr_id"), rs.getString("ac_name"),
							rs.getString("ac_memo"), rs.getString("ac_period_from"), rs.getString("ac_period_to"),
							rs.getInt("ac_worktime_unit"), rs.getInt("ac_alarm_before"),
							rs.getString("ac_unpaidbreak_flag"), rs.getFloat("ac_tax_rate"),
							rs.getFloat("ac_basic_wage"), rs.getString("ac_color_bg"), rs.getString("ac_phone1"),
							rs.getString("ac_phone2"), rs.getString("ac_addr1_1"), rs.getString("ac_addr1_2"),
							rs.getString("ac_addr1_3"), rs.getString("ac_stus"), rs.getString("ac_created"),
							rs.getString("ac_edited"));
				} catch (SQLException e) {
					throw new BeanInstantiationException(ActorEty.class, e.getMessage(), e);
				}
			}
		};
		lgr.debug(CC.GETTING_OUT_6 + "retireveActorList");
		return this.jdbcTemplate.query(this.sqls.getSql("actorRetrieveActors"), rowMapper, userId);
	}
}
