package account;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import at.account.AccountCont;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class AccountTest {
	@Autowired
	AccountCont accountCont;

	@Before
	public void setUp() throws Exception {}

	@Test
	public void registerUserContTest() {
//		String registerResult = accountCont.registerUserCont("email@mail.com", "pwpw", "NICK", "GEN001", "BIRTH", "TYPE");
//		assertEquals(1, registerResult);
	}
}
