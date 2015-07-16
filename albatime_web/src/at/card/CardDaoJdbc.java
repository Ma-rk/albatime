package at.card;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import at.card.interfaces.ICardDao;
import at.model.CardEty;
import at.supp.CC;
import at.supp.interfaces.ISqlService;

public class CardDaoJdbc implements ICardDao {
	private static final Logger lgr = LoggerFactory.getLogger(CardDaoJdbc.class);
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
	public int insertCardBiz(CardEty card) {
		lgr.debug(CC.GETTING_INTO_6 + "insertCardBiz");
		int insertCardResult = this.jdbcTemplate.update(this.sqls.getSql("cardInsertCard"), card.getActorSeq(),
				card.getName(), card.getMemo(), card.getTimeFrom(), card.getTimeTo(), card.getUnpaidbreakMin(),
				card.getStus());
		lgr.debug(CC.GETTING_OUT_6 + "insertCardBiz");
		return insertCardResult;
	}
}
