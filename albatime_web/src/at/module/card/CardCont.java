package at.module.card;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.com.CC;
import at.com.CommUtil;
import at.model.CardEty;
import at.module.card.interfaces.ICardBiz;
import at.supp.ResultFac;

@RestController
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
	public String insertCardCont(@Valid CardEty card, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_CARD_CREATE_FAIL));
		}
		lgr.debug(card.toString());

		cardBiz.insertCardBiz(card);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(ResultFac.rf());
	}

	@RequestMapping(value = CC.API_CARD, produces = "application/json", method = RequestMethod.GET)
	public String retrieveCardListCont(@Valid CardEty card, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_CARD_RETRIEVE_FAIL));
		}
		lgr.debug(card.toString());

		List<CardEty> cardList = cardBiz.retireveCardListBiz(card);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(ResultFac.rf(cardList));
	}

	@RequestMapping(value = CC.API_CARD, produces = "application/json", method = RequestMethod.PUT)
	public String updateCardCont(@Valid CardEty card, BindingResult result) {
		lgr.debug(CC.GETTING_INTO_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		if (CommUtil.checkGotWrongParams(result)) {
			return CC.gson.toJson(ResultFac.rf(false, CC.ERROR_CARD_UPDATE_FAIL));
		}
		lgr.debug(card.toString());

		cardBiz.updateCardBiz(card);

		lgr.debug(CC.GETTING_OUT_2 + new Object() {}.getClass().getEnclosingMethod().getName());
		return CC.gson.toJson(ResultFac.rf());
	}
}
