package at.sche;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import at.actor.ActorDaoJdbc;
import at.model.ScheEty;
import at.sche.interfaces.IScheDao;
import at.supp.CC;
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
}
