package supp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import at.account.AccountBizImpl;
import at.supp.CC;
import at.supp.JwtMgr;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class SupportTest {
	private static final Logger lgr = LoggerFactory.getLogger(SupportTest.class);

	@Test
	public void generateJwTokenKeyTest() {
		String jwtKey = JwtMgr.generateJwTokenKey();
		lgr.info(jwtKey);
	}

	@Test
	public void generateJwTokenKey10000TimesTest() {
		int i = 0;
		while (i++ < 10000) {
			generateJwTokenKeyTest();
		}
	}

	@Test
	public void createJsonWebTokenTest() {
		long userId = 5l;
		String jwTokenKey = JwtMgr.generateJwTokenKey();
		long jwTokenKeySeq = 10l;
		lgr.info("value of jwTokenKey: [" + jwTokenKey + "]");
		lgr.info("value of jwToken: ["
				+ JwtMgr.createJsonWebToken(userId, CC.DEFAULT_SESSION_DURATION_DAYS, jwTokenKey) + "]");

	}
}
