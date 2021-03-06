package module;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import at.model.TokenKeyEty;
import at.model.UserEty;
import at.module.token.TokenDaoHBN;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class TokenTest {
	private static final Logger lgr = LoggerFactory.getLogger(TokenTest.class);

	UserEty user1;

	@Autowired
	private TokenDaoHBN tokenDao;

	@Before
	public void setUp() throws Exception {
		this.user1 = new UserEty("email@mail.com", "pwpw");
	}

	@Test
	public void loginTest() {
//		String loginUser = userCont.login(null, "email@mail.com", "pwpw");
//		lgr.info(loginUser.toString());
	}

	@Test
	public void retrieveJwTokenKeyTest() {
		TokenKeyEty tokenKeyEty = new TokenKeyEty(1l, 1l);
		tokenKeyEty.setAsNormalStus();
		String key = tokenDao.retrieveJwTokenKey(tokenKeyEty);
		lgr.info(key);
	}
}
