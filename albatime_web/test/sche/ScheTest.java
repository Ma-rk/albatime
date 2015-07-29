package sche;

import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.util.ArrayList;
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

import at.model.ScheEty;
import at.sche.interfaces.IScheBiz;
import at.sche.interfaces.IScheDao;
import at.supp.CC;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class ScheTest {
	private static final Logger lgr = LoggerFactory.getLogger(ScheTest.class);
	@Autowired
	private IScheDao scheDao;
	@Autowired
	private IScheBiz scheBiz;

	List<ScheEty> scheListFixture = new ArrayList<ScheEty>();

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		lgr.debug("deleted lows: [{}]", scheDao.cleanTbScheDao());

		scheListFixture.add(new ScheEty());
		scheListFixture.get(0).setActorSeq(1l);
		scheListFixture.get(0).setMemo("mememem momomo");
		scheListFixture.get(0).setTimeFrom(new Time(5, 6, 7));
		scheListFixture.get(0).setTimeTo(new Time(8, 9, 10));
		scheListFixture.get(0).setUnpaidbreakMin(60);
		scheListFixture.add(new ScheEty());
		scheListFixture.get(1).setActorSeq(1l);
		scheListFixture.get(1).setMemo("mememem momomo2");
		scheListFixture.get(1).setTimeFrom(new Time(10, 11, 12));
		scheListFixture.get(1).setTimeTo(new Time(12, 11, 9));
		scheListFixture.get(1).setUnpaidbreakMin(90);

		for (ScheEty sche : scheListFixture) {
			scheBiz.createScheBiz(sche);
		}
	}

	@Test
	public void retrieveScheTest() {
		ScheEty retrieveQry = new ScheEty();
		retrieveQry.setActorSeq(1l);
		retrieveQry.setStus(CC.SCHE_STUS_NORMAL);;
		List<ScheEty> retrievedScheList = scheBiz.retireveScheListBiz(retrieveQry);

		for (int i = 0; i < retrievedScheList.size(); i++) {
			assertEquals(scheListFixture.get(i).getActorSeq(), retrievedScheList.get(i).getActorSeq());
			assertEquals(scheListFixture.get(i).getMemo(), retrievedScheList.get(i).getMemo());
			assertEquals(scheListFixture.get(i).getTimeFrom(), retrievedScheList.get(i).getTimeFrom());
			assertEquals(scheListFixture.get(i).getTimeTo(), retrievedScheList.get(i).getTimeTo());
			assertEquals(scheListFixture.get(i).getUnpaidbreakMin(), retrievedScheList.get(i).getUnpaidbreakMin());
			assertEquals(CC.SCHE_STUS_NORMAL, retrievedScheList.get(i).getStus());
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void updateScheTest() {
		ScheEty retrieveQry = new ScheEty();
		retrieveQry.setActorSeq(1l);
		retrieveQry.setStus(CC.SCHE_STUS_NORMAL);;
		List<ScheEty> originalScheList = scheBiz.retireveScheListBiz(retrieveQry);

		originalScheList.get(0).setMemo("updated memo 0");
		originalScheList.get(0).setTimeFrom(new Time(5, 4, 3));
		originalScheList.get(0).setTimeTo(new Time(11, 10, 9));
		originalScheList.get(0).setUnpaidbreakMin(15);
		originalScheList.get(1).setMemo("updated memo 0");
		originalScheList.get(1).setTimeFrom(new Time(5, 4, 3));
		originalScheList.get(1).setTimeTo(new Time(11, 10, 9));
		originalScheList.get(1).setUnpaidbreakMin(15);

		assertEquals(1, scheBiz.updateScheBiz(originalScheList.get(0)));
		assertEquals(1, scheBiz.updateScheBiz(originalScheList.get(1)));

		List<ScheEty> updatedCardList = scheBiz.retireveScheListBiz(retrieveQry);

		for (int i = 0; i < updatedCardList.size(); i++) {
			assertEquals(originalScheList.get(i).getMemo(), updatedCardList.get(i).getMemo());
			assertEquals(originalScheList.get(i).getTimeFrom(), updatedCardList.get(i).getTimeFrom());
			assertEquals(originalScheList.get(i).getTimeTo(), updatedCardList.get(i).getTimeTo());
			assertEquals(originalScheList.get(i).getUnpaidbreakMin(), updatedCardList.get(i).getUnpaidbreakMin());
			assertEquals(CC.SCHE_STUS_EDITED, updatedCardList.get(i).getStus());
		}
	}

	@After
	public void tearDown() {
		lgr.debug("deleted lows: [{}]", scheDao.cleanTbScheDao());
	}
}
