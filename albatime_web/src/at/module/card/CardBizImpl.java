package at.module.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import at.model.CardEty;
import at.module.card.interfaces.ICardBiz;
import at.module.card.interfaces.ICardDao;

public class CardBizImpl implements ICardBiz {

	@Autowired
	private ICardDao cardDao;

	public void insertCardBiz(CardEty card) {
		card.setAsNormalStus();
		cardDao.insertCardDao(card);
	}

	public List<CardEty> retireveCardListBiz(CardEty card) {
		card.setAsNormalStus();
		return cardDao.retrieveCardListDao(card);
	}

	public void updateCardBiz(CardEty card) {
		cardDao.updateCardDao(card);
	}

	public void deleteCardBiz(CardEty card) {
		card.setAsDeletedStus();
		cardDao.deleteCardDao(card);
	}
}
