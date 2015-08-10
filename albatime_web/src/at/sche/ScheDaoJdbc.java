package at.sche;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import at.com.CC;
import at.model.ScheEty;
import at.sche.interfaces.IScheDao;
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

	public int insertScheDao(ScheEty sche) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(sche);
		tx.commit();

		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return 1;
	}

	public List<ScheEty> retrieveScheListDao(ScheEty sche) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<ScheEty> schedules = em
				.createQuery("select a from ScheEty as a where a.actorSeq = :actorSeq and a.stus = :stus",
						ScheEty.class)
				.setParameter("actorSeq", sche.getActorSeq())
				.setParameter("stus", sche.getStus())
				.getResultList();
		tx.commit();

		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return schedules;
	}

	public int updateScheDao(ScheEty sche) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

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
		tx.commit();

		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return 1;
	}
}
