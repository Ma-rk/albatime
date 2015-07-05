package user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import at.model.UserEty;
import at.user.UserCont;
import at.user.UserDaoJdbc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class UserTest {
	private static final Logger lgr = LoggerFactory.getLogger(UserTest.class);

	UserEty user1;

	@Autowired
	private UserCont userCont;

	@Autowired
	private UserDaoJdbc userDao;

	@Before
	public void setUp() throws Exception {
		this.user1 = new UserEty("email@mail.com", "pwpw");
	}

	@Test
	public void loginTest() {
		UserEty loginUser = userCont.login(null, "email@mail.com", "pwpw");
		lgr.info(loginUser.toString());
	}

	@Test
	public void retrieveJwTokenKeyTest() {
		String key = userDao.retrieveJwTokenKey(1l, 1l);
		lgr.info(key);
	}
}
