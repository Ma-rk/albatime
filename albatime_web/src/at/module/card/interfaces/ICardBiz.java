package at.module.card.interfaces;

import java.util.List;

import at.model.CardEty;

public interface ICardBiz {
	void insertCardBiz(CardEty card);

	List<CardEty> retireveCardListBiz(CardEty card);

	void updateCardBiz(CardEty card);

	void deleteCardBiz(CardEty card);
}
