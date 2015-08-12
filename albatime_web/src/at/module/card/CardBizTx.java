package at.module.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import at.model.CardEty;
import at.module.card.interfaces.ICardBiz;

public class CardBizTx implements ICardBiz {

	@Autowired
	private ICardBiz cardBiz;

	public void insertCardBiz(CardEty card) {
		cardBiz.insertCardBiz(card);
	}

	public List<CardEty> retireveCardListBiz(CardEty card) {
		return cardBiz.retireveCardListBiz(card);
	}

	public void updateCardBiz(CardEty card) {
		cardBiz.updateCardBiz(card);
	}

	public void deleteCardBiz(CardEty card) {
		cardBiz.deleteCardBiz(card);
	}
}
