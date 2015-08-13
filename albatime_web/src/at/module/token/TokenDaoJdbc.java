package at.module.token;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;

import at.com.CC;
import at.model.TokenEty;
import at.model.TokenKeyEty;
import at.module.token.interfaces.ITokenDao;
import at.supp.interfaces.ISqlService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class TokenDaoJdbc implements ITokenDao {
	private static final Logger lgr = LoggerFactory.getLogger(TokenDaoJdbc.class);
	/*
	 * DI codes
	 */
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Autowired
	private ISqlService sqls;

	public long insertJwTokenKey(TokenKeyEty tokenKeyEty) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());

		EntityManager em = CC.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(tokenKeyEty);
		long newJwTokenKeySeq = tokenKeyEty.getSeq();
		tx.commit();

		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return newJwTokenKeySeq;
	}

	public String retrieveJwTokenKey(TokenKeyEty tokenKeyEty) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
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
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return jwTokenKey;
	}

	public List<Map<String, Object>> retrieveJwTokenList(TokenEty tokenEty) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		List<Map<String, Object>> jwTokenList = jdbcTemplate.queryForList(this.sqls.getSql("tkRetireveToken"),
				tokenEty.getUserId(), tokenEty.getStus());
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return jwTokenList;
	}

	public int expireJwTokens(TokenEty tokenEty) {
		lgr.debug(CC.GETTING_INTO_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		int expireJwTokenResult = jdbcTemplate.update(this.sqls.getSql("tkExpireToken"), tokenEty.getStus(),
				tokenEty.getUserId(), tokenEty.getJwTokenKeySeq(), tokenEty.getStus());
		lgr.debug(CC.GETTING_OUT_6 + new Object() {}.getClass().getEnclosingMethod().getName());
		return expireJwTokenResult;
	}
}
