package at.module.sche;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.com.CC;
import at.model.ScheEty;
import at.module.sche.interfaces.IScheDao;

public class ScheDaoJdbc implements IScheDao {
	private static final Logger lgr = LoggerFactory.getLogger(ScheDaoJdbc.class);

	public void insertScheDao(ScheEty sche) {
		lgr.debug("inserting Schedule for actor [{}]", sche.getActorSeq());
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(sche);
		tx.commit();
	}

	public List<ScheEty> retrieveScheListDao(ScheEty sche) {
		lgr.debug("retrieving Schedule for actor [{}]", sche.getActorSeq());
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<ScheEty> schedules = em
				.createQuery("select a from ScheEty as a where a.actorSeq = :actorSeq and a.stus = :stus",
						ScheEty.class)
				.setParameter("actorSeq", sche.getActorSeq()).setParameter("stus", sche.getStus()).getResultList();
		tx.commit();
		return schedules;
	}

	public void updateScheDao(ScheEty sche) {
		lgr.debug("updating Schedule [{}]", sche.getSeq());
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		ScheEty retrievedSche = em.find(ScheEty.class, sche.getSeq());
		retrievedSche.setMemo(sche.getMemo());
		retrievedSche.setDate(sche.getDate());
		retrievedSche.setHourFrom(sche.getHourFrom());
		retrievedSche.setMinFrom(sche.getMinFrom());
		retrievedSche.setHourTo(sche.getHourTo());
		retrievedSche.setMinTo(sche.getMinTo());
		retrievedSche.setUnpaidbreakMin(sche.getUnpaidbreakMin());
		tx.commit();
	}

	public void deleteScheDao(ScheEty sche) {
		lgr.debug("deleting Schedule [{}]", sche.getSeq());
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		ScheEty retrievedSche = em.find(ScheEty.class, sche.getSeq());
		retrievedSche.setStus(sche.getStus());
		tx.commit();
	}
}
