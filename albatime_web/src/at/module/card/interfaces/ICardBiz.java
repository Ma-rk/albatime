package at.module.card.interfaces;

import java.util.List;

import at.model.CardEty;

public interface ICardBiz {
	int insertCardBiz(CardEty card);

	List<CardEty> retireveCardListBiz(CardEty card);

	int updateCardBiz(CardEty card);
}
