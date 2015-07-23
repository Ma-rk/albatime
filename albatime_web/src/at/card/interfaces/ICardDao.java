package at.card.interfaces;

import java.util.List;

import at.model.CardEty;

public interface ICardDao {
	int insertCardBiz(CardEty card);

	List<CardEty> retrieveCardListDao(CardEty card);

	int updateCardDao(CardEty card);
}
