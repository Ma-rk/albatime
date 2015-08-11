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
	public int insertCardBiz(CardEty card) {
		return cardBiz.insertCardBiz(card);
	}

	public List<CardEty> retireveCardListBiz(CardEty card) {
		return cardBiz.retireveCardListBiz(card);
	}

	public int updateCardBiz(CardEty card) {
		return cardBiz.updateCardBiz(card);
	}
}
