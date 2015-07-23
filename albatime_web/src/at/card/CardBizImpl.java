package at.card;

import java.util.List;

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
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		card.setAsNormalStus();
		int insertCardResult = cardDao.insertCardBiz(card);
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return insertCardResult;
	}

	public List<CardEty> retireveCardListBiz(CardEty card) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		List<CardEty> cardList = cardDao.retrieveCardListDao(card);
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return cardList;
	}

	public int updateCardBiz(CardEty card) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		card.setAsEditedStus();
		int updateCardResult = cardDao.updateCardDao(card);
		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return updateCardResult;
	}
}
