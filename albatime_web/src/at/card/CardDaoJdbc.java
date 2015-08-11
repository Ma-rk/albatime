package at.card;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.card.interfaces.ICardDao;
import at.com.CC;
import at.model.CardEty;

public class CardDaoJdbc implements ICardDao {
	private static final Logger lgr = LoggerFactory.getLogger(CardDaoJdbc.class);

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
}
