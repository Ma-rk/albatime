package account;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import at.account.AccountCont;
import at.account.AccountDaoJdbc;
import at.model.UserEty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class AccountTest {
	private static final Logger lgr = LoggerFactory.getLogger(AccountTest.class);

	UserEty user1;
	
	@Autowired
	AccountCont accountCont;
	
	@Before
	public void setUp() throws Exception {
		this.user1 = new UserEty("email@mail.com", "pwpw");
	}

	@Test
	public void loginTest() {
		lgr.info(accountCont.login("email@mail.com", "pwpw").toString());
	}
}