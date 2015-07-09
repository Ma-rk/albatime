package actor;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import at.account.AccountCont;
import at.actor.ActorCont;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class ActorTest {
	private static final Logger lgr = LoggerFactory.getLogger(ActorTest.class);
	@Autowired
	private ActorCont actorCont;

	@Test
	public void createActorTest() {
		String inserActorResult = actorCont.createActor(1l, null, "ACTOR", "02-000-1111", "010-9999-2222", "경기 성남", "분당 삼평", "넥스트");
		lgr.debug(inserActorResult);
	}

}
