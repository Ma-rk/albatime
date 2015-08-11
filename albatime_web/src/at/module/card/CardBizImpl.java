package at.module.card;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.com.CC;
import at.model.CardEty;
import at.module.card.interfaces.ICardBiz;
import at.module.card.interfaces.ICardDao;

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
	public void insertCardBiz(CardEty card) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		card.setAsNormalStus();
		cardDao.insertCardDao(card);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
	}

	public List<CardEty> retireveCardListBiz(CardEty card) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		card.setAsNormalStus();
		List<CardEty> cardList = cardDao.retrieveCardListDao(card);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		return cardList;
	}

	public void updateCardBiz(CardEty card) {
		lgr.debug(CC.GETTING_INTO_4 + new Object() {}.getClass().getEnclosingMethod().getName());
		cardDao.updateCardDao(card);
		lgr.debug(CC.GETTING_OUT_4 + new Object() {}.getClass().getEnclosingMethod().getName());
	}
}
