package at.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.card.interfaces.ICardBiz;
import at.model.CardEty;
import at.supp.CC;

@Controller
public class CardCont {
	private static final Logger lgr = LoggerFactory.getLogger(CardCont.class);
	/*
	 * DI codes
	 */
	ICardBiz cardBiz;

	public void setCardBiz(ICardBiz cardBiz) {
		this.cardBiz = cardBiz;
	}

	/*
	 * functional methods
	 */
	@RequestMapping(value = CC.API_CARD, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody String createCardCont(@CookieValue("userIdInCookie") long userId, CardEty card) {
		lgr.debug(CC.GETTING_INTO_2 + "createCardCont");
		lgr.debug(card.toString());

		int inserActorResult = cardBiz.createCardBiz(card);

		lgr.debug(CC.GETTING_OUT_2 + "createCardCont");
		return CC.gson.toJson(inserActorResult);
	}
}
