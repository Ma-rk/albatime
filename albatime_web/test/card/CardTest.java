package card;

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

import at.card.interfaces.ICardBiz;
import at.card.interfaces.ICardDao;
import at.model.CardEty;
import at.supp.CC;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class CardTest {
	private static final Logger lgr = LoggerFactory.getLogger(CardTest.class);
	@Autowired
	private ICardDao cardDao;
	@Autowired
	private ICardBiz cardBiz;

	List<CardEty> cardListFixture = new ArrayList<CardEty>();

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		lgr.debug("deleted lows: [{}]", cardDao.cleanTbActorDao());

		cardListFixture.add(new CardEty());
		cardListFixture.get(0).setActorSeq(1l);
		cardListFixture.get(0).setName("card1");
		cardListFixture.get(0).setMemo("mememem momomo");
		cardListFixture.get(0).setTimeFrom(new Time(5, 6, 7));
		cardListFixture.get(0).setTimeTo(new Time(8, 9, 10));
		cardListFixture.get(0).setUnpaidbreakMin(60);
		cardListFixture.add(new CardEty());
		cardListFixture.get(1).setActorSeq(1l);
		cardListFixture.get(1).setName("card2");
		cardListFixture.get(1).setMemo("mememem momomo2");
		cardListFixture.get(1).setTimeFrom(new Time(10, 11, 12));
		cardListFixture.get(1).setTimeTo(new Time(12, 11, 9));
		cardListFixture.get(1).setUnpaidbreakMin(90);

		for (CardEty card : cardListFixture) {
			cardBiz.createCardBiz(card);
		}
	}

	@Test
	public void retrieveCardTest() {
		List<CardEty> retrievedCardList = cardBiz.retireveCardListBiz(cardListFixture.get(0));

		for (int i = 0; i < retrievedCardList.size(); i++) {
			assertEquals(cardListFixture.get(i).getName(), retrievedCardList.get(i).getName());
			assertEquals(cardListFixture.get(i).getMemo(), retrievedCardList.get(i).getMemo());
			assertEquals(cardListFixture.get(i).getTimeFrom(), retrievedCardList.get(i).getTimeFrom());
			assertEquals(cardListFixture.get(i).getTimeTo(), retrievedCardList.get(i).getTimeTo());
			assertEquals(cardListFixture.get(i).getUnpaidbreakMin(), retrievedCardList.get(i).getUnpaidbreakMin());
			assertEquals(CC.CARD_STUS_NORMAL, retrievedCardList.get(i).getStus());
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void updateCardTest() {
		List<CardEty> retrievedCardList = cardBiz.retireveCardListBiz(cardListFixture.get(0));

		retrievedCardList.get(0).setName("updated name 0");
		retrievedCardList.get(0).setMemo("updated memo 0");
		retrievedCardList.get(0).setTimeFrom(new Time(5, 4, 3));
		retrievedCardList.get(0).setTimeTo(new Time(11, 10, 9));
		retrievedCardList.get(0).setUnpaidbreakMin(15);
		retrievedCardList.get(1).setName("updated name 0");
		retrievedCardList.get(1).setMemo("updated memo 0");
		retrievedCardList.get(1).setTimeFrom(new Time(5, 4, 3));
		retrievedCardList.get(1).setTimeTo(new Time(11, 10, 9));
		retrievedCardList.get(1).setUnpaidbreakMin(15);

		assertEquals(1, cardBiz.updateCardBiz(retrievedCardList.get(0)));
		assertEquals(1, cardBiz.updateCardBiz(retrievedCardList.get(1)));

		List<CardEty> updatedCardList = cardBiz.retireveCardListBiz(cardListFixture.get(0));

		for (int i = 0; i < updatedCardList.size(); i++) {
			assertEquals(retrievedCardList.get(i).getName(), updatedCardList.get(i).getName());
			assertEquals(retrievedCardList.get(i).getMemo(), updatedCardList.get(i).getMemo());
			assertEquals(retrievedCardList.get(i).getTimeFrom(), updatedCardList.get(i).getTimeFrom());
			assertEquals(retrievedCardList.get(i).getTimeTo(), updatedCardList.get(i).getTimeTo());
			assertEquals(retrievedCardList.get(i).getUnpaidbreakMin(), updatedCardList.get(i).getUnpaidbreakMin());
			assertEquals(CC.CARD_STUS_EDITED, updatedCardList.get(i).getStus());
		}
	}

	@After
	public void tearDown() {
		lgr.debug("deleted lows: [{}]", cardDao.cleanTbActorDao());
	}
}
