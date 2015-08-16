package module;

import static org.junit.Assert.assertEquals;

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

import at.com.CC;
import at.com.interfaces.IComDao;
import at.model.ScheEty;
import at.module.sche.interfaces.IScheBiz;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class ScheTest {
	private static final Logger lgr = LoggerFactory.getLogger(ScheTest.class);
	@Autowired
	private IComDao comDao;
	@Autowired
	private IScheBiz scheBiz;

	List<ScheEty> scheListFixture = new ArrayList<ScheEty>();

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		lgr.debug("deleted lows: [{}]", comDao.cleanTbScheDao());

		scheListFixture.add(new ScheEty());
		scheListFixture.get(0).setActorSeq(33l);
		scheListFixture.get(0).setMemo("mememem momomo");
		scheListFixture.get(0).setDate(new Date("2015/07/16"));
		scheListFixture.get(0).setHourFrom(10);
		scheListFixture.get(0).setMinFrom(10);
		scheListFixture.get(0).setHourTo(10);
		scheListFixture.get(0).setMinTo(11);
		scheListFixture.get(0).setUnpaidbreakMin(60);

		scheListFixture.add(new ScheEty());
		scheListFixture.get(1).setActorSeq(33l);
		scheListFixture.get(1).setMemo("mememem momomo2");
		scheListFixture.get(1).setDate(new Date("2015/07/20"));
		scheListFixture.get(1).setHourFrom(3);
		scheListFixture.get(1).setMinFrom(44);
		scheListFixture.get(1).setHourTo(4);
		scheListFixture.get(1).setMinTo(55);
		scheListFixture.get(1).setUnpaidbreakMin(90);

		for (ScheEty sche : scheListFixture) {
			scheBiz.insertScheBiz(sche);
		}
	}

	@Test
	public void retrieveScheTest() {
		ScheEty retrieveQry = new ScheEty();
		retrieveQry.setActorSeq(33l);
		retrieveQry.setAsNormalStus();
		List<ScheEty> retrievedScheList = scheBiz.retireveScheListBiz(retrieveQry);

		for (int i = 0; i < retrievedScheList.size(); i++) {
			assertEquals(scheListFixture.get(i).getActorSeq(), retrievedScheList.get(i).getActorSeq());
			assertEquals(scheListFixture.get(i).getMemo(), retrievedScheList.get(i).getMemo());
			assertEquals(scheListFixture.get(i).getHourFrom(), retrievedScheList.get(i).getHourFrom());
			assertEquals(scheListFixture.get(i).getMinFrom(), retrievedScheList.get(i).getMinFrom());
			assertEquals(scheListFixture.get(i).getHourTo(), retrievedScheList.get(i).getHourTo());
			assertEquals(scheListFixture.get(i).getHourFrom(), retrievedScheList.get(i).getHourFrom());
			assertEquals(scheListFixture.get(i).getUnpaidbreakMin(), retrievedScheList.get(i).getUnpaidbreakMin());
			assertEquals(scheListFixture.get(i).getMins(), retrievedScheList.get(i).getMins());
			assertEquals(CC.SCHE_WORKED_NO, retrievedScheList.get(i).getWorked());
			assertEquals(CC.SCHE_PAID_NO, retrievedScheList.get(i).getPaid());
			assertEquals(CC.SCHE_STUS_NORMAL, retrievedScheList.get(i).getStus());
		}
		assertEquals(1, retrievedScheList.get(0).getMins());
		assertEquals(71, retrievedScheList.get(1).getMins());
	}

	@Test
	public void updateScheTest() {
		ScheEty retrieveQryForOriginal = new ScheEty();
		retrieveQryForOriginal.setActorSeq(33l);
		retrieveQryForOriginal.setAsNormalStus();
		List<ScheEty> originalScheList = scheBiz.retireveScheListBiz(retrieveQryForOriginal);

		originalScheList.get(0).setMemo("updated memo 1");
		originalScheList.get(0).setHourFrom(10);
		originalScheList.get(0).setMinFrom(10);
		originalScheList.get(0).setHourTo(12);
		originalScheList.get(0).setMinTo(30);
		originalScheList.get(0).setUnpaidbreakMin(15);
		originalScheList.get(0).setAsWorkedYes();
		originalScheList.get(0).setAsPaidYes();

		originalScheList.get(1).setMemo("updated memo 2");
		originalScheList.get(1).setHourFrom(23);
		originalScheList.get(1).setMinFrom(50);
		originalScheList.get(1).setHourTo(0);
		originalScheList.get(1).setMinTo(0);
		originalScheList.get(1).setUnpaidbreakMin(15);
		originalScheList.get(1).setAsWorkedYes();
		originalScheList.get(1).setAsPaidYes();

		for (ScheEty sche : originalScheList) {
			scheBiz.updateScheBiz(sche);
		}

		ScheEty retrieveQryForUpdated = new ScheEty();
		retrieveQryForUpdated.setActorSeq(33l);
		List<ScheEty> updatedScheList = scheBiz.retireveScheListBiz(retrieveQryForUpdated);

		for (int i = 0; i < updatedScheList.size(); i++) {
			assertEquals(originalScheList.get(i).getMemo(), updatedScheList.get(i).getMemo());
			assertEquals(originalScheList.get(i).getHourFrom(), updatedScheList.get(i).getHourFrom());
			assertEquals(originalScheList.get(i).getMinFrom(), updatedScheList.get(i).getMinFrom());
			assertEquals(originalScheList.get(i).getHourTo(), updatedScheList.get(i).getHourTo());
			assertEquals(originalScheList.get(i).getMinTo(), updatedScheList.get(i).getMinTo());
			assertEquals(originalScheList.get(i).getUnpaidbreakMin(), updatedScheList.get(i).getUnpaidbreakMin());
			assertEquals(CC.SCHE_WORKED_YES, updatedScheList.get(i).getWorked());
			assertEquals(CC.SCHE_PAID_YES, updatedScheList.get(i).getPaid());
		}
		assertEquals(140, updatedScheList.get(0).getMins());
		assertEquals(10, updatedScheList.get(1).getMins());
	}

	@Test
	public void deleteScheTest() {
		List<ScheEty> retrievedScheList = scheBiz.retireveScheListBiz(scheListFixture.get(0));

		for (int i = 0; i < retrievedScheList.size(); i++) {
			scheBiz.deleteScheBiz(retrievedScheList.get(i));
		}
		assertEquals(new ArrayList<ScheEty>(), scheBiz.retireveScheListBiz(scheListFixture.get(0)));
	}

	@After
	public void tearDown() {
		lgr.debug("deleted lows: [{}]", comDao.cleanTbScheDao());
	}
}
