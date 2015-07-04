package supp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import at.account.AccountBizImpl;
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
		int i=0;
		while( i++ <10000){
			generateJwTokenKeyTest();
		}
	}
}