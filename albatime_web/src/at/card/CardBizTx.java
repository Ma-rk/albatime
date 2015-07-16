package at.card;

import org.springframework.transaction.PlatformTransactionManager;

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

	private PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionMansger) {
		this.transactionManager = transactionMansger;
	}

	/*
	 * functional methods
	 */
	public int createCardBiz(CardEty card) {
		return cardBiz.createCardBiz(card);
	}

}
