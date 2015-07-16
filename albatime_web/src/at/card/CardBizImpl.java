package at.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.card.interfaces.ICardBiz;
import at.card.interfaces.ICardDao;
import at.model.CardEty;
import at.supp.CC;

public class CardBizImpl implements ICardBiz {
	private static final Logger lgr = LoggerFactory.getLogger(CardBizImpl.class);
	/*
	 * DI codes
	 */
	private ICardDao cardDao;

	public void setCardDao(ICardDao cardDao) {
		this.cardDao = cardDao;
	}

	/*
	 * functional methods
	 */
	public int createCardBiz(CardEty card) {
		lgr.debug(CC.GETTING_INTO_4 + "createCardBiz");
		card.setAsNormalStus();
		int insertCardResult = cardDao.insertCardBiz(card);
		lgr.debug(CC.GETTING_OUT_4 + "createCardBiz");
		return insertCardResult;
	}
}
