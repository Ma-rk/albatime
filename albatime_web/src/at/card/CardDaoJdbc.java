package at.card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import at.card.interfaces.ICardDao;
import at.com.CC;
import at.model.CardEty;
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
	public int insertCardDao(CardEty card) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int insertCardResult = this.jdbcTemplate.update(this.sqls.getSql("cardInsertCard"), card.getActorSeq(),
				card.getName(), card.getMemo(), card.getTimeFrom(), card.getTimeTo(), card.getUnpaidbreakMin(),
				card.getStus());
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), insertCardResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return insertCardResult;
	}

	public List<CardEty> retrieveCardListDao(CardEty card) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		RowMapper<CardEty> rowMapper = new RowMapper<CardEty>() {
			public CardEty mapRow(ResultSet rs, int rowNum) {
				try {
					return new CardEty(rs.getLong("crd_seq"), rs.getLong("crd_actor_seq"), rs.getString("crd_name"),
							rs.getString("crd_memo"), rs.getTime("crd_time_from"), rs.getTime("crd_time_to"),
							rs.getInt("crd_unpaid_break_min"), rs.getString("crd_stus"), rs.getString("crd_created"),
							rs.getString("crd_edited"));
				} catch (SQLException e) {
					throw new BeanInstantiationException(CardEty.class, e.getMessage(), e);
				}
			}
		};
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return this.jdbcTemplate.query(this.sqls.getSql("cardRetrieveCards"), rowMapper, card.getActorSeq());
	}

	public int updateCardDao(CardEty card) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		lgr.debug("updating card for actor [{}], card seq [{}]", card.getActorSeq(), card.getSeq());
		int updateCardResult = this.jdbcTemplate.update(this.sqls.getSql("cardUpdateCards"), card.getName(),
				card.getMemo(), card.getTimeFrom(), card.getTimeTo(), card.getUnpaidbreakMin(), card.getStus(),
				card.getSeq(), card.getActorSeq());
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), updateCardResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return updateCardResult;
	}

	public int cleanTbActorDao() {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int truncateCardResult = this.jdbcTemplate.update(this.sqls.getSql("cardCleanTbCard"));
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), truncateCardResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return truncateCardResult;
	}
}
