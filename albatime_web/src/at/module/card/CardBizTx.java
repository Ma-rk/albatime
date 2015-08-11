package at.module.card;

import java.util.List;

import at.model.CardEty;
import at.module.card.interfaces.ICardBiz;

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
	public void insertCardBiz(CardEty card) {
		cardBiz.insertCardBiz(card);
	}

	public List<CardEty> retireveCardListBiz(CardEty card) {
		return cardBiz.retireveCardListBiz(card);
	}

	public void updateCardBiz(CardEty card) {
		cardBiz.updateCardBiz(card);
	}
}
