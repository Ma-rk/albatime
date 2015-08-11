package module;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import at.com.CC;
import at.model.UserEty;
import at.module.account.interfaces.IAccountBiz;
import at.module.account.interfaces.IAccountDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class AccountTest {
	private static final Logger lgr = LoggerFactory.getLogger(AccountTest.class);
	@Autowired
	IAccountBiz accountBiz;
	@Autowired
	IAccountDao accountDao;

	private List<UserEty> userListFixture = new ArrayList<UserEty>();

	@Before
	public void setUp() {
		lgr.debug("deleted lows: [{}]", accountDao.cleanTbAccountDao());

		userListFixture.add(new UserEty());
		userListFixture.get(0).setEmail("e@mail.com");
		userListFixture.get(0).setPw("pwpwpw");
		userListFixture.get(0).setNick("myNick");
		userListFixture.get(0).setType(CC.USER_TYPE_ALBA);
		userListFixture.add(new UserEty());
		userListFixture.get(1).setEmail("2e@mail.com");
		userListFixture.get(1).setPw("pwpwpwpw");
		userListFixture.get(1).setNick("myNick2");
		userListFixture.get(1).setType(CC.USER_TYPE_OWNER);
	}

	@Test
	public void getEmailCountBizTest() {
		for (int i = 0; i < userListFixture.size(); i++) {
			assertEquals(0, accountBiz.getEmailCountBiz(userListFixture.get(i)));
			accountBiz.registerUserBiz(userListFixture.get(i));
			assertEquals(1, accountBiz.getEmailCountBiz(userListFixture.get(i)));
		}
	}

	@Test
	public void loginBizTest() {
		for (int i = 0; i < userListFixture.size(); i++) {
			accountBiz.registerUserBiz(userListFixture.get(i));
			UserEty loginUser1 = accountBiz.login(userListFixture.get(i));
			assertEquals(userListFixture.get(i).getEmail(), loginUser1.getEmail());
			loginUser1.getPw();
			assertEquals(userListFixture.get(i).getNick(), loginUser1.getNick());
			assertEquals(userListFixture.get(i).getType(), loginUser1.getType());
			assertEquals(userListFixture.get(i).getStus(), loginUser1.getStus());
			loginUser1.getSignUp();
		}
	}
}
