package at.card;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

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
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(card);
		tx.commit();
		return 1;
	}

	public List<CardEty> retrieveCardListDao(CardEty card) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<CardEty> cards = em
				.createQuery("select a from CardEty as a where a.actorSeq = :actorSeq and a.stus = :stus",
						CardEty.class)
				.setParameter("actorSeq", card.getActorSeq()).setParameter("stus", card.getStus()).getResultList();
		tx.commit();

		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return cards;
	}

	public int updateCardDao(CardEty card) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		CardEty retrievedCard = em.find(CardEty.class, card.getSeq());
		retrievedCard.setName(card.getName());
		retrievedCard.setMemo(card.getMemo());
		retrievedCard.setHourFrom(card.getHourFrom());
		retrievedCard.setMinFrom(card.getMinFrom());
		retrievedCard.setHourTo(card.getHourTo());
		retrievedCard.setMinTo(card.getMinTo());
		retrievedCard.setUnpaidbreakMin(card.getUnpaidbreakMin());
		retrievedCard.setStus(card.getStus());
		tx.commit();
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return 1;
	}

	public int cleanTbCardDao() {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int truncateCardResult = this.jdbcTemplate.update(this.sqls.getSql("cardCleanTbCard"));
		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), truncateCardResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return truncateCardResult;
	}
}
