package at.card;

import java.util.List;

import at.card.interfaces.ICardBiz;
import at.model.CardEty;

public class CardBizTx implements ICardBiz {
	/*
	 * DI codes
	 */
	private ICardBiz cardBiz;

	public void setCardBiz(ICardBiz cardBiz) {
		this.cardBiz = cardBiz;
	}

	/*
	 * functional methods
	 */
	public int createCardBiz(CardEty card) {
		return cardBiz.createCardBiz(card);
	}

	public List<CardEty> retireveCardListBiz(CardEty card) {
		return cardBiz.retireveCardListBiz(card);
	}

	public int updateCardBiz(CardEty card) {
		return cardBiz.updateCardBiz(card);
	}
}
