package at.module.card;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.com.CC;
import at.model.CardEty;
import at.module.card.interfaces.ICardDao;

public class CardDaoJdbc implements ICardDao {
	private static final Logger lgr = LoggerFactory.getLogger(CardDaoJdbc.class);

	public void insertCardDao(CardEty card) {
		lgr.debug("inserting Card for actor [{}]", card.getActorSeq());
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(card);
		tx.commit();
	}

	public List<CardEty> retrieveCardListDao(CardEty card) {
		lgr.debug("retrieving Cark for actor [{}]", card.getActorSeq());
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<CardEty> cards = em
				.createQuery("select a from CardEty as a where a.actorSeq = :actorSeq and a.stus = :stus",
						CardEty.class)
				.setParameter("actorSeq", card.getActorSeq()).setParameter("stus", card.getStus()).getResultList();
		tx.commit();
		return cards;
	}

	public void updateCardDao(CardEty card) {
		lgr.debug("updating Cark [{}]", card.getSeq());
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
		tx.commit();
	}

	public void deleteCardDao(CardEty card) {
		lgr.debug("deleting Card [{}]", card.getSeq());
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		CardEty retrievedCard = em.find(CardEty.class, card.getSeq());
		retrievedCard.setStus(card.getStus());
		tx.commit();
	}
}
