package at.module.card.interfaces;

import java.util.List;

import at.model.CardEty;

public interface ICardDao {
	void insertCardDao(CardEty card);

	List<CardEty> retrieveCardListDao(CardEty card);

	void updateCardDao(CardEty card);

	void deleteCardDao(CardEty card);
}
