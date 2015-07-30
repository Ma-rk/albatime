package sche;

import static org.junit.Assert.assertEquals;

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

	@Before
	public void setUp() {
		lgr.debug("deleted lows: [{}]", scheDao.cleanTbScheDao());

		scheListFixture.add(new ScheEty());
		scheListFixture.get(0).setActorSeq(1l);
		scheListFixture.get(0).setMemo("mememem momomo");
		scheListFixture.get(0).setTimeFrom("01:01");
		scheListFixture.get(0).setTimeTo("10:10");
		scheListFixture.get(0).setUnpaidbreakMin(60);
		scheListFixture.add(new ScheEty());
		scheListFixture.get(1).setActorSeq(1l);
		scheListFixture.get(1).setMemo("mememem momomo2");
		scheListFixture.get(1).setTimeFrom("12:34");
		scheListFixture.get(1).setTimeTo("23:45");
		scheListFixture.get(1).setUnpaidbreakMin(90);

		for (ScheEty sche : scheListFixture) {
			scheBiz.insertScheBiz(sche);
		}
	}

	@Test
	public void retrieveScheTest() {
		ScheEty retrieveQry = new ScheEty();
		retrieveQry.setActorSeq(1l);
		retrieveQry.setAsNormalStus();
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

	@Test
	public void updateScheTest() {
		ScheEty retrieveQryForOriginal = new ScheEty();
		retrieveQryForOriginal.setActorSeq(1l);
		retrieveQryForOriginal.setAsNormalStus();
		List<ScheEty> originalScheList = scheBiz.retireveScheListBiz(retrieveQryForOriginal);

		originalScheList.get(0).setMemo("updated memo 0");
		originalScheList.get(0).setTimeFrom("10:10");
		originalScheList.get(0).setTimeTo("12:30");
		originalScheList.get(0).setUnpaidbreakMin(15);
		originalScheList.get(1).setMemo("updated memo 0");
		originalScheList.get(1).setTimeFrom("23:50");
		originalScheList.get(1).setTimeTo("00:00");
		originalScheList.get(1).setUnpaidbreakMin(15);

		assertEquals(1, scheBiz.updateScheBiz(originalScheList.get(0)));
		assertEquals(1, scheBiz.updateScheBiz(originalScheList.get(1)));

		ScheEty retrieveQryForUpdated = new ScheEty();
		retrieveQryForUpdated.setActorSeq(1l);
		retrieveQryForUpdated.setAsEditedStus();
		List<ScheEty> updatedCardList = scheBiz.retireveScheListBiz(retrieveQryForUpdated);

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
