package card;

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

import at.card.interfaces.ICardBiz;
import at.card.interfaces.ICardDao;
import at.com.CC;
import at.model.CardEty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/at-servlet.xml")
public class CardTest {
	private static final Logger lgr = LoggerFactory.getLogger(CardTest.class);
	@Autowired
	private ICardDao cardDao;
	@Autowired
	private ICardBiz cardBiz;

	List<CardEty> cardListFixture = new ArrayList<CardEty>();

	@Before
	public void setUp() {
		lgr.debug("deleted lows: [{}]", cardDao.cleanTbCardDao());

		cardListFixture.add(new CardEty());
		cardListFixture.get(0).setActorSeq(1l);
		cardListFixture.get(0).setName("card1");
		cardListFixture.get(0).setMemo("mememem momomo");
		cardListFixture.get(0).setHourFrom(10);
		cardListFixture.get(0).setMinFrom(0);
		cardListFixture.get(0).setHourTo(17);
		cardListFixture.get(0).setMinTo(59);
		cardListFixture.get(0).setUnpaidbreakMin(60);

		cardListFixture.add(new CardEty());
		cardListFixture.get(1).setActorSeq(1l);
		cardListFixture.get(1).setName("card2");
		cardListFixture.get(1).setMemo("mememem momomo2");
		cardListFixture.get(1).setHourFrom(8);
		cardListFixture.get(1).setMinFrom(0);
		cardListFixture.get(1).setHourTo(8);
		cardListFixture.get(1).setMinTo(59);
		cardListFixture.get(1).setUnpaidbreakMin(90);

		for (CardEty card : cardListFixture) {
			cardBiz.insertCardBiz(card);
		}
	}

	@Test
	public void retrieveCardTest() {
		List<CardEty> retrievedCardList = cardBiz.retireveCardListBiz(cardListFixture.get(0));

		for (int i = 0; i < retrievedCardList.size(); i++) {
			assertEquals(cardListFixture.get(i).getName(), retrievedCardList.get(i).getName());
			assertEquals(cardListFixture.get(i).getMemo(), retrievedCardList.get(i).getMemo());
			assertEquals(cardListFixture.get(i).getHourFrom(), retrievedCardList.get(i).getHourFrom());
			assertEquals(cardListFixture.get(i).getMinTo(), retrievedCardList.get(i).getMinTo());
			assertEquals(cardListFixture.get(i).getHourFrom(), retrievedCardList.get(i).getHourFrom());
			assertEquals(cardListFixture.get(i).getMinTo(), retrievedCardList.get(i).getMinTo());
			assertEquals(cardListFixture.get(i).getUnpaidbreakMin(), retrievedCardList.get(i).getUnpaidbreakMin());
			assertEquals(CC.CARD_STUS_NORMAL, retrievedCardList.get(i).getStus());
		}
	}

	@Test
	public void updateCardTest() {
		List<CardEty> retrievedCardList = cardBiz.retireveCardListBiz(cardListFixture.get(0));

		retrievedCardList.get(0).setName("updated name 0");
		retrievedCardList.get(0).setMemo("updated memo 0");
		retrievedCardList.get(0).setHourFrom(10);
		retrievedCardList.get(0).setMinFrom(0);
		retrievedCardList.get(0).setHourTo(17);
		retrievedCardList.get(0).setMinTo(59);
		retrievedCardList.get(0).setUnpaidbreakMin(15);
		retrievedCardList.get(1).setName("updated name 0");
		retrievedCardList.get(1).setMemo("updated memo 0");
		retrievedCardList.get(1).setHourFrom(11);
		retrievedCardList.get(1).setMinFrom(10);
		retrievedCardList.get(1).setHourTo(18);
		retrievedCardList.get(1).setMinTo(40);
		retrievedCardList.get(1).setUnpaidbreakMin(15);

		assertEquals(1, cardBiz.updateCardBiz(retrievedCardList.get(0)));
		assertEquals(1, cardBiz.updateCardBiz(retrievedCardList.get(1)));

		List<CardEty> updatedCardList = cardBiz.retireveCardListBiz(cardListFixture.get(0));

		for (int i = 0; i < updatedCardList.size(); i++) {
			assertEquals(retrievedCardList.get(i).getName(), updatedCardList.get(i).getName());
			assertEquals(retrievedCardList.get(i).getMemo(), updatedCardList.get(i).getMemo());
			assertEquals(retrievedCardList.get(i).getHourFrom(), updatedCardList.get(i).getHourFrom());
			assertEquals(retrievedCardList.get(i).getMinFrom(), updatedCardList.get(i).getMinFrom());
			assertEquals(retrievedCardList.get(i).getHourTo(), updatedCardList.get(i).getHourTo());
			assertEquals(retrievedCardList.get(i).getMinTo(), updatedCardList.get(i).getMinTo());
			assertEquals(retrievedCardList.get(i).getUnpaidbreakMin(), updatedCardList.get(i).getUnpaidbreakMin());
			assertEquals(CC.CARD_STUS_EDITED, updatedCardList.get(i).getStus());
		}
	}

	@After
	public void tearDown() {
		lgr.debug("deleted lows: [{}]", cardDao.cleanTbCardDao());
	}
}
