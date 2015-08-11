package at.actor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.actor.interfaces.IActorDao;
import at.com.CC;
import at.model.ActorEty;

public class ActorDaoJdbc implements IActorDao {
	private static final Logger lgr = LoggerFactory.getLogger(ActorDaoJdbc.class);

	public int insertActorDao(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(actor);
		tx.commit();

		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return 1;
	}

	public List<ActorEty> retireveActorListDao(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<ActorEty> actors = em
				.createQuery("select a from ActorEty as a where a.userId = :userId and a.stus = :stus", ActorEty.class)
				.setParameter("userId", actor.getUserId()).setParameter("stus", actor.getStus()).getResultList();
		tx.commit();

		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return actors;
	}

	public int updateActorDao(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		ActorEty retrievedActor = em.find(ActorEty.class, actor.getSeq());
		retrievedActor.setName(actor.getName());
		retrievedActor.setMemo(actor.getMemo());
		retrievedActor.setPeriodFrom(actor.getPeriodFrom());
		retrievedActor.setPeriodTo(actor.getPeriodTo());
		retrievedActor.setWorkTimeUnit(actor.getWorkTimeUnit());
		retrievedActor.setAlarmBefore(actor.getAlarmBefore());
		retrievedActor.setUnpaidbreakFlag(actor.getUnpaidbreakFlag());
		retrievedActor.setDefaultWage(actor.getDefaultWage());
		retrievedActor.setTaxRate(actor.getTaxRate());
		retrievedActor.setBgColor(actor.getBgColor());
		retrievedActor.setPhone1(actor.getPhone1());
		retrievedActor.setAddr1(actor.getAddr1());
		retrievedActor.setStus(actor.getStus());
		tx.commit();

		int updateActorResult = 1000;
		lgr.debug("updateActorDao result: [{}]", updateActorResult);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return updateActorResult;
	}

	public void deleteActorDao(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		lgr.debug("deleting User [{}]'s Actor [{}]", actor.getUserId(), actor.getSeq());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		ActorEty retrievedActor = em.find(ActorEty.class, actor.getSeq());
		retrievedActor.setStus(actor.getStus());
		tx.commit();

		lgr.debug("deleted.");
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
	}
}
