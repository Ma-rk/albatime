package at.module.token;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import at.com.CC;
import at.model.TokenKeyEty;
import at.module.token.interfaces.ITokenDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenDaoJdbc implements ITokenDao {
	private static final Logger lgr = LoggerFactory.getLogger(TokenDaoJdbc.class);

	public long insertJwTokenKey(TokenKeyEty tokenKeyEty) {
		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(tokenKeyEty);
		long newJwTokenKeySeq = tokenKeyEty.getSeq();
		lgr.debug("inserted token key [{}] for token seq [{}]");
		tx.commit();
		return newJwTokenKeySeq;
	}

	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		lgr.debug("retireive token key for tkSeq [{}], userId [{}]", tokenKeyEty.getSeq(), tokenKeyEty.getUserId());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<TokenKeyEty> jwTokenKeyList = em
				.createQuery(
						"select a from TokenKeyEty as a where a.seq = :seq and a.userId = :userId and a.stus = :stus",
						TokenKeyEty.class)
				.setParameter("seq", tokenKeyEty.getSeq()).setParameter("userId", tokenKeyEty.getUserId())
				.setParameter("stus", tokenKeyEty.getStus()).getResultList();
		tx.commit();

		String jwTokenKey = null;
		if (jwTokenKeyList != null && jwTokenKeyList.size() > 0) {
			jwTokenKey = jwTokenKeyList.get(0).getJwTokenKey();
			lgr.debug("jwTokenKey: [{}]", jwTokenKey);
		} else {
			lgr.debug("jwTokenKey: [{}]", "not exists...");
		}
		return jwTokenKey;
	}
}
