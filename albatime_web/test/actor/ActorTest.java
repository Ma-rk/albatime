package actor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import at.actor.interfaces.IActorBiz;
import at.actor.interfaces.IActorDao;
import at.com.CC;
import at.model.ActorEty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class ActorTest {
	private static final Logger lgr = LoggerFactory.getLogger(ActorTest.class);
	@Autowired
	private IActorBiz actorBiz;
	@Autowired
	private IActorDao actorDao;

	List<ActorEty> actorListFixture = new ArrayList<ActorEty>();
	final double DELTA = 1e-15;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		lgr.debug("deleted lows: [{}]", actorDao.cleanTbActorDao());

		actorListFixture.add(new ActorEty());
		actorListFixture.get(0).setUserId(1l);
		actorListFixture.get(0).setName("ac 01");
		actorListFixture.get(0).setMemo("mmee");
		actorListFixture.get(0).setPeriodFrom(new Date("2015/07/16"));
		actorListFixture.get(0).setPeriodTo(new Date("2015/08/15"));
		actorListFixture.get(0).setWorkTimeUnit(5);
		actorListFixture.get(0).setAlarmBefore(120);
		actorListFixture.get(0).setUnpaidbreakFlag("y");
		actorListFixture.get(0).setTaxRate(5.5f);
		actorListFixture.get(0).setDefaultWage(7500.5f);
		actorListFixture.get(0).setBgColor("FFFFFF");
		actorListFixture.get(0).setPhone1("010-0101-0101");
		actorListFixture.get(0).setAddr1("경기도 성남시");
		actorListFixture.add(new ActorEty());
		actorListFixture.get(1).setUserId(1l);
		actorListFixture.get(1).setName("ac 02");
		actorListFixture.get(1).setMemo("mmee");
		actorListFixture.get(1).setPeriodFrom(new Date("2015/20/16"));
		actorListFixture.get(1).setPeriodTo(new Date("2015/09/15"));
		actorListFixture.get(1).setWorkTimeUnit(5);
		actorListFixture.get(1).setAlarmBefore(120);
		actorListFixture.get(1).setUnpaidbreakFlag("y");
		actorListFixture.get(1).setTaxRate(5.5f);
		actorListFixture.get(1).setDefaultWage(7500.5f);
		actorListFixture.get(1).setBgColor("FFFFFF");
		actorListFixture.get(1).setPhone1("010-0101-0101");
		actorListFixture.get(1).setAddr1("경기도 성남시");

		for (ActorEty actor : actorListFixture) {
			assertEquals(1, actorBiz.insertActorBiz(actor));
		}
	}

	@Test
	public void retrieveActorTest() {
		List<ActorEty> retrievedActorList = actorBiz.retireveActorListBiz(actorListFixture.get(0));

		for (int i = 0; i < retrievedActorList.size(); i++) {
			assertEquals(retrievedActorList.get(i).getUserId(), actorListFixture.get(i).getUserId());
			assertEquals(retrievedActorList.get(i).getName(), actorListFixture.get(i).getName());
			assertEquals(retrievedActorList.get(i).getMemo(), actorListFixture.get(i).getMemo());
			assertEquals(retrievedActorList.get(i).getPeriodFrom(), actorListFixture.get(i).getPeriodFrom());
			assertEquals(retrievedActorList.get(i).getPeriodTo(), actorListFixture.get(i).getPeriodTo());
			assertEquals(retrievedActorList.get(i).getWorkTimeUnit(), actorListFixture.get(i).getWorkTimeUnit());
			assertEquals(retrievedActorList.get(i).getAlarmBefore(), actorListFixture.get(i).getAlarmBefore());
			assertEquals(retrievedActorList.get(i).getUnpaidbreakFlag(), actorListFixture.get(i).getUnpaidbreakFlag());
			assertEquals(retrievedActorList.get(i).getTaxRate(), actorListFixture.get(i).getTaxRate(), DELTA);
			assertEquals(retrievedActorList.get(i).getDefaultWage(), actorListFixture.get(i).getDefaultWage(), DELTA);
			assertEquals(retrievedActorList.get(i).getBgColor(), actorListFixture.get(i).getBgColor());
			assertEquals(retrievedActorList.get(i).getPhone1(), actorListFixture.get(i).getPhone1());
			assertEquals(retrievedActorList.get(i).getAddr1(), actorListFixture.get(i).getAddr1());
			assertEquals(CC.ACTOR_STUS_NORMAL, actorListFixture.get(i).getStus());
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void updateActorTest() {
		List<ActorEty> retrievedActorList = actorBiz.retireveActorListBiz(actorListFixture.get(0));

		retrievedActorList.get(0).setName("updated name 0");
		retrievedActorList.get(0).setMemo("updated memo 0");
		retrievedActorList.get(0).setPeriodFrom(new Date("2017/20/16"));
		retrievedActorList.get(0).setPeriodTo(new Date("2020/20/16"));
		retrievedActorList.get(0).setWorkTimeUnit(60);
		retrievedActorList.get(0).setAlarmBefore(10);
		retrievedActorList.get(0).setUnpaidbreakFlag("n");
		retrievedActorList.get(0).setTaxRate(0.5f);
		retrievedActorList.get(0).setDefaultWage(40f);
		retrievedActorList.get(0).setBgColor("000000");
		retrievedActorList.get(0).setPhone1("02-0000-0000");
		retrievedActorList.get(0).setAddr1("우리집");
		retrievedActorList.get(1).setName("updated name 1");
		retrievedActorList.get(1).setMemo("updated memo 1");
		retrievedActorList.get(1).setPeriodFrom(new Date("1917/20/16"));
		retrievedActorList.get(1).setPeriodTo(new Date("1920/20/16"));
		retrievedActorList.get(1).setWorkTimeUnit(22);
		retrievedActorList.get(1).setAlarmBefore(15);
		retrievedActorList.get(1).setUnpaidbreakFlag("n");
		retrievedActorList.get(1).setTaxRate(0.6f);
		retrievedActorList.get(1).setDefaultWage(4f);
		retrievedActorList.get(1).setBgColor("aaaaaa");
		retrievedActorList.get(1).setPhone1("02-0110-0110");
		retrievedActorList.get(1).setAddr1("니네집");

		assertEquals(1000, actorBiz.updateActorBiz(retrievedActorList.get(0)));
		assertEquals(1000, actorBiz.updateActorBiz(retrievedActorList.get(1)));

		List<ActorEty> updatedActorList = actorBiz.retireveActorListBiz(actorListFixture.get(0));

		for (int i = 0; i < retrievedActorList.size(); i++) {
			assertEquals(retrievedActorList.get(i).getUserId(), updatedActorList.get(i).getUserId());
			assertEquals(retrievedActorList.get(i).getName(), updatedActorList.get(i).getName());
			assertEquals(retrievedActorList.get(i).getMemo(), updatedActorList.get(i).getMemo());
			assertEquals(retrievedActorList.get(i).getPeriodFrom(), updatedActorList.get(i).getPeriodFrom());
			assertEquals(retrievedActorList.get(i).getPeriodTo(), updatedActorList.get(i).getPeriodTo());
			assertEquals(retrievedActorList.get(i).getWorkTimeUnit(), updatedActorList.get(i).getWorkTimeUnit());
			assertEquals(retrievedActorList.get(i).getAlarmBefore(), updatedActorList.get(i).getAlarmBefore());
			assertEquals(retrievedActorList.get(i).getUnpaidbreakFlag(), updatedActorList.get(i).getUnpaidbreakFlag());
			assertEquals(retrievedActorList.get(i).getTaxRate(), updatedActorList.get(i).getTaxRate(), DELTA);
			assertEquals(retrievedActorList.get(i).getDefaultWage(), updatedActorList.get(i).getDefaultWage(), DELTA);
			assertEquals(retrievedActorList.get(i).getBgColor(), updatedActorList.get(i).getBgColor());
			assertEquals(retrievedActorList.get(i).getPhone1(), updatedActorList.get(i).getPhone1());
			assertEquals(retrievedActorList.get(i).getAddr1(), updatedActorList.get(i).getAddr1());
		}
	}

	@Test
	public void deleteActorTest() {
		List<ActorEty> retrievedActorList = actorBiz.retireveActorListBiz(actorListFixture.get(0));

		for (int i = 0; i < retrievedActorList.size(); i++) {
			actorBiz.deleteActorBiz(retrievedActorList.get(i));
		}
		assertEquals(new ArrayList<ActorEty>(), actorBiz.retireveActorListBiz(actorListFixture.get(0)));
	}

	@After
	public void tearDown() {
		lgr.debug("deleted lows: [{}]", actorDao.cleanTbActorDao());
	}
}
