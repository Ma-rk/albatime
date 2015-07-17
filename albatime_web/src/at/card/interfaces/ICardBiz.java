package at.card.interfaces;

import java.util.List;

import at.model.CardEty;

public interface ICardBiz {
	int createCardBiz(CardEty card);

	List<CardEty> retireveCardListBiz(CardEty card);

	int updateCardBiz(CardEty card);
}
