package at.sche;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import at.model.ScheEty;
import at.sche.interfaces.IScheDao;
import at.supp.CC;
import at.supp.HourMin;
import at.supp.interfaces.ISqlService;

public class ScheDaoJdbc implements IScheDao {
	private static final Logger lgr = LoggerFactory.getLogger(ScheDaoJdbc.class);
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

	public int cleanTbScheDao() {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int truncateScheResult = this.jdbcTemplate.update(this.sqls.getSql("scheCleanTbSche"));
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), truncateScheResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return truncateScheResult;
	}

	public int createScheDao(ScheEty sche) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int createScheResult = this.jdbcTemplate.update(this.sqls.getSql("scheInsertSche"), sche.getActorSeq(),
				sche.getMemo(), sche.getTimeFrom(), sche.getTimeTo(), sche.getUnpaidbreakMin(), sche.getStus());
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), createScheResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return createScheResult;
	}

	public List<ScheEty> retrieveScheListDao(ScheEty sche) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		RowMapper<ScheEty> rowMapper = new RowMapper<ScheEty>() {
			public ScheEty mapRow(ResultSet rs, int rowNum) {
				try {
					return new ScheEty(rs.getLong("sch_seq"), rs.getLong("sch_actor_seq"), rs.getString("sch_memo"),
							new HourMin(rs.getString("sch_time_from")), new HourMin(rs.getString("sch_time_to")),
							rs.getInt("sch_unpaid_break_min"), rs.getString("sch_stus"),
							new DateTime(rs.getTimestamp("sch_created").getTime()),
							new DateTime(rs.getTimestamp("sch_edited")));
				} catch (SQLException e) {
					throw new BeanInstantiationException(ScheEty.class, e.getMessage(), e);
				}
			}
		};
		List<ScheEty> scheList = this.jdbcTemplate.query(this.sqls.getSql("scheRetrieveSches"), rowMapper,
				sche.getActorSeq(), sche.getStus());
		lgr.debug("retrieved [{}] rows.", String.valueOf(scheList.size()));
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return scheList;
	}

	public int updateScheDao(ScheEty sche) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int updateScheResult = this.jdbcTemplate.update(this.sqls.getSql("scheUpdateSche"), sche.getMemo(),
				sche.getTimeFrom(), sche.getTimeTo(), sche.getUnpaidbreakMin(), sche.getStus(), sche.getSeq());
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), updateScheResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return updateScheResult;

	}
}
