package at.module.account;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import at.com.CC;
import at.model.UserEty;
import at.module.account.interfaces.IAccountDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

public class AccountDaoJdbc implements IAccountDao {
	private static final Logger lgr = LoggerFactory.getLogger(AccountDaoJdbc.class);

	public int getEmailCountDao(UserEty user) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		long emailCount = em.createQuery("select count(a.email) from UserEty as a where a.email = :email", Long.class)
				.setParameter("email", user.getEmail()).getSingleResult();
		tx.commit();

		lgr.debug("{} result: [{}]", new Object() {}.getClass().getEnclosingMethod().getName(), emailCount);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return (int) emailCount;
	}

	public void registerUserDao(UserEty user) throws DuplicateKeyException {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(user);
		tx.commit();

		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
	}

	public UserEty getUserInfoByEmailAndPw(UserEty user) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

		UserEty userInfo = null;
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		List<UserEty> userList = em
				.createQuery("select a from UserEty as a where a.email = :email and a.pw = :pw and a.stus = :stus",
						UserEty.class)
				.setParameter("email", user.getEmail()).setParameter("pw", user.getPw()).setParameter("stus", user.getStus())
				.getResultList();
		tx.commit();

		if (userList.size() != 0) {
			userInfo = userList.get(0);
		}
		lgr.debug("user: " + user);
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return userInfo;
	}
}
